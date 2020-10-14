package pw.zakharov.amongcraft.api.event.arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Arena;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 21:52
 */
public class ArenaStopEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final @NotNull Arena arena;
    private final @NotNull Arena.StopCause cause;

    public ArenaStopEvent(@NotNull Arena arena, @NotNull Arena.StopCause cause) {
        this.arena = arena;
        this.cause = cause;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NotNull Arena getArena() {
        return arena;
    }

    public Arena.@NotNull StopCause getCause() {
        return cause;
    }

}
