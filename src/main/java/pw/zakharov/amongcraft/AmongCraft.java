package pw.zakharov.amongcraft;

import me.lucko.helper.Commands;
import me.lucko.helper.Helper;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import pw.zakharov.amongcraft.api.arena.Arena;
import pw.zakharov.amongcraft.arena.SingleArena;

public final class AmongCraft extends ExtendedJavaPlugin {

    private Arena singleArena;
    private World amongWorld;

    @Override
    protected void enable() {
        WorldCreator wc = new WorldCreator("among-us_shuttle");
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        amongWorld = Helper.server().createWorld(wc);

        singleArena = new SingleArena(
                "Shuttle",
                wc.name(),
                new Location(amongWorld, 0, 4, 0),
                2);

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    singleArena.enable();
                    singleArena.start(5);
                    singleArena.randomJoin(context.sender());
                    context.reply("Arena state: " + singleArena.getState().name());
                })
                .register("start");

        Commands.create()
                .assertPlayer()
                .handler(context -> {

                    singleArena.stop(Arena.StopCause.UNKNOWN, 5);
                    singleArena.disable();
                })
                .register("stop");
    }

    @Override
    protected void disable() {
        singleArena.stop(Arena.StopCause.UNKNOWN);
        singleArena.disable();
    }

}
