package pw.zakharov.amongcraft.api.event.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.zakharov.amongcraft.api.Task;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 28.10.2020 19:48
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStartEvent extends Event {

    static HandlerList handlers = new HandlerList();

    @Getter @NonNull Player player;
    @Getter @NonNull Task task;

    public TaskStartEvent(@NonNull Player player, @NonNull Task task) {
        this.player = player;
        this.task = task;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
