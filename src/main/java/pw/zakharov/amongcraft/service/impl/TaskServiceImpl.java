package pw.zakharov.amongcraft.service.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import pw.zakharov.amongcraft.api.Task;
import pw.zakharov.amongcraft.service.TaskService;

import javax.inject.Singleton;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 17:47
 */
@Singleton
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    @Getter @NonNull Set<Task> tasks;

    public TaskServiceImpl() {
        this.tasks = new LinkedHashSet<>();
    }

    @Override
    public void register(@NonNull Task task) {
        tasks.add(task);
    }

    @Override
    public void unregister(@NonNull String name) {
        tasks.removeIf(task -> task.getContext().getName().equals(name));
    }

    @Override
    public Optional<Task> getTask(@NonNull String name) {
        return tasks.stream()
                    .filter(task -> task.getContext().getName().equals(name))
                    .findFirst();
    }

}
