package pw.zakharov.amongcraft.data;

import lombok.*;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.config.objectmapping.Setting;
import me.lucko.helper.config.objectmapping.serialize.ConfigSerializable;
import org.bukkit.Location;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 15.10.2020 22:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArenaData {

    /* Arena info */
    @Setting(value = "arena-name")
    @NonNull String name;

    @Setting(value = "world-name")
    @NonNull String worldName;

    /* Team settings */
    @Setting(value = "team-imposter-size")
    int maxImposters;

    @Setting(value = "team-imposter-sword-cooldown")
    int swordCooldown;

    @Setting(value = "team-innocent-size")
    int maxInnocents;

    /* Locations */
    @Setting(value = "location-lobby")
    @NonNull Location lobbyLocation;

    @Setting(value = "location-spectator")
    @NonNull Location spectatorLocation;

    @Setting(value = "locations-innocent")
    @NonNull Set<Location> innocentLocations;

    @Setting(value = "locations-imposter")
    @NonNull Set<Location> imposterLocations;

}