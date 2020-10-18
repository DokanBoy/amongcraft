package pw.zakharov.amongcraft.arena.loader;

import com.google.common.reflect.TypeToken;
import me.lucko.helper.Helper;
import me.lucko.helper.config.ConfigFactory;
import me.lucko.helper.config.commented.CommentedConfigurationNode;
import me.lucko.helper.config.hocon.HoconConfigurationLoader;
import me.lucko.helper.config.objectmapping.ObjectMappingException;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.jetbrains.annotations.NotNull;
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
public class ArenaLoaderImpl implements ArenaLoader {

    private final @NotNull Path dir;
    private final @NotNull String name;

    private final @NotNull Arena arena;
    private final @NotNull ArenaData arenaData;

    public ArenaLoaderImpl(@NotNull Path dir, @NotNull String name) {
        this.dir = dir;
        this.name = name.toLowerCase() + ".conf";
        this.arenaData = loadData().orElseThrow(NullPointerException::new);
        this.arena = loadArena();
    }

    @Override
    public @NotNull Arena getArena() {
        return arena;
    }

    private Optional<ArenaData> loadData() {
        final HoconConfigurationLoader arenaLoader = ConfigFactory.hocon().loader(dir.resolve(name));
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

    private @NotNull Arena loadArena() {
        WorldCreator wc = new WorldCreator(arenaData.getWorldName()).type(WorldType.FLAT).generateStructures(false);
        World world = Helper.server().createWorld(wc);

        return new SingleArena(arenaData.getName(), world, arenaData.getLobbyPosition().toLocation());
    }

}