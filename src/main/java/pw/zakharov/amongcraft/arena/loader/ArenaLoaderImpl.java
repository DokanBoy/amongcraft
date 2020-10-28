package pw.zakharov.amongcraft.arena.loader;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import me.lucko.helper.config.ConfigFactory;
import me.lucko.helper.config.ConfigurationNode;
import me.lucko.helper.serialize.Point;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.arena.SingleArena;
import pw.zakharov.amongcraft.data.ArenaData;
import pw.zakharov.amongcraft.inject.annotations.ArenasPath;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.util.CollectionUtils;

import java.nio.file.Path;

import static pw.zakharov.amongcraft.api.Team.Role.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 19.10.2020 17:01
 */
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ArenaLoaderImpl implements ArenaLoader {

    @NonNull Path dir;
    @Inject @NonFinal TeamService teamService;

    @Inject
    public ArenaLoaderImpl(@ArenasPath Path dir) {
        this.dir = dir;
    }

    @Override
    @SneakyThrows
    public @NonNull Arena loadArena(@NonNull String arena) {
        ArenaData arenaData = ConfigFactory.hocon()
                                           .load(dir.resolve(arena.toLowerCase() + ".conf"))
                                           .getValue(TypeToken.of(ArenaData.class));
        if (arenaData == null) {
            throw new NullPointerException("Arena file not found in " + dir + " path!");
        }
        return loadArena(arenaData);
    }

    // todo: refactoring???
    @Override
    public @NonNull void saveArena(@NonNull Arena arena) {
        val arenaContext = arena.getContext();
        val spectatorTeam = teamService.getTeam(arenaContext.getName(), SPECTATOR)
                                       .orElseThrow(NullPointerException::new);
        val innocentTeam = teamService.getTeam(arenaContext.getName(), INNOCENT)
                                      .orElseThrow(NullPointerException::new);
        val imposterTeam = teamService.getTeam(arenaContext.getName(), IMPOSTER)
                                      .orElseThrow(NullPointerException::new);
        val arenaData = ArenaData.builder()
                                 .worldName(arena.getWorld().getName())
                                 .name(arenaContext.getName())
                                 .lobbyPoint(Point.of(arenaContext.getLobby()))
                                 .spectatorPoint(Point.of(spectatorTeam.getNextSpawn()))
                                 .innocentPoints(CollectionUtils
                                         .locations2Points(innocentTeam.getSpawns()))
                                 .maxInnocents(innocentTeam.getMaxSize())
                                 .imposterPoints(CollectionUtils
                                         .locations2Points(imposterTeam.getSpawns()))
                                 .maxImposters(imposterTeam.getMaxSize())
                                 .swordCooldown(5)
                                 .build();

        saveDataToFile(arenaData);
    }

    @SneakyThrows
    private void saveDataToFile(ArenaData arenaData) {
        ConfigurationNode arenaDataNode = ConfigurationNode.root();
        arenaDataNode.setValue(TypeToken.of(ArenaData.class), arenaData);
        ConfigFactory.hocon().loader(dir.resolve(arenaData.getName() + ".conf")).save(arenaDataNode);
    }

    private @NonNull Arena loadArena(ArenaData arenaData) {
        val wc = new WorldCreator(arenaData.getWorldName()).type(WorldType.FLAT).generateStructures(false);
        val world = Bukkit.createWorld(wc);

        return new SingleArena(world, new SingleArena.SingleArenaContext(teamService,
                arenaData.getName(),
                arenaData.getLobbyPoint().toLocation(), arenaData.getSpectatorPoint().toLocation(),
                CollectionUtils.points2Locations(arenaData.getInnocentPoints()), arenaData.getMaxInnocents(),
                CollectionUtils.points2Locations(arenaData.getImposterPoints()), arenaData.getMaxImposters()));
    }

}