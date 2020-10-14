package pw.zakharov.amongcraft.api.member;

import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.stat.Statistic;
import pw.zakharov.amongcraft.api.Task;

import java.util.Set;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:37
 */
public interface Member {

    @NotNull UUID getId();

    @NotNull Set<Statistic<?>> getStats();

    @NotNull Set<Task> getTasks();

    default @NotNull Statistic<?> findStatByType(Statistic.Type type) {
        return getStats()
                .stream()
                .filter(stat -> stat.getType() == type)
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

}