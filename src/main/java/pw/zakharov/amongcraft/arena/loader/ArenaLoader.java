package pw.zakharov.amongcraft.arena.loader;

import lombok.NonNull;
import me.lucko.helper.Helper;
import pw.zakharov.amongcraft.api.Arena;

import java.nio.file.Path;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 17.10.2020 22:20
 */
public interface ArenaLoader {

    @NonNull Path DEFAULT_ARENA_PATH = Helper.hostPlugin().getDataFolder().toPath().resolve("arenas");

    static ArenaLoader createLoader(@NonNull Path dir, @NonNull String name) {
        return new ArenaLoaderImpl(dir, name);
    }

    void saveArena(@NonNull Arena arena);

    @NonNull Arena getArena();

}
