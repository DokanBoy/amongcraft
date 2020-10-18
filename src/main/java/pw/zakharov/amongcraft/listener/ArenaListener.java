package pw.zakharov.amongcraft.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.TeamService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.10.2020 1:52
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaListener implements Listener {

    @NonNull Plugin plugin;
    @NonNull ArenaService arenaService;
    @NonNull TeamService teamService;

    public ArenaListener(@NonNull Plugin plugin,
                         @NonNull ArenaService arenaService,
                         @NonNull TeamService teamService) {
        this.plugin = plugin;
        this.arenaService = arenaService;
        this.teamService = teamService;
    }

    @EventHandler
    public void onStart(ArenaStartEvent event) {

    }

    @EventHandler
    public void onStop(ArenaStopEvent event) {

    }

}