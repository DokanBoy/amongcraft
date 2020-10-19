package pw.zakharov.amongcraft.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.Helper;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.Team;
import pw.zakharov.amongcraft.api.event.arena.ArenaScheduledStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaScheduledStopEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStartEvent;
import pw.zakharov.amongcraft.api.event.arena.ArenaStopEvent;
import pw.zakharov.amongcraft.service.TeamService;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.10.2020 1:52
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaListener implements Listener {

    @NonNull Plugin plugin;
    @NonNull TeamService teamService;

    public ArenaListener(@NonNull Plugin plugin, @NonNull TeamService teamService) {
        this.plugin = plugin;
        this.teamService = teamService;
    }

    @EventHandler
    public void onStart(@NonNull ArenaStartEvent event) {
        final @NonNull Arena arena = event.getArena();
        final @NonNull Set<Team> teams = arena.getContext().getTeams();

        for (Player p: arena.getContext().getPlayers()) {
            arena.randomJoin(p);
        }

        for (Team t : teams) {
            final @NonNull Set<Player> teamPlayers = t.getPlayers();
            for (Player p : teamPlayers) {
                p.teleport(t.getNextSpawn());
                p.sendMessage(new TextComponent("Игра началась! Вы телепортированы на арену."));
                p.sendMessage(new TextComponent("Ваша роль: " + teamService.getPlayerTeam(p)
                                                                           .orElseThrow(NullPointerException::new)
                                                                           .getContext()
                                                                           .getName()));
            }
        }
        Helper.server().broadcast(new TextComponent("Арена запущена"));
    }

    @EventHandler
    public void onScheduledStart(@NonNull ArenaScheduledStartEvent event) {
        Helper.server().broadcast(new TextComponent("Запуск арены через " + event.getAfterSec() + " сек"));
    }

    @EventHandler
    public void onStop(@NonNull ArenaStopEvent event) {
        Helper.server().broadcast(new TextComponent("Арена остановлена"));
    }

    @EventHandler
    public void onScheduledStop(@NonNull ArenaScheduledStopEvent event) {
        Helper.server().broadcast(new TextComponent("Остановка арены через " + event.getAfterSec() + " сек"));
    }

}