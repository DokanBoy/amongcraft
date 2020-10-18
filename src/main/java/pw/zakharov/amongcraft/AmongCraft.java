package pw.zakharov.amongcraft;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.Commands;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class AmongCraft extends ExtendedJavaPlugin {

    static @Getter TeamService teamService;
    static @Getter TaskService taskService;
    static @Getter ArenaService arenaService;
    static @Getter ScoreboardService scoreboardService;

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

}
