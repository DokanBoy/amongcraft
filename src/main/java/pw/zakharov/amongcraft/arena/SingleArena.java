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

import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        this.state = State.DISABLED;

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
    public void enable() {
        setStatus(State.ENABLED);

        world = Helper.server().getWorld(worldName);
        setupWorld();
    }

    @Override
    public void disable() {
        setStatus(State.DISABLED);

        Players.all().forEach(player -> player.kickPlayer(""));
        Helper.server().unloadWorld(world, false);
    }

    @Override
    public void start() {
        TextComponent textComponent = new TextComponent("Арена запущена!");
        Helper.server().broadcast(textComponent);

        context.getTeams().forEach(team -> team.getPlayers().forEach(player -> player.teleport(team.getSpawn())));
        Events.callSync(new ArenaStartEvent());
    }

    @Override
    public void start(int afterSec) {
        TextComponent textComponent = new TextComponent("Запуск арены через " + afterSec + " сек");
        Helper.server().broadcast(textComponent);

        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(this::start);
    }

    @Override
    public void stop() {
        TextComponent textComponent = new TextComponent("Арена остановлена");
        Helper.server().broadcast(textComponent);

        Events.callSync(new ArenaStopEvent());
    }

    @Override
    public void stop(int afterSec) {
        TextComponent textComponent = new TextComponent("Остановка арены через " + afterSec + " сек");
        Helper.server().broadcast(textComponent);

        Schedulers.builder()
                .sync()
                .after(afterSec, TimeUnit.SECONDS)
                .run(this::stop);
    }

    @Override
    public boolean canStart() {
        return state != State.ENABLED;
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

    public void randomJoin(@NotNull Player player) {
        join(player, RandomSelector.uniform(context.getTeams()).pick());
    }

    public void join(@NotNull Player player, @NotNull Team team) {
        team.join(player);
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
                    new ImposterTeam(lobbyLocation.add(5, 0, 10), 12),
                    new InnocentTeam(lobbyLocation.add(-5, 5, 10), 12),
                    new SpectatorTeam(lobbyLocation.add(5, 5, 10))
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