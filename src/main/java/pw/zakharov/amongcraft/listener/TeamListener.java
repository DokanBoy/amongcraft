package pw.zakharov.amongcraft.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.Team;
import pw.zakharov.amongcraft.api.event.team.TeamJoinEvent;
import pw.zakharov.amongcraft.api.event.team.TeamLeaveEvent;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 19.10.2020 22:19
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamListener implements Listener {

    // todo: create loader for items
    static @NonNull ItemStack KNIFE_ITEM_STACK = ItemStackBuilder.of(Material.IRON_SWORD)
                                                                 .name("&cНож")
                                                                 .breakable(false)
                                                                 .hideAttributes()
                                                                 .build();

    @NonNull Plugin plugin;

    public TeamListener(@NonNull Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Общий слушатель
     * @param event TeamJoinEvent
     */
    @EventHandler
    public void onJoin(@NonNull TeamJoinEvent event) {
        final @NonNull Arena arena = event.getArena();
        final @NonNull Player player = event.getPlayer();

        player.setDisplayName("");
        player.getInventory().clear();
        player.getInventory().addItem(KNIFE_ITEM_STACK);
    }

    /**
     * Слушатель для входа в команду импостеров
     * @param event TeamJoinEvent
     */
    @EventHandler
    public void onJoinToImposters(@NonNull TeamJoinEvent event) {
        if (event.getTeam().getContext().getRole() != Team.Role.IMPOSTER) return;

        val arena = event.getArena();
        val player = event.getPlayer();

        player.setDisplayName("");
        player.getInventory().clear();
        player.getInventory().addItem(KNIFE_ITEM_STACK);
    }

    /**
     * Слушатель для входа в команду мирных
     * @param event TeamJoinEvent
     */
    @EventHandler
    public void onJoinToInnocents(@NonNull TeamJoinEvent event) {
        if (event.getTeam().getContext().getRole() != Team.Role.INNOCENT) return;

        val arena = event.getArena();
        val player = event.getPlayer();

        player.setDisplayName("");
        player.getInventory().clear();
        player.getInventory().addItem(KNIFE_ITEM_STACK);
    }

    /**
     * Слушатель для входа в команду наблюдателей
     * @param event TeamJoinEvent
     */
    @EventHandler
    public void onJoinToSpectators(@NonNull TeamJoinEvent event) {
        if (event.getTeam().getContext().getRole() != Team.Role.SPECTATOR) return;

        val arena = event.getArena();
        val player = event.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
    }

    /**
     * Общий слушатель для выхода из команды
     * @param event TeamLeaveEvent
     */
    @EventHandler
    public void onLeave(@NonNull TeamLeaveEvent event) {
        val arena = event.getArena();
        val player = event.getPlayer();

        player.getInventory().clear();
    }

}
