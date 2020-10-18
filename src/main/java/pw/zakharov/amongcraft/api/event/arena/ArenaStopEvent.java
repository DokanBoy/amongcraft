package pw.zakharov.amongcraft.api.event.arena;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.zakharov.amongcraft.api.Arena;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 21:52
 */
public class ArenaStopEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final @NonNull Arena arena;
    private final @NonNull Arena.StopCause cause;

    public ArenaStopEvent(@NonNull Arena arena, @NonNull Arena.StopCause cause) {
        this.arena = arena;
        this.cause = cause;
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

    public @NonNull Arena.StopCause getCause() {
        return cause;
    }

}
