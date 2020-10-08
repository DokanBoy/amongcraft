package pw.zakharov.amongcraft.team;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.arena.Team;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class InnocentTeam implements Team {

    private static final Role INNOCENT_ROLE = Role.INNOCENT;

    private final int maxSize;
    private final TeamData data;
    private final Location spawn;
    private final Set<Player> players;

    public InnocentTeam(Location spawn, int maxSize) {
        this.maxSize = maxSize;
        this.spawn = spawn;
        this.players = new LinkedHashSet<>();
        this.data = new InnocentTeamData("Мирный");
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
    public @NotNull Location getSpawn() {
        return spawn;
    }

    @Override
    public @NotNull Set<Player> getPlayers() {
        return players;
    }

    @Override
    public @NotNull TeamData getData() {
        return data;
    }

    @Override
    public @NotNull Role getRole() {
        return INNOCENT_ROLE;
    }

    private static class InnocentTeamData implements TeamData {

        private final String name;
        private final Color color = Color.GREEN;

        public InnocentTeamData(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getName() {
            return name;
        }

        @Override
        public @NotNull Color getColor() {
            return color;
        }

    }

}
