package pw.zakharov.amongcraft.arena;

import com.google.common.collect.ImmutableSet;
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
import pw.zakharov.amongcraft.api.event.arena.ArenaScheduledStopEvent;
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

    @Getter @NonNull World world;
    @Getter @NonNull ArenaContext context;

    @Getter @NonNull @NonFinal State state;

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
        if (state == STARTED || state == DISABLED) {
            Log.warn("You can`t start arena when it has not state ENABLED!");
            return;
        }

        setStatus(STARTED);
        Events.callSync(new ArenaStartEvent(this));
    }

    @Override
    public void start(int afterSec) {
        if (state != ENABLED) {
            Log.warn("You can`t start arena when it has not state ENABLED!");
            return;
        }

        setStatus(STARTING);
        Helper.server().broadcast(new TextComponent("Запуск арены через " + afterSec + " сек"));
        Schedulers.builder()
                  .sync()
                  .after(afterSec, TimeUnit.SECONDS)
                  .run(this::start);
    }

    @Override
    public void stop(@NonNull StopCause cause) {
        if (state != STARTED) {
            Log.warn("You can`t stop arena when it has not state STARTED!");
            return;
        }

        setStatus(ENABLED);
        Events.callSync(new ArenaStopEvent(this, cause));
    }

    @Override
    public void stop(@NonNull StopCause cause, int afterSec) {
        if (state != STARTED) {
            Log.warn("You can`t stop arena when it has not state STARTED!");
            return;
        }

        setStatus(STOPPING);
        Schedulers.builder()
                  .sync()
                  .after(afterSec, TimeUnit.SECONDS)
                  .run(() -> stop(cause));
        Events.callSync(new ArenaScheduledStopEvent(this, cause, afterSec));
    }

    @Override
    public void randomJoin(@NonNull Player player) {
        final @NonNull Team imposterTeam = TeamService.getTeam(this, Team.Role.IMPOSTER);
        final @NonNull Team innocentTeam = TeamService.getTeam(this, Team.Role.IMPOSTER);
        final @NonNull Team spectatorTeam = TeamService.getTeam(this, Team.Role.SPECTATOR);

        if (imposterTeam.getSize() < imposterTeam.getMaxSize()
                && innocentTeam.getSize() < innocentTeam.getMaxSize()) {
            Log.info("В обоих командах есть место, выбираем рандомную");
            join(player, RandomSelector.uniform(ImmutableSet.of(imposterTeam, innocentTeam)).pick());
        } else if (imposterTeam.getSize() >= imposterTeam.getMaxSize()) {
            Log.info("В команде импостеров закончилось место, пикаем innocentTeam");
            join(player, innocentTeam);
        } else if (innocentTeam.getSize() >= innocentTeam.getMaxSize()) {
            Log.info("В команде мирных закончилось место, пикаем imposterTeam");
            join(player, imposterTeam);
        } else {
            Log.info("В командах закончилось место, пикаем spectatorTeam");
            join(player, spectatorTeam);
        }
    }

    @Override
    public void join(@NonNull Player player, @NonNull Team team) {
        if (state == DISABLED) {
            Log.warn("Arena " + context.getName() + " is disabled. Enable before join!" + "");
            return;
        }
        final Optional<Team> currentTeam = context.getTeams()
                                            .stream()
                                            .filter(t -> t.getPlayers().contains(player))
                                            .findFirst();
        if (currentTeam.isPresent()) {
            Log.info("Player already in " + currentTeam.get().getContext().getName());
            return;
        }
        team.join(player);
        Log.info("Player " + player.getName() + " joined to " + team.getContext().getName());
    }

    private void setStatus(@NonNull State state) {
        this.state = state;
    }

    @ToString
    @EqualsAndHashCode
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class SingleArenaContext implements ArenaContext {

        @Getter @NonNull String name;
        @Getter @NonNull Location lobby;
        @Getter @NonNull Set<Team> teams;

        public SingleArenaContext(@NonNull String name,
                                  @NonNull Location lobby, @NonNull Location spectatorSpawn,
                                  @NonNull Set<Location> innocentSpawns, int innocents,
                                  @NonNull Set<Location> imposterSpawns, int imposters) {
            final @NonNull TeamService teamService = AmongCraft.getTeamService();
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