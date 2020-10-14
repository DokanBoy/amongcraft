package pw.zakharov.amongcraft.service.impl;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Task;
import pw.zakharov.amongcraft.service.TaskService;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 14.10.2020 17:47
 */
public class TaskServiceImpl implements TaskService {

    private final @NotNull Plugin plugin;
    private final @NotNull Set<Task> tasks;

    public TaskServiceImpl(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.tasks = new LinkedHashSet<>();
    }

    @Override
    public void register(@NotNull Task task) {
        tasks.add(task);
    }

    @Override
    public void unregister(@NotNull String name) {
        tasks.removeIf(task -> task.getContext().getName().equals(name));
    }

    @Override
    public Optional<Task> getTask(@NotNull String name) {
        return tasks
                .stream()
                .filter(task -> task.getContext().getName().equals(name))
                .findFirst();
    }

    @Override
    public @NotNull Set<Task> getTasks() {
        return tasks;
    }

}
