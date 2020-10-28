package pw.zakharov.amongcraft.inject;

import com.google.inject.AbstractModule;
import pw.zakharov.amongcraft.arena.loader.ArenaLoader;
import pw.zakharov.amongcraft.arena.loader.ArenaLoaderImpl;
import pw.zakharov.amongcraft.service.ArenaService;
import pw.zakharov.amongcraft.service.ScoreboardService;
import pw.zakharov.amongcraft.service.TaskService;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.service.impl.ArenaServiceImpl;
import pw.zakharov.amongcraft.service.impl.ScoreboardServiceImpl;
import pw.zakharov.amongcraft.service.impl.TaskServiceImpl;
import pw.zakharov.amongcraft.service.impl.TeamServiceImpl;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 28.10.2020 13:28
 */
public class AmongModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TaskService.class).to(TaskServiceImpl.class);
        bind(TeamService.class).to(TeamServiceImpl.class);
        bind(ArenaService.class).to(ArenaServiceImpl.class);
        bind(ScoreboardService.class).to(ScoreboardServiceImpl.class);

        bind(ArenaLoader.class).to(ArenaLoaderImpl.class);
    }

}
