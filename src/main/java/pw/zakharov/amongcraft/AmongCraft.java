package pw.zakharov.amongcraft;

import me.lucko.helper.Commands;
import me.lucko.helper.Helper;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
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
                new Location(amongWorld, 0, 0, 0),
                2);

        Commands.create()
                .assertPlayer()
                .handler(context -> singleArena.stop(10))
                .register("astop");

        Commands.create()
                .assertPlayer()
                .handler(context -> singleArena.disable())
                .register("adisable");

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    singleArena.enable();
                    singleArena.start(10);
                    singleArena.randomJoin(context.sender());
                })
                .register("among");
    }

    @Override
    protected void disable() {
        super.disable();
    }

}
