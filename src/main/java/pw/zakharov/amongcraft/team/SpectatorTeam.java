package pw.zakharov.amongcraft.team;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.team.Team;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class SpectatorTeam implements Team {

    private static final Role SPECTATOR_ROLE = Role.SPECTATOR;
    private static final int MAX_TEAM_SIZE = 999;

    private final TeamContext data;
    private final Location spawn;
    private final Set<Player> players;

    public SpectatorTeam(Location spawn) {
        this.spawn = spawn;
        this.players = new LinkedHashSet<>();
        this.data = new SpectatorTeamContext("Наблюдатель");
    }

    @Override
    public int getSize() {
        return players.size();
    }

    @Override
    public int getMaxSize() {
        return MAX_TEAM_SIZE;
    }

    @Override
    public void join(@NotNull Player player) {
        players.add(player);
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
    public @NotNull Team.TeamContext getData() {
        return data;
    }

    @Override
    public @NotNull Role getRole() {
        return SPECTATOR_ROLE;
    }

    private static class SpectatorTeamContext implements TeamContext {

        private final String name;
        private final Color color = Color.GRAY;

        public SpectatorTeamContext(String name) {
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