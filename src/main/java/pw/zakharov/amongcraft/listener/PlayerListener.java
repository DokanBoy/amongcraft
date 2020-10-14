package pw.zakharov.amongcraft.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.util.ArmorStandUtils;

import static pw.zakharov.amongcraft.api.Team.Role.IMPOSTER;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 20:59
 */
public class PlayerListener implements Listener {

    private final @NotNull Plugin plugin;
    private final @NotNull ArenaService arenaService;
    private final @NotNull TeamService teamService;

    public PlayerListener(@NotNull Plugin plugin,
                          @NotNull ArenaService arenaService,
                          @NotNull TeamService teamService) {
        this.plugin = plugin;
        this.arenaService = arenaService;
        this.teamService = teamService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("§aИгрок " + player.getName() + " §cвошел на арену!");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        teamService.getPlayerTeam(player).ifPresent(team -> {
            team.leave(player);
        });
        event.setQuitMessage("§cИгрок " + player.getName() + " §cвышел!");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().name().contains("RIGHT_CLICK")) return;

        Player player = event.getPlayer();
        teamService.getPlayerTeam(player).ifPresent(team -> {
            if (team.getContext().getRole() == IMPOSTER && event.getItem().getType() == Material.IRON_SWORD)
                ArmorStandUtils.throwSword(player, 5);
        });
    }

}