package pw.zakharov.amongcraft.api.event.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 21:05
 */
public class PlayerJoinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
