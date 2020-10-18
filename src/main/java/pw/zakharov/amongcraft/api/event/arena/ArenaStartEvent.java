package pw.zakharov.amongcraft.api.event.arena;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.zakharov.amongcraft.api.Arena;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 21:51
 */
public class ArenaStartEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final @NonNull Arena arena;

    public ArenaStartEvent(@NonNull Arena arena) {
        this.arena = arena;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NonNull Arena getArena() {
        return arena;
    }

}
