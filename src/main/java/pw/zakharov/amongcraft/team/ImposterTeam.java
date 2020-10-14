package pw.zakharov.amongcraft.team;

import org.bukkit.Color;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class ImposterTeam extends AbstractTeam {

    public ImposterTeam(@NotNull Set<Location> spawns, int maxSize) {
        super(new ImposterTeamContext("Предатель"), spawns, maxSize);
    }

    private static class ImposterTeamContext extends AbstractTeamContext {

        private ImposterTeamContext(@NotNull String name) {
            super(name, Color.RED, Role.IMPOSTER);
        }

    }

}