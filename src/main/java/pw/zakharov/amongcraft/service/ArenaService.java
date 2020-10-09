package pw.zakharov.amongcraft.service;

import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.arena.Arena;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.10.2020 20:11
 */
public interface ArenaService {

    void register(@NotNull Arena arena);

    void unregister(@NotNull String name);

    Optional<Arena> getArena(@NotNull String name);

    @NotNull Set<Arena> getArenas();

}