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
import pw.zakharov.amongcraft.api.arena.Arena;
import pw.zakharov.amongcraft.api.arena.ArenaContext;
import pw.zakharov.amongcraft.api.arena.Team;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.team.ImposterTeam;
import pw.zakharov.amongcraft.team.InnocentTeam;
import pw.zakharov.amongcraft.team.SpectatorTeam;
import pw.zakharov.amongcraft.util.LocationUtil;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static pw.zakharov.amongcraft.api.arena.Arena.State.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:48
 */
public class SingleArena implements Arena {

    private World world;
    private State state;

    private final String arenaName;
    private final String worldName;
    private final ArenaContext context;

    public SingleArena(@NotNull String arenaName,
                       @NotNull String worldName,
                       @NotNull Location lobbyLocation,
                       int teams) {
        this.arenaName = arenaName;
        this.worldName = worldName;
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

        world = Helper.server().getWorld(worldName);
        setupWorld();

        setStatus(ENABLED);
    }

    @Override
    public void disable() {
        if (state == DISABLED) return;
        if (state == STARTED) stop();

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
            final Iterator<Location> spawns = LocationUtil.getEndlessLocationIterator(t.getSpawn(), 3);
            for (Player p : teamPlayers) {
                p.teleport(spawns.next());
                p.sendMessage(new TextComponent("Игра началась! Вы телепортированы на арену."));
            }
        }

        setStatus(STARTED);
        Events.callSync(new ArenaStartEvent());
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
    public void stop() {
        if (!(state == STARTING || state == STARTED)) return; // todo: optimize expression

        setStatus(ENABLED);
        Helper.server().broadcast(new TextComponent("Арена остановлена"));
        Events.callSync(new ArenaStopEvent());
    }

    @Override
    public void stop(int afterSec) {
        if (state == STARTING || state == STARTED) return;

        Helper.server().broadcast(new TextComponent("Остановка арены через " + afterSec + " сек"));
        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(this::stop);
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
                    .filter(t -> t.getRole() == Team.Role.SPECTATOR)
                    .findAny()
                    .orElseThrow(NullPointerException::new);
            specTeam.join(player);
            Log.info("Player " + player.getName() + " joined to " + specTeam.getData().getName());
            return;
        }

        Optional<Team> currentTeam = getContext().getTeams()
                .stream()
                .filter(t -> t.getPlayers().contains(player))
                .findFirst();
        if (currentTeam.isPresent()) {
            Log.info("Player already in " + currentTeam.get().getData().getName());
            return;
        }
        team.join(player);
        player.teleport(context.getLobby());
        Log.info("Player " + player.getName() + " joined to " + team.getData().getName());
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

        private final Location lobbyLocation;
        private final Set<Team> teams;
        private final int maxTeams;

        public SingleArenaContext(@NotNull Location lobbyLocation, int maxTeams) {
            this.teams = ImmutableSet.of(
                    new ImposterTeam(lobbyLocation.add(10, 0, 10), 2),
                    new InnocentTeam(lobbyLocation.add(15, 0, 15), 12),
                    new SpectatorTeam(lobbyLocation.add(20, 0, 20))
            );
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