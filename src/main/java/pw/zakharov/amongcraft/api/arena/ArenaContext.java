package pw.zakharov.amongcraft.api.arena;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:06
 */
public interface ArenaContext {

    int getMaxTeams();

    default int getTeamsAmount() {
        return getTeams().size();
    }

    @NotNull Set<Team> getTeams();

    @NotNull Location getLobby();
}
