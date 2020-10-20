package pw.zakharov.amongcraft.team;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class SpectatorTeam extends AbstractTeam {

    public SpectatorTeam(@NonNull Location spawn) {
        super(new SpectatorTeamContext("Наблюдатель"), Collections.singleton(spawn), 999);
    }

    @Override
    public void join(@NonNull Player player) {
        getPlayers().add(player);
    }

    private static class SpectatorTeamContext extends AbstractTeamContext {

        private SpectatorTeamContext(@NonNull String name) {
            super(name, Color.GRAY, Role.SPECTATOR);
        }

    }

}