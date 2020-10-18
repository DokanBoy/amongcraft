package pw.zakharov.amongcraft.listener;

import lombok.NonNull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.service.ArenaService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.10.2020 1:52
 */
public class ArenaListener implements Listener {

    private final @NonNull Plugin plugin;
    private final @NonNull ArenaService arenaService;

    public ArenaListener(@NonNull Plugin plugin,
                         @NonNull ArenaService arenaService) {
        this.plugin = plugin;
        this.arenaService = arenaService;
    }

    @EventHandler
    public void onStart(ArenaStartEvent event) {

    }

    @EventHandler
    public void onStop(ArenaStopEvent event) {

    }

}