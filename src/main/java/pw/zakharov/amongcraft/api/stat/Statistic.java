package pw.zakharov.amongcraft.api.stat;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:38
 */
public interface Statistic {

    String getName();

    Type getType();

    enum Type {
        KILL_INNOCENT,
        FIND_BLOCK,
        EXECUTE_TASK,
        UNKNOWN,
        ;
    }

}
