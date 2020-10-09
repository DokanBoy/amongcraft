package pw.zakharov.amongcraft.api.event.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 10.10.2020 1:50
 */
public class PlayerLeaveFromLobbyEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}