package pw.zakharov.amongcraft.api.arena;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:25
 */
public interface Team {

    int getSize();

    int getMaxSize();

    default void join(@NotNull Player player) {
        if (getMaxSize() > getSize() || player.hasPermission("among.admin")) {
            getPlayers().add(player);
            player.teleport(getSpawn());
        }
    }

    @NotNull Location getSpawn();

    @NotNull List<Player> getPlayers();

    @NotNull TeamData getData();

    @NotNull Role getRole();

    interface TeamData {

        @NotNull String getName();

        @NotNull Color getColor();

    }

    enum Role {
        IMPOSTER,
        INNOCENT,
        SPECTATOR,
        ;
    }

}
