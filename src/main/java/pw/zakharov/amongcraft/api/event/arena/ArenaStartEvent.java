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
 * Date: 06.10.2020 21:51
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaStartEvent extends Event {

    static HandlerList handlers = new HandlerList();

    @Getter @NonNull Arena arena;

    public ArenaStartEvent(@NonNull Arena arena) {
        this.arena = arena;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
