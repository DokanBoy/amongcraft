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
import pw.zakharov.amongcraft.service.*;

import static pw.zakharov.amongcraft.api.arena.Arena.StopCause.UNKNOWN;

public final class AmongCraft extends ExtendedJavaPlugin {

    private World amongWorld;

    private static TeamService teamService;
    private static ArenaService arenaService;
    private static ScoreboardService scoreboardService;

    @Override
    protected void enable() {
        WorldCreator wc = new WorldCreator("among-us_shuttle");
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        amongWorld = Helper.server().createWorld(wc);

        teamService = new TeamServiceImpl(this);
        arenaService = new ArenaServiceImpl(this);
        scoreboardService = new ScoreboardServiceImpl(this);

        arenaService.register(new SingleArena("Shuttle", amongWorld, new Location(amongWorld, 0, 4, 0), 2));

        Arena shuttleArena = arenaService.getArena("Shuttle").orElseThrow(NullPointerException::new);

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    shuttleArena.start(5);
                    shuttleArena.randomJoin(context.sender());
                    context.reply("Arena state: " + shuttleArena.getState().name());
                })
                .register("astart");

        Commands.create()
                .assertPlayer()
                .handler(context -> {
                    shuttleArena.stop(UNKNOWN, 5);
                    shuttleArena.disable();
                })
                .register("astop");
    }

    @Override
    protected void disable() {
        arenaService.getArenas().forEach(arena -> {
            arena.stop(UNKNOWN);
            arena.disable();
        });
    }

    public static TeamService getTeamService() {
        return teamService;
    }

    public static ArenaService getArenaService() {
        return arenaService;
    }

    public static ScoreboardService getScoreboardService() {
        return scoreboardService;
    }

}
