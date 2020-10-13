package pw.zakharov.amongcraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.service.ArenaService;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.10.2020 1:52
 */
public class ArenaListener implements Listener {

    private final @NotNull Plugin plugin;
    private final @NotNull ArenaService arenaService;

    public ArenaListener(@NotNull Plugin plugin, @NotNull ArenaService arenaService) {
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