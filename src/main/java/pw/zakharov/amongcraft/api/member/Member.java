package pw.zakharov.amongcraft.api.member;

import lombok.NonNull;
import pw.zakharov.amongcraft.api.Task;
import pw.zakharov.amongcraft.api.statistic.Statistic;

import java.util.Set;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:37
 */
public interface Member {

    @NonNull UUID getId();

    @NonNull Set<Statistic<?>> getStats();

    @NonNull Set<Task> getTasks();

    default @NonNull Statistic<?> findStatByType(@NonNull Statistic.Type type) {
        return getStats()
                .stream()
                .filter(stat -> stat.getType() == type)
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

}