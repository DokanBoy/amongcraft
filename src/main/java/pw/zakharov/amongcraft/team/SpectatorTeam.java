package pw.zakharov.amongcraft.team;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class SpectatorTeam extends AbstractTeam {

    public SpectatorTeam(@NotNull Set<Location> spawns) {
        super(new SpectatorTeamContext("Наблюдатель"), spawns, 999);
    }

    @Override
    public boolean join(@NotNull Player player) {
        getPlayers().add(player);
        return true;
    }

    private static class SpectatorTeamContext extends AbstractTeamContext {

        private SpectatorTeamContext(@NotNull String name) {
            super(name, Color.GRAY, Role.SPECTATOR);
        }

    }

}