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
public class ArenaScheduledStartEvent extends Event {

    static HandlerList handlers = new HandlerList();

    @Getter @NonNull Arena arena;
    @Getter int afterSec;

    public ArenaScheduledStartEvent(@NonNull Arena arena, int afterSec) {
        this.arena = arena;
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
