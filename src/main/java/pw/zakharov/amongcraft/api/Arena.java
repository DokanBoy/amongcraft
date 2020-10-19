package pw.zakharov.amongcraft.api;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:05
 */
public interface Arena {

    void enable();

    void disable();

    void start();

    void start(int afterSec);

    void stop(@NonNull StopCause cause);

    void stop(@NonNull StopCause cause, int afterSec);

    void randomJoin(@NonNull Player player);

    void join(@NonNull Player player, @NonNull Team team);

    @NonNull ArenaContext getContext();

    @NonNull State getState();

    @NonNull World getWorld();

    interface ArenaContext {

        @NonNull String getName();

        default int getTeamsAmount() {
            return getTeams().size();
        }

        @NonNull Set<Team> getTeams();

        @NonNull Set<Player> getPlayers();

        @NonNull Location getLobby();

    }

    enum State {
        ENABLED,
        DISABLED,
        STARTING,
        STOPPING,
        STARTED,
        ;
    }

    enum StopCause {
        INNOCENT_WIN,
        IMPOSTER_WIN,
        UNKNOWN,
        ;
    }

}
