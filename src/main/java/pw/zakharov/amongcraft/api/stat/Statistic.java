package pw.zakharov.amongcraft.api.stat;

import lombok.NonNull;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:38
 */
public interface Statistic<V> {

    @NonNull String getName();

    @NonNull Type getType();

    @NonNull V getValue();

    enum Type {
        KILL_INNOCENT,
        FIND_BLOCK,
        EXECUTE_TASK,
        UNKNOWN,
        ;
    }

}
