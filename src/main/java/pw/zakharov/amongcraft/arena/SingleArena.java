package pw.zakharov.amongcraft.arena;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
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
import pw.zakharov.amongcraft.AmongCraft;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.Team;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.team.ImposterTeam;
import pw.zakharov.amongcraft.team.InnocentTeam;
import pw.zakharov.amongcraft.team.SpectatorTeam;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static pw.zakharov.amongcraft.api.Arena.State.*;
import static pw.zakharov.amongcraft.api.Arena.StopCause.UNKNOWN;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:48
 */
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SingleArena implements Arena {

    @NonFinal @NonNull State state;

    @NonNull World world;
    @NonNull ArenaContext context;

    public SingleArena(@NonNull World world, @NonNull SingleArenaContext context) {
        this.world = world;
        this.context = context;
        this.state = DISABLED;

        Log.info("Created arena: " + toString());
        enable();
    }

    private void setupWorld() {
        world.setAutoSave(false);
        world.setDifficulty(Difficulty.EASY);

        world.setMonsterSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setWaterAnimalSpawnLimit(0);
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

        setStatus(DISABLED);
        Players.all().forEach(player -> player.kickPlayer("Arena disabled"));
        Helper.server().unloadWorld(world, false);
    }

    @Override
    public void start() {
        if (state != STARTING && state != ENABLED) return;

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
        if (state != ENABLED) return;

        setStatus(STARTING);
        Helper.server().broadcast(new TextComponent("Запуск арены через " + afterSec + " сек"));
        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(this::start);
    }

    @Override
    public void stop(@NonNull StopCause cause) {
        if (state == ENABLED || state == DISABLED) return;

        setStatus(ENABLED);
        Helper.server().broadcast(new TextComponent("Арена остановлена"));
        Events.callSync(new ArenaStopEvent(this, cause));
    }

    @Override
    public void stop(@NonNull StopCause cause, int afterSec) {
        if (state != STARTED) return;

        setStatus(STOPPING);
        Helper.server().broadcast(new TextComponent("Остановка арены через " + afterSec + " сек"));
        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(() -> stop(cause));
    }

    @Override
    public @NonNull ArenaContext getContext() {
        return context;
    }

    private void setStatus(@NonNull State state) {
        this.state = state;
    }

    @Override
    public @NonNull State getState() {
        return state;
    }

    @Override
    public void randomJoin(@NonNull Player player) {
        // todo : максимальное количество импостеров и спектаторов нельзя пикать
        Team team = RandomSelector.uniform(context.getTeams()).pick();
        join(player, team);
    }

    @Override
    public void join(@NonNull Player player, @NonNull Team team) {
        if (state == DISABLED) {
            Log.warn("Arena " + context.getName() + " is disabled. Enable before join!" + "");
            return;
        }

        Team specTeam = context.getTeams()
                .stream()
                .filter(t -> t.getContext().getRole() == Team.Role.SPECTATOR)
                .findAny()
                .orElseThrow(NullPointerException::new);
        if (state == STARTED) {
            specTeam.join(player);
            Log.info("Player " + player.getName() + " joined to " + specTeam.getContext().getName());
            return;
        }

        Optional<Team> currentTeam = context.getTeams()
                .stream()
                .filter(t -> t.getPlayers().contains(player))
                .findFirst();
        if (currentTeam.isPresent()) {
            Log.info("Player already in " + currentTeam.get().getContext().getName());
            return;
        }
        if (!team.join(player)) {
            specTeam.join(player);
        }
        player.teleport(context.getLobby());
        Log.info("Player " + player.getName() + " joined to " + team.getContext().getName());
    }

    @ToString
    @EqualsAndHashCode
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class SingleArenaContext implements ArenaContext {

         @Getter @NonNull String name;
         @Getter @NonNull Location lobby;
         @Getter @NonNull Set<Team> teams;

        public SingleArenaContext(@NonNull String name,
                                  @NonNull Location lobby,
                                  @NonNull Location spectatorSpawn,
                                  @NonNull Set<Location> innocentSpawns, int innocents,
                                  @NonNull Set<Location> imposterSpawns, int imposters) {
            final TeamService teamService = AmongCraft.getTeamService();
            teamService.register(name, new InnocentTeam(innocentSpawns, innocents));
            teamService.register(name, new ImposterTeam(imposterSpawns, imposters));
            teamService.register(name, new SpectatorTeam(spectatorSpawn));


            this.name = name;
            this.lobby = lobby;
            this.teams = teamService.getArenaTeams(name);
        }

        @Override
        public @NonNull Set<Player> getPlayers() {
            Set<Player> players = new LinkedHashSet<>();
            teams.forEach(team -> players.addAll(team.getPlayers()));
            return players;
        }

    }

}