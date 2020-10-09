package pw.zakharov.amongcraft.api.arena;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.service.ArenaService;

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

    void stop(StopCause cause);

    void stop(StopCause cause, int afterSec);

    @NotNull ArenaContext getContext();

    @NotNull State getState();

    void randomJoin(@NotNull Player player);

    void join(@NotNull Player player, @NotNull Team team);

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
