package pw.zakharov.amongcraft.arena.loader;

import com.google.common.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.Helper;
import me.lucko.helper.config.ConfigFactory;
import me.lucko.helper.config.commented.CommentedConfigurationNode;
import me.lucko.helper.config.hocon.HoconConfigurationLoader;
import me.lucko.helper.config.objectmapping.ObjectMappingException;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.ArenaLoader;
import pw.zakharov.amongcraft.arena.SingleArena;
import pw.zakharov.amongcraft.data.ArenaData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 17.10.2020 22:20
 */
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArenaLoaderImpl implements ArenaLoader {

    @NonNull Path dir;
    @NonNull String name;
    @NonNull Arena arena;
    @NonNull ArenaData arenaData;

    public ArenaLoaderImpl(@NonNull Path dir, @NonNull String name) {
        this.dir = dir;
        this.name = name.toLowerCase() + ".conf";
        this.arenaData = loadData().orElseThrow(NullPointerException::new);
        this.arena = loadArena();
    }

    @Override
    public @NonNull Arena getArena() {
        return arena;
    }

    private Optional<ArenaData> loadData() {
        HoconConfigurationLoader arenaLoader = ConfigFactory.hocon().loader(dir.resolve(name));
        CommentedConfigurationNode arenaNode;
        ArenaData arenaData = null;
        try {
            arenaNode = arenaLoader.load();
            arenaData = arenaNode.getValue(TypeToken.of(ArenaData.class));
        } catch (ObjectMappingException | IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(arenaData);
    }

    private @NonNull Arena loadArena() {
        WorldCreator wc = new WorldCreator(arenaData.getWorldName()).type(WorldType.FLAT).generateStructures(false);
        World world = Helper.server().createWorld(wc);

        return new SingleArena(world, new SingleArena.SingleArenaContext(arenaData.getName(),
                arenaData.getLobbyLocation(), arenaData.getSpectatorLocation(),
                arenaData.getInnocentLocations(), arenaData.getInnocentAmount(),
                arenaData.getImposterLocations(), arenaData.getImposterAmount()));
    }

}