package pw.zakharov.amongcraft;

import me.lucko.helper.Commands;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.ArenaLoader;
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

    private static TeamService teamService;
    private static TaskService taskService;
    private static ArenaService arenaService;
    private static ScoreboardService scoreboardService;

    @Override
    protected void enable() {
        saveResource("configuration.conf", false);
        saveResource("localization.conf", false);
        saveResource("arenas\\shuttle.conf", false);

        teamService = new TeamServiceImpl(this);
        taskService = new TaskServiceImpl(this);
        arenaService = new ArenaServiceImpl(this);
        scoreboardService = new ScoreboardServiceImpl(this);

        ArenaLoader arenaLoader = ArenaLoader.createLoader(getDataFolder().toPath().resolve("arenas"), "Shuttle");
        Arena shuttleArena = arenaLoader.getArena();
        arenaService.register(shuttleArena);

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
