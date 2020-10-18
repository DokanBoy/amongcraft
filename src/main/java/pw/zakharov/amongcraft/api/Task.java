package pw.zakharov.amongcraft.api;

import lombok.NonNull;
import org.bukkit.Location;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 12:36
 */
public interface Task {

    @NonNull TaskContext getContext();

    @NonNull Location getStartLocation();

    @NonNull Location getTaskLocation();

    @NonNull State getState();

    void start();

    void complete();

    interface TaskContext {
        @NonNull String getName();
    }

    enum State {
        NOT_COMPLETED,
        STARTED,
        COMPLETED,
        ;
    }

}
