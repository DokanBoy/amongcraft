package pw.zakharov.amongcraft.team;

import com.google.common.collect.Iterators;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pw.zakharov.amongcraft.api.Team;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 1:01
 */
@ToString(exclude = {"spawnsIterator"})
@EqualsAndHashCode(exclude = {"spawnsIterator"})
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class AbstractTeam implements Team {

    @Getter int maxSize;
    @Getter @NonNull TeamContext context;
    @Getter @NonNull Set<Player> players;
    @Getter @NonNull Set<Location> spawns;
    @NonNull @PackagePrivate Iterator<Location> spawnsIterator;

    protected AbstractTeam(@NonNull TeamContext context, @NonNull Set<Location> spawns, int maxSize) {
        this.context = context;
        this.spawns = spawns;
        this.maxSize = maxSize;

        this.players = new LinkedHashSet<>();
        this.spawnsIterator = Iterators.cycle(spawns);
    }

    @Override
    public @NonNull Location getNextSpawn() {
        return spawnsIterator.next();
    }

    @ToString
    @EqualsAndHashCode
    @FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
    protected abstract static class AbstractTeamContext implements TeamContext {

        @Getter @NonNull String name;
        @Getter @NonNull Color color;
        @Getter @NonNull Role role;

        protected AbstractTeamContext(@NonNull String name, @NonNull Color color, @NonNull Role role) {
            this.name = name;
            this.color = color;
            this.role = role;
        }

    }

}
