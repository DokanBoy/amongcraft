package pw.zakharov.amongcraft.api;

import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.arena.loader.ArenaLoaderImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 17.10.2020 22:20
 */
public interface ArenaLoader {

    static ArenaLoader createLoader(@NotNull Path dir, @NotNull String name) {
        return new ArenaLoaderImpl(dir, name);
    }

    @NotNull Arena getArena();

}
