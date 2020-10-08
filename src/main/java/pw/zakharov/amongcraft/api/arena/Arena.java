package pw.zakharov.amongcraft.api.arena;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:05
 */
public interface Arena {

    void enable();

    void disable();

    void start();

    void start(int afterSec);

    void stop();

    void stop(int afterSec);

    boolean canStart();

    @NotNull ArenaContext getContext();

    @NotNull State getState();

    void randomJoin(@NotNull Player player);

    void join(@NotNull Player player, @NotNull Team team);

    enum State {
        ENABLED,
        DISABLED,
        ;
    }

}
