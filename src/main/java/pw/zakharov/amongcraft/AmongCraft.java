package pw.zakharov.amongcraft;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.lucko.helper.Commands;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.arena.loader.ArenaLoader;
import pw.zakharov.amongcraft.listener.ArenaListener;
import pw.zakharov.amongcraft.listener.PlayerListener;
import pw.zakharov.amongcraft.listener.TeamListener;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.ScoreboardService;
import pw.zakharov.amongcraft.service.TaskService;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.service.impl.ArenaServiceImpl;
import pw.zakharov.amongcraft.service.impl.ScoreboardServiceImpl;
import pw.zakharov.amongcraft.service.impl.TaskServiceImpl;
import pw.zakharov.amongcraft.service.impl.TeamServiceImpl;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class AmongCraft extends ExtendedJavaPlugin {

    static @Getter TeamService teamService;
    static @Getter TaskService taskService;
    static @Getter ArenaService arenaService;
    static @Getter ScoreboardService scoreboardService;

    @Override
    protected void enable() {
        saveResource("arenas\\shuttle.conf", false);
        saveResource("configuration.conf", false);
        saveResource("scoreboards.conf", false);
        saveResource("messages.conf", false);

        teamService = new TeamServiceImpl(this);
        taskService = new TaskServiceImpl(this);
        arenaService = new ArenaServiceImpl(this);
        scoreboardService = new ScoreboardServiceImpl(this);

        registerListener(new TeamListener(this));
        registerListener(new ArenaListener(this, teamService));
        registerListener(new PlayerListener(this, arenaService, teamService));

        /* TEST USAGE */
        val arenaLoader = ArenaLoader.createLoader(ArenaLoader.DEFAULT_ARENA_PATH, "Shuttle");
        val shuttleArena = arenaLoader.getArena();
        arenaService.register(shuttleArena);

        Commands.create()
                .assertPlayer()
                .handler(context -> shuttleArena.start(5))
                .register("astart");

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    shuttleArena.stop(Arena.StopCause.UNKNOWN, 5);
                    shuttleArena.disable();
                })
                .register("astop");
    }

    @Override
    protected void disable() {
        arenaService.getArenas()
                    .forEach(arena -> {
                        arena.stop(Arena.StopCause.UNKNOWN);
                        arena.disable();
                    });
    }

}