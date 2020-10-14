package pw.zakharov.amongcraft.service;

import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Task;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 17:45
 */
public interface TaskService {

    void register(@NotNull Task task);

    void unregister(@NotNull String name);

    Optional<Task> getTask(@NotNull String name);

    @NotNull Set<Task> getTasks();

}
