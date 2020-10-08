package pw.zakharov.amongcraft.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 20:59
 */
public class PlayerListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {

    }

}
