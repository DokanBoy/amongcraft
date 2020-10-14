package pw.zakharov.amongcraft.api.stat;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:38
 */
public interface Statistic<V> {

    @NotNull String getName();

    @NotNull Type getType();

    @NotNull V getValue();

    enum Type {
        KILL_INNOCENT,
        FIND_BLOCK,
        EXECUTE_TASK,
        UNKNOWN,
        ;
    }

}
