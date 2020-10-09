package pw.zakharov.amongcraft.util;

import com.google.common.collect.Iterators;
import org.bukkit.Location;
import org.bukkit.util.NumberConversions;

import java.util.Iterator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 08.10.2020 19:27
 */
public final class LocationUtil {

    private LocationUtil() {
    }

    public static Location getSurfaceRandomNearLocation(Location location, double xRadius, double zRadius) {
        final DoubleUnaryOperator randomiser = d -> d * (2 * Math.random() - 1);
        return location.clone().add(randomiser.applyAsDouble(xRadius), 0, randomiser.applyAsDouble(zRadius));
    }

    public static Location getSurfaceRandomNearLocation(Location location, double xzRadius) {
        return getSurfaceRandomNearLocation(location, xzRadius, xzRadius);
    }

    public static Iterator<Location> getEndlessLocationIterator(Location location, double xzRadius) {
        return Iterators.cycle(Stream.generate(() -> LocationUtil.getSurfaceRandomNearLocation(location, xzRadius))
                .limit((long) NumberConversions.square(xzRadius))
                .collect(Collectors.toList()));
    }

}

