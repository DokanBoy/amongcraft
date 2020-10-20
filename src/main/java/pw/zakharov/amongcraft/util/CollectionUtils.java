package pw.zakharov.amongcraft.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import me.lucko.helper.serialize.Point;
import org.bukkit.Location;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 19.10.2020 15:52
 */
@UtilityClass
public class CollectionUtils {

    public @NonNull Set<Location> points2Locations(@NonNull Set<Point> points) {
        val locations = new LinkedHashSet<Location>();
        for (Point point : points) {
            locations.add(point.toLocation());
        }
        return locations;
    }

    public @NonNull Set<Point> locations2Points(@NonNull Set<Location> locations) {
        val points = new LinkedHashSet<Point>();
        for (Location location : locations) {
            points.add(Point.of(location));
        }
        return points;
    }

}
