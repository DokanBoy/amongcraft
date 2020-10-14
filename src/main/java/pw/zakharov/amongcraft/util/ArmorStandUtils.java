package pw.zakharov.amongcraft.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 1:38
 */
public final class ArmorStandUtils {

    private ArmorStandUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void throwSword(@NotNull Player player, int distance) {
        player.sendMessage("You throw sword for " + distance + " blocks");
    }

}