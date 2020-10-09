package pw.zakharov.amongcraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.10.2020 1:52
 */
public class ArenaListener implements Listener {

    private final JavaPlugin plugin;

    public ArenaListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStart(ArenaStartEvent event) {

    }

    @EventHandler
    public void onStop(ArenaStopEvent event) {

    }

}