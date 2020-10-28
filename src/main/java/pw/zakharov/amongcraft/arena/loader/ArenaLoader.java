package pw.zakharov.amongcraft.arena.loader;

import lombok.NonNull;
import pw.zakharov.amongcraft.api.Arena;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 17.10.2020 22:20
 */
public interface ArenaLoader {

    void saveArena(@NonNull Arena arena);

    @NonNull Arena loadArena(@NonNull String arena);

}
