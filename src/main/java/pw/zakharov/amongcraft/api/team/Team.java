package pw.zakharov.amongcraft.api.team;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

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
        }
    }

    @NotNull Location getSpawn();

    @NotNull Set<Player> getPlayers();

    @NotNull TeamContext getData();

    @NotNull Role getRole();

    interface TeamContext {

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
