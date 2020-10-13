package pw.zakharov.amongcraft.arena;

import com.google.common.collect.ImmutableSet;
import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.Schedulers;
import me.lucko.helper.random.RandomSelector;
import me.lucko.helper.utils.Log;
import me.lucko.helper.utils.Players;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.AmongCraft;
import pw.zakharov.amongcraft.api.arena.Arena;
import pw.zakharov.amongcraft.api.arena.ArenaContext;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.api.team.Team;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.team.ImposterTeam;
import pw.zakharov.amongcraft.team.InnocentTeam;
import pw.zakharov.amongcraft.team.SpectatorTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static pw.zakharov.amongcraft.api.arena.Arena.State.*;
import static pw.zakharov.amongcraft.api.arena.Arena.StopCause.UNKNOWN;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:48
 */
public class SingleArena implements Arena {

    private @NotNull State state;

    private final @NotNull String arenaName;
    private final @NotNull World world;
    private final @NotNull ArenaContext context;

    public SingleArena(@NotNull String arenaName,
                       @NotNull World world,
                       @NotNull Location lobbyLocation,
                       int teams) {
        this.arenaName = arenaName;
        this.world = world;
        this.state = DISABLED;

        Log.info("Created arena: " + toString());
        this.context = new SingleArenaContext(lobbyLocation, teams);
    }

    private void setupWorld() {
        world.setPVP(false);
        world.setAutoSave(false);
        world.setDifficulty(Difficulty.EASY);

        world.setMonsterSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setWaterAnimalSpawnLimit(0);
    }

    @Override
    public @NotNull String getName() {
        return arenaName;
    }

    @Override
    public void enable() {
        if (state == ENABLED) return;
        setupWorld();

        setStatus(ENABLED);
    }

    @Override
    public void disable() {
        if (state == DISABLED) return;
        if (state == STARTED) stop(UNKNOWN);

        Players.all().forEach(player -> player.kickPlayer("Arena disabled"));
        Helper.server().unloadWorld(world, false);

        setStatus(DISABLED);
    }

    @Override
    public void start() {
        if (!(state == ENABLED || state == STARTING)) return;
        setStatus(STARTING);

        final Set<Team> teams = context.getTeams();
        for (Team t : teams) {
            final Set<Player> teamPlayers = t.getPlayers();
            for (Player p : teamPlayers) {
                p.teleport(t.getNextSpawn());
                p.sendMessage(new TextComponent("Игра началась! Вы телепортированы на арену."));
            }
        }

        setStatus(STARTED);

        Events.callSync(new ArenaStartEvent(this));
        Helper.server().broadcast(new TextComponent("Арена запущена!"));
    }

    @Override
    public void start(int afterSec) {
        if (!(state == ENABLED || state == STARTING)) return;

        setStatus(STARTING);
        Helper.server().broadcast(new TextComponent("Запуск арены через " + afterSec + " сек"));
        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(this::start);
    }

    @Override
    public void stop(StopCause cause) {
        if (!(state == STARTING || state == STARTED)) return; // todo: optimize expression

        setStatus(ENABLED);
        Helper.server().broadcast(new TextComponent("Арена остановлена"));
        Events.callSync(new ArenaStopEvent(this, cause));
    }

    @Override
    public void stop(StopCause cause, int afterSec) {
        if (state == STARTING || state == STARTED) return;

        Helper.server().broadcast(new TextComponent("Остановка арены через " + afterSec + " сек"));
        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(() -> stop(cause));
    }

    @Override
    public @NotNull ArenaContext getContext() {
        return context;
    }

    private void setStatus(State state) {
        this.state = state;
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void randomJoin(@NotNull Player player) {
        join(player, RandomSelector.uniform(context.getTeams()).pick());
    }

    @Override
    public void join(@NotNull Player player, @NotNull Team team) {
        if (state == DISABLED) {
            Log.warn("Arena " + arenaName + " is disabled. Enable before join!" + "");
            return;
        }

        if (state == STARTED) {
            Team specTeam = getContext().getTeams()
                    .stream()
                    .filter(t -> t.getContext().getRole() == Team.Role.SPECTATOR)
                    .findAny()
                    .orElseThrow(NullPointerException::new);
            specTeam.join(player);
            Log.info("Player " + player.getName() + " joined to " + specTeam.getContext().getName());
            return;
        }

        Optional<Team> currentTeam = getContext().getTeams()
                .stream()
                .filter(t -> t.getPlayers().contains(player))
                .findFirst();
        if (currentTeam.isPresent()) {
            Log.info("Player already in " + currentTeam.get().getContext().getName());
            return;
        }
        team.join(player);
        player.teleport(context.getLobby());
        Log.info("Player " + player.getName() + " joined to " + team.getContext().getName());
    }

    @Override
    public String toString() {
        return "SingleArena{" +
                "name='" + arenaName +
                ", world=" + world +
                ", status=" + state +
                '}';
    }

    public static class SingleArenaContext implements ArenaContext {

        private final @NotNull Location lobbyLocation;
        private final @NotNull Set<Team> teams;
        private final int maxTeams;

        public SingleArenaContext(@NotNull Location lobbyLocation, int maxTeams) {
            final TeamService teamService = AmongCraft.getTeamService();
            teamService.register(new ImposterTeam(ImmutableSet.of(lobbyLocation.add(10, 0, 10)), 2));
            teamService.register(new InnocentTeam(ImmutableSet.of(lobbyLocation.add(15, 0, 15)), 2));
            teamService.register(new SpectatorTeam(ImmutableSet.of(lobbyLocation.add(20, 0, 20))));

            this.teams = teamService.getTeams();
            this.maxTeams = maxTeams;
            this.lobbyLocation = lobbyLocation;

            Log.info("Created arena context: " + toString());
        }

        @Override
        public int getMaxTeams() {
            return maxTeams;
        }

        @Override
        public @NotNull Set<Team> getTeams() {
            return teams;
        }

        @Override
        public @NotNull List<Player> getPlayers() {
            List<Player> players = new ArrayList<>();
            teams.forEach(team -> players.addAll(team.getPlayers()));
            return players;
        }

        @Override
        public @NotNull Location getLobby() {
            return lobbyLocation;
        }

        @Override
        public String toString() {
            return "SingleArenaContext{" +
                    "teams=" + teams +
                    ", maxTeams=" + maxTeams +
                    '}';
        }

    }

}