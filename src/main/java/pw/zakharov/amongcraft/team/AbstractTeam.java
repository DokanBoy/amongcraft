package pw.zakharov.amongcraft.team;

import com.google.common.collect.Iterators;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.team.Team;

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

    }

}
