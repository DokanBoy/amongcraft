package pw.zakharov.amongcraft.service;

import lombok.NonNull;
import pw.zakharov.amongcraft.api.Task;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 17:45
 */
public interface TaskService {

    void register(@NonNull Task task);

    void unregister(@NonNull String name);

    Optional<Task> getTask(@NonNull String name);

    @NonNull Set<Task> getTasks();

}
