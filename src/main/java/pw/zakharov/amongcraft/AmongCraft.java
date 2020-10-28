package pw.zakharov.amongcraft;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.lucko.helper.Commands;
import me.lucko.helper.Events;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.arena.loader.ArenaLoader;
import pw.zakharov.amongcraft.inject.AmongModule;
import pw.zakharov.amongcraft.listener.ArenaListener;
import pw.zakharov.amongcraft.listener.PlayerListener;
import pw.zakharov.amongcraft.listener.TeamListener;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.TeamService;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class AmongCraft extends ExtendedJavaPlugin {

    Injector injector;

    @Override
    protected void enable() {
        saveResource("arenas\\shuttle.conf", false);
        saveResource("configuration.conf", false);
        saveResource("scoreboards.conf", false);
        saveResource("messages.conf", false);

        injector = Guice.createInjector(new AmongModule());
        ArenaService arenaService = injector.getInstance(ArenaService.class);
        TeamService teamService = injector.getInstance(TeamService.class);

        registerListener(new TeamListener(this));
        registerListener(new ArenaListener(this, teamService));
        registerListener(new PlayerListener(this, arenaService, teamService));

        /* TEST USAGE */
        val arenaLoader = injector.getInstance(ArenaLoader.class);
        val shuttleArena = arenaLoader.loadArena("Shuttle");
        arenaService.register(shuttleArena);

        Events.subscribe(PlayerJoinEvent.class)
              .handler(event -> shuttleArena.join(event.getPlayer()));

        Commands.create()
                .handler(context -> shuttleArena.start(5))
                .register("astart");

        Commands.create()
                .handler(context -> {
                    shuttleArena.stop(Arena.StopCause.UNKNOWN);
                    shuttleArena.disable();
                })
                .register("astop");
    }

    @Override
    protected void disable() {
        ArenaService arenaService = injector.getInstance(ArenaService.class);
        arenaService.getArenas()
                    .forEach(arena -> {
                        arena.stop(Arena.StopCause.UNKNOWN);
                        arena.disable();
                    });
    }

}