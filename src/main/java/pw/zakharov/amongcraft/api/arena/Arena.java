package pw.zakharov.amongcraft.api.arena;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.team.Team;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:05
 */
public interface Arena {

    @NotNull String getName();

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
