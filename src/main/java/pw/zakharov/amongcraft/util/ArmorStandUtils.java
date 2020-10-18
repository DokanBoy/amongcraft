package pw.zakharov.amongcraft.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 1:38
 */
@UtilityClass
public class ArmorStandUtils {

    public void throwSword(@NonNull Player player, int distance) {
        player.sendMessage("You throw sword for " + distance + " blocks");
    }

}
