package pw.zakharov.amongcraft.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

    void stop(@NotNull StopCause cause);

    void stop(@NotNull StopCause cause, int afterSec);

    void randomJoin(@NotNull Player player);

    void join(@NotNull Player player, @NotNull Team team);

    @NotNull ArenaContext getContext();

    @NotNull State getState();

    interface ArenaContext {

        @NotNull String getName();

        int getMaxTeams();

        default int getTeamsAmount() {
            return getTeams().size();
        }

        @NotNull Set<Team> getTeams();

        @NotNull Set<Player> getPlayers();

        @NotNull Location getLobby();

    }

    enum State {
        ENABLED,
        DISABLED,
        STARTING,
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
