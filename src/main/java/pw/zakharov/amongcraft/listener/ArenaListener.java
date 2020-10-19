package pw.zakharov.amongcraft.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.api.Team;
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
        Team impostersTeam = event.getArena()
                .getContext()
                .getTeams()
                .stream()
                .filter(team -> team.getContext().getRole() == Team.Role.IMPOSTER)
                .findFirst().orElseThrow(NullPointerException::new);

        ItemStack itemStack = ItemStackBuilder.of(Material.IRON_SWORD)
                .name("&cНож")
                .breakable(false)
                .hideAttributes()
                .build();

        for (Player player : impostersTeam.getPlayers()) {
            player.getInventory().addItem(itemStack);
        }
    }

    @EventHandler
    public void onStop(ArenaStopEvent event) {

    }

}