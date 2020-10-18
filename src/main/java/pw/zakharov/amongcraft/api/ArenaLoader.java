package pw.zakharov.amongcraft.api;

import lombok.NonNull;
import pw.zakharov.amongcraft.arena.loader.ArenaLoaderImpl;

import java.nio.file.Path;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 17.10.2020 22:20
 */
public interface ArenaLoader {

    static ArenaLoader createLoader(@NonNull Path dir, @NonNull String name) {
        return new ArenaLoaderImpl(dir, name);
    }

    @NonNull Arena getArena();

}
