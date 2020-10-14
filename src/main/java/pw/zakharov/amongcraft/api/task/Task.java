package pw.zakharov.amongcraft.api.task;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 12:36
 */
public interface Task {

    @NotNull Location getStartPosition();

    @NotNull Location getTaskLocation();

    @NotNull State getState();

    void start();

    void complete();

    enum State {
        NOT_COMPLETED,
        COMPLETED,
        ;
    }

}
