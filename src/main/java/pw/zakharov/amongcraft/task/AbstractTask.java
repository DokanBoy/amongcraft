package pw.zakharov.amongcraft.task;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import me.lucko.helper.utils.Log;
import org.bukkit.Location;
import pw.zakharov.amongcraft.api.Task;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 15.10.2020 19:52
 */
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AbstractTask implements Task {

    @Getter @NonNull TaskContext context;
    @Getter @NonNull Location startLocation;
    @Getter @NonNull Location taskLocation;

    @Getter @NonNull @NonFinal State state;

    protected AbstractTask(@NonNull TaskContext context,
                           @NonNull Location startLocation,
                           @NonNull Location taskLocation) {
        this.context = context;
        this.startLocation = startLocation;
        this.taskLocation = taskLocation;

        this.state = State.NOT_COMPLETED;

        Log.info("Task created: " + toString());
    }

    @Override
    public void start() {
        this.state = State.STARTED;
    }

    @Override
    public void complete() {
        this.state = State.COMPLETED;
    }

    @ToString
    @EqualsAndHashCode
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    protected abstract static class AbstractTaskContext implements TaskContext {

        @Getter @NonNull String name;

        protected AbstractTaskContext(@NonNull String name) {
            this.name = name;
        }

    }

}
