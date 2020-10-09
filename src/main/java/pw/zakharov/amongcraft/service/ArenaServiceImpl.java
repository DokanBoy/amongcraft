package pw.zakharov.amongcraft.service;

import me.lucko.helper.utils.Log;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.arena.Arena;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 07.10.2020 23:51
 */
public class ArenaServiceImpl implements ArenaService {

    private final @NotNull Plugin plugin;
    private final @NotNull Set<Arena> arenas;

    public ArenaServiceImpl(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.arenas = new LinkedHashSet<>();
    }

    @Override
    public void register(@NotNull Arena arena) {
        if (arenas.stream().anyMatch(a -> a.getName().equals(arena.getName()))) {
            Log.warn("Arena with name " + arena.getName() + " already register!");
            return;
        }
        arenas.add(arena);
        arena.enable();
    }

    @Override
    public void unregister(@NotNull String name) {
        Optional<Arena> arena = arenas.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();

        arena.ifPresent(a -> {
            a.stop(Arena.StopCause.UNKNOWN);
            a.disable();
            arenas.remove(a);
        });
    }

    @Override
    public Optional<Arena> getArena(@NotNull String name) {
        return arenas.stream().filter(a -> a.getName().equals(name)).findFirst();
    }

    @Override
    public @NotNull Set<Arena> getArenas() {
        return arenas;
    }

}