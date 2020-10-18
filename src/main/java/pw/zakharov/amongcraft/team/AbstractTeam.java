package pw.zakharov.amongcraft.team;

import com.google.common.collect.Iterators;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
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
public abstract class AbstractTeam implements Team {

    private final @Getter int maxSize;
    private final @Getter @NonNull TeamContext context;
    private final @Getter @NonNull Set<Player> players;
    private final @Getter @NonNull Set<Location> spawns;
    private final @NonNull Iterator<Location> spawnsIterator;

    protected AbstractTeam(@NonNull TeamContext context, @NonNull Set<Location> spawns, int maxSize) {
        this.context = context;
        this.spawns = spawns;
        this.maxSize = maxSize;

        this.players = new LinkedHashSet<>();
        this.spawnsIterator = Iterators.cycle(spawns);
    }

    @Override
    public int getSize() {
        return players.size();
    }

    @Override
    public @NonNull Location getNextSpawn() {
        return spawnsIterator.next();
    }

    @ToString
    @EqualsAndHashCode
    protected abstract static class AbstractTeamContext implements TeamContext {

        private final @Getter @NonNull String name;
        private final @Getter @NonNull Color color;
        private final @Getter @NonNull Role role;

        protected AbstractTeamContext(@NonNull String name, @NonNull Color color, @NonNull Role role) {
            this.name = name;
            this.color = color;
            this.role = role;
        }

    }

}
