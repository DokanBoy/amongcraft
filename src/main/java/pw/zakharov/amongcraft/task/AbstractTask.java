package pw.zakharov.amongcraft.task;

import com.google.common.base.Objects;
import me.lucko.helper.utils.Log;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Task;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 15.10.2020 19:52
 */
public class AbstractTask implements Task {

    private final @NotNull TaskContext context;
    private final @NotNull Location startLocation;
    private final @NotNull Location taskLocation;

    private @NotNull State state;

    protected AbstractTask(@NotNull TaskContext context,
                           @NotNull Location startLocation,
                           @NotNull Location taskLocation) {
        this.context = context;
        this.startLocation = startLocation;
        this.taskLocation = taskLocation;

        this.state = State.NOT_COMPLETED;

        Log.info("Task created: " + toString());
    }

    @Override
    public @NotNull TaskContext getContext() {
        return context;
    }

    @Override
    public @NotNull Location getStartLocation() {
        return startLocation;
    }

    @Override
    public @NotNull Location getTaskLocation() {
        return taskLocation;
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void start() {
        this.state = State.STARTED;
    }

    @Override
    public void complete() {
        this.state = State.COMPLETED;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "context=" + context +
                ", startPosition=" + startLocation +
                ", taskLocation=" + taskLocation +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return Objects.equal(context, that.context) &&
                Objects.equal(startLocation, that.startLocation) &&
                Objects.equal(taskLocation, that.taskLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(context, startLocation, taskLocation, state);
    }

    protected abstract static class AbstractTaskContext implements TaskContext {

        private final @NotNull String name;

        protected AbstractTaskContext(@NotNull String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractTaskContext that = (AbstractTaskContext) o;
            return Objects.equal(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

    }

}
