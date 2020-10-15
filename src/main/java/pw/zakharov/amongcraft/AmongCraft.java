package pw.zakharov.amongcraft;

import me.lucko.helper.Commands;
import me.lucko.helper.Helper;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.arena.SingleArena;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.ScoreboardService;
import pw.zakharov.amongcraft.service.TaskService;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.service.impl.ArenaServiceImpl;
import pw.zakharov.amongcraft.service.impl.ScoreboardServiceImpl;
import pw.zakharov.amongcraft.service.impl.TaskServiceImpl;
import pw.zakharov.amongcraft.service.impl.TeamServiceImpl;

import static pw.zakharov.amongcraft.api.Arena.StopCause.UNKNOWN;

public final class AmongCraft extends ExtendedJavaPlugin {

    private World amongWorld;

    private static @NotNull TeamService teamService;
    private static @NotNull TaskService taskService;
    private static @NotNull ArenaService arenaService;
    private static @NotNull ScoreboardService scoreboardService;

    @Override
    protected void enable() {
        WorldCreator wc = new WorldCreator("among-us_shuttle");
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        amongWorld = Helper.server().createWorld(wc);

        teamService = new TeamServiceImpl(this);
        taskService = new TaskServiceImpl(this);
        arenaService = new ArenaServiceImpl(this);
        scoreboardService = new ScoreboardServiceImpl(this);

        arenaService.register(new SingleArena("Shuttle", amongWorld, new Location(amongWorld, 0, 4, 0), 2));

        Arena shuttleArena = arenaService.getArena("Shuttle").orElseThrow(NullPointerException::new);

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    shuttleArena.enable();
                    shuttleArena.start(5);
                    shuttleArena.randomJoin(context.sender());
                    context.reply("Arena state: " + shuttleArena.getState().name());
                })
                .register("astart");

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    shuttleArena.stop(UNKNOWN, 5);
                    shuttleArena.disable();
                })
                .register("astop");
    }

    @Override
    protected void disable() {
        arenaService.getArenas().forEach(arena -> {
            arena.stop(UNKNOWN);
            arena.disable();
        });
    }

    public static @NotNull TeamService getTeamService() {
        return teamService;
    }

    public static @NotNull TaskService getTaskService() {
        return taskService;
    }

    public static @NotNull ArenaService getArenaService() {
        return arenaService;
    }

    public static @NotNull ScoreboardService getScoreboardService() {
        return scoreboardService;
    }

}
