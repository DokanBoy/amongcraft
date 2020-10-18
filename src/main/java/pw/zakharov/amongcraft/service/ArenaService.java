package pw.zakharov.amongcraft.service;

import lombok.NonNull;
import pw.zakharov.amongcraft.api.Arena;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.10.2020 20:11
 */
public interface ArenaService {

    void register(@NonNull Arena arena);

    void unregister(@NonNull String name);

    Optional<Arena> getArena(@NonNull String name);

    @NonNull Set<Arena> getArenas();

}