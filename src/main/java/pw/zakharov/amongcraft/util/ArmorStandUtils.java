package pw.zakharov.amongcraft.util;

import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 1:38
 */
public final class ArmorStandUtils {

    private ArmorStandUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void throwSword(@NonNull Player player, int distance) {
        player.sendMessage("You throw sword for " + distance + " blocks");
    }

}
