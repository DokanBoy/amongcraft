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
 * Date: 06.10.2020 21:52
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaStopEvent extends Event {

    static HandlerList handlers = new HandlerList();

    @Getter @NonNull Arena arena;
    @Getter @NonNull Arena.StopCause cause;

    public ArenaStopEvent(@NonNull Arena arena, @NonNull Arena.StopCause cause) {
        this.arena = arena;
        this.cause = cause;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
