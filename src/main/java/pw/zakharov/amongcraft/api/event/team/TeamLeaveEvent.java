package pw.zakharov.amongcraft.api.event.team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.zakharov.amongcraft.api.Team;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 19.10.2020 22:09
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamLeaveEvent extends Event {

    static HandlerList handlers = new HandlerList();

    @Getter @NonNull Player player;
    @Getter @NonNull Team team;

    public TeamLeaveEvent(@NonNull Player player, @NonNull Team team) {
        this.player = player;
        this.team = team;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
