package pw.zakharov.amongcraft.data;

import lombok.*;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.config.objectmapping.Setting;
import me.lucko.helper.config.objectmapping.serialize.ConfigSerializable;
import me.lucko.helper.serialize.Point;

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
    @NonNull Point lobbyPoint;

    @Setting(value = "location-spectator")
    @NonNull Point spectatorPoint;

    @Setting(value = "locations-innocent")
    @NonNull Set<Point> innocentPoints;

    @Setting(value = "locations-imposter")
    @NonNull Set<Point> imposterPoints;

}