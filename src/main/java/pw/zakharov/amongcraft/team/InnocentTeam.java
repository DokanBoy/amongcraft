package pw.zakharov.amongcraft.team;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Location;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:01
 */
public class InnocentTeam extends AbstractTeam {

    public InnocentTeam(@NonNull Set<Location> spawns, int maxSize) {
        super(new InnocentTeamContext("Мирный"), spawns, maxSize);
    }

    private static class InnocentTeamContext extends AbstractTeamContext {

        private InnocentTeamContext(@NonNull String name) {
            super(name, Color.GREEN, Role.INNOCENT);
        }

    }

}