package pw.zakharov.amongcraft.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.lucko.helper.cooldown.Cooldown;
import me.lucko.helper.cooldown.CooldownMap;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.util.ArmorStandUtils;

import java.util.concurrent.TimeUnit;

import static pw.zakharov.amongcraft.api.Team.Role.IMPOSTER;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 20:59
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerListener implements Listener {

    @NonNull Plugin plugin;
    @NonNull ArenaService arenaService;
    @NonNull TeamService teamService;

    public PlayerListener(@NonNull Plugin plugin,
                          @NonNull ArenaService arenaService,
                          @NonNull TeamService teamService) {
        this.plugin = plugin;
        this.arenaService = arenaService;
        this.teamService = teamService;
    }

    @EventHandler
    public void onJoin(@NonNull PlayerJoinEvent event) {
        val player = event.getPlayer();

        event.setJoinMessage("§aИгрок §f" + player.getName() + " §aвошел на арену!");
    }

    @EventHandler
    public void onQuit(@NonNull PlayerQuitEvent event) {
        val player = event.getPlayer();
        val playerTeam = teamService.getPlayerTeam(player).orElseThrow(NullPointerException::new);

        playerTeam.leave(player);
        event.setQuitMessage("§cИгрок §f" + player.getName() + " §cвышел! Он был " + playerTeam.getContext().getName());
    }

    @EventHandler
    public void onInteractWithKnife(@NonNull PlayerInteractEvent event) {
        if (!event.getAction().name().contains("RIGHT_CLICK")) return;
        if (event.getItem().getType() != Material.IRON_SWORD) return;

        val player = event.getPlayer();
        val playerTeam = teamService.getPlayerTeam(player).orElseThrow(NullPointerException::new);

        if (playerTeam.getContext().getRole() != IMPOSTER) return;

        CooldownMap<Player> cooldownMap = CooldownMap.create(Cooldown.of(5, TimeUnit.SECONDS));
        if (cooldownMap.test(player)) {
            ArmorStandUtils.throwSword(player, 5);
        } else {
            player.sendMessage(new TextComponent("Перезарядка, осталось " + cooldownMap.elapsed(player)));
        }
    }

}