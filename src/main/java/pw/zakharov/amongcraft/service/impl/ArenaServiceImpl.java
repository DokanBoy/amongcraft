package pw.zakharov.amongcraft.service.impl;

import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.lucko.helper.utils.Log;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.service.ArenaService;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 07.10.2020 23:51
 */
@Singleton
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaServiceImpl implements ArenaService {

    @Getter @NonNull Set<Arena> arenas;

    public ArenaServiceImpl() {
        this.arenas = new LinkedHashSet<>();
    }

    @Override
    public void register(@NonNull Arena arena) {
        if (arenas.stream().anyMatch(a -> a.getContext().getName().equals(arena.getContext().getName()))) {
            Log.warn("Arena with name " + arena.getContext().getName() + " already register!");
            return;
        }
        arenas.add(arena);
        arena.enable();
    }

    @Override
    public void unregister(@NonNull String name) {
        val arena = arenas.stream()
                          .filter(a -> a.getContext().getName().equals(name))
                          .findFirst();

        arena.ifPresent(a -> {
            a.stop(Arena.StopCause.UNKNOWN);
            a.disable();
            arenas.remove(a);
        });
    }

    @Override
    public Optional<Arena> getArena(@NonNull String name) {
        return arenas.stream()
                     .filter(a -> a.getContext().getName().equals(name))
                     .findFirst();
    }

}