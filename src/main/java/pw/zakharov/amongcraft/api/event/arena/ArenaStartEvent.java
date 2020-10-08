package pw.zakharov.amongcraft.api.event.arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 21:51
 */
public class ArenaStartEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
