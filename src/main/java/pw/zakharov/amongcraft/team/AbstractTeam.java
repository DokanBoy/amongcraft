package pw.zakharov.amongcraft.team;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Team;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 1:01
 */
public abstract class AbstractTeam implements Team {

    private final int maxSize;
    private final @NotNull TeamContext context;
    private final @NotNull Set<Player> players;
    private final @NotNull Set<Location> spawns;
    private final @NotNull Iterator<Location> spawnsIterator;

    protected AbstractTeam(@NotNull TeamContext context,
                           @NotNull Set<Location> spawns,
                           int maxSize) {
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
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public @NotNull Set<Location> getSpawns() {
        return spawns;
    }

    @Override
    public @NotNull Location getNextSpawn() {
        return spawnsIterator.next();
    }

    @Override
    public @NotNull Set<Player> getPlayers() {
        return players;
    }

    @Override
    public @NotNull TeamContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "maxSize=" + maxSize +
                ", context=" + context +
                ", players=" + players +
                ", spawns=" + spawns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTeam that = (AbstractTeam) o;
        return maxSize == that.maxSize &&
                Objects.equal(context, that.context) &&
                Objects.equal(players, that.players) &&
                Objects.equal(spawns, that.spawns);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maxSize, context, players, spawns);
    }

    protected abstract static class AbstractTeamContext implements TeamContext {

        private final @NotNull String name;
        private final @NotNull Color color;
        private final @NotNull Role role;

        protected AbstractTeamContext(@NotNull String name, @NotNull Color color, @NotNull Role role) {
            this.name = name;
            this.color = color;
            this.role = role;
        }

        @Override
        public @NotNull String getName() {
            return name;
        }

        @Override
        public @NotNull Color getColor() {
            return color;
        }

        @Override
        public @NotNull Role getRole() {
            return role;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "name='" + name + '\'' +
                    ", color=" + color +
                    ", role=" + role +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractTeamContext that = (AbstractTeamContext) o;
            return role == that.role &&
                    Objects.equal(color, that.color) &&
                    Objects.equal(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, color, role);
        }

    }

}
