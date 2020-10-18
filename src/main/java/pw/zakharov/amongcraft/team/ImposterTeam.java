package pw.zakharov.amongcraft.team;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Location;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class ImposterTeam extends AbstractTeam {

    public ImposterTeam(@NonNull Set<Location> spawns, int maxSize) {
        super(new ImposterTeamContext("Предатель"), spawns, maxSize);
    }

    private static class ImposterTeamContext extends AbstractTeamContext {

        private ImposterTeamContext(@NonNull String name) {
            super(name, Color.RED, Role.IMPOSTER);
        }

    }

}