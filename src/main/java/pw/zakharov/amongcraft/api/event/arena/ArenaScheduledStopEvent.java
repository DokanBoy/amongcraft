package pw.zakharov.amongcraft.api.event.arena;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.zakharov.amongcraft.api.Arena;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 19.10.2020 22:42
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaScheduledStopEvent extends Event {

    static HandlerList handlers = new HandlerList();

    @Getter @NonNull Arena arena;
    @Getter @NonNull Arena.StopCause cause;
    @Getter int afterSec;

    public ArenaScheduledStopEvent(@NonNull Arena arena, @NonNull Arena.StopCause cause, int afterSec) {
        this.arena = arena;
        this.cause = cause;
        this.afterSec = afterSec;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
