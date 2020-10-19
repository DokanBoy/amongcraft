package pw.zakharov.amongcraft.arena.loader;

import com.google.common.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import me.lucko.helper.Helper;
import me.lucko.helper.config.ConfigFactory;
import me.lucko.helper.config.commented.CommentedConfigurationNode;
import me.lucko.helper.config.hocon.HoconConfigurationLoader;
import me.lucko.helper.config.objectmapping.ObjectMappingException;
import me.lucko.helper.serialize.Point;
import me.lucko.helper.utils.Log;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import pw.zakharov.amongcraft.AmongCraft;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.Team;
import pw.zakharov.amongcraft.arena.SingleArena;
import pw.zakharov.amongcraft.data.ArenaData;
import pw.zakharov.amongcraft.service.TeamService;
import pw.zakharov.amongcraft.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static pw.zakharov.amongcraft.api.Team.Role.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 19.10.2020 17:01
 */
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
/* package */ final class ArenaLoaderImpl implements ArenaLoader {

    @NonNull HoconConfigurationLoader hoconLoader;

    @NonNull @NonFinal Arena arena;
    @NonNull @NonFinal CommentedConfigurationNode arenaDataNode;

    public ArenaLoaderImpl(@NonNull Path dir, @NonNull String name) {
        this.hoconLoader = ConfigFactory.hocon().loader(dir.resolve(name.toLowerCase() + ".conf"));
        this.arenaDataNode = hoconLoader.createEmptyNode();
    }

    @Override
    public @NonNull Arena getArena() {
        ArenaData arenaData = loadData().orElseThrow(NullPointerException::new);
        this.arena = loadArena(arenaData);

        return arena;
    }

    @Override
    public @NonNull void saveArena(@NonNull Arena arena) {
        this.arena = arena;

        final @NonNull TeamService teamService = AmongCraft.getTeamService();
        final @NonNull Arena.ArenaContext arenaContext = arena.getContext();
        final @NonNull Team spectatorTeam = teamService.getTeam(arenaContext.getName(), SPECTATOR)
                                                       .orElseThrow(NullPointerException::new);
        final @NonNull Team innocentTeam = teamService.getTeam(arenaContext.getName(), INNOCENT)
                                                      .orElseThrow(NullPointerException::new);
        final @NonNull Team imposterTeam = teamService.getTeam(arenaContext.getName(), IMPOSTER)
                                                      .orElseThrow(NullPointerException::new);
        final ArenaData arenaData = ArenaData.builder()
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

    private void saveDataToFile(ArenaData arenaData) {
        try {
            arenaDataNode.setValue(TypeToken.of(ArenaData.class), arenaData);
            hoconLoader.save(arenaDataNode);
        } catch (ObjectMappingException | IOException e) {
            Log.warn(e.getMessage());
        }
    }

    private Optional<ArenaData> loadData() {
        ArenaData arenaData = null;
        try {
            arenaDataNode = hoconLoader.load();
            arenaData = arenaDataNode.getValue(TypeToken.of(ArenaData.class));
        } catch (ObjectMappingException | IOException e) {
            Log.warn(e.getMessage());
        }
        return Optional.ofNullable(arenaData);
    }

    private @NonNull Arena loadArena(ArenaData arenaData) {
        final WorldCreator wc = new WorldCreator(arenaData.getWorldName()).type(WorldType.FLAT)
                                                                          .generateStructures(false);
        final World world = Helper.server().createWorld(wc);

        return new SingleArena(world, new SingleArena.SingleArenaContext(arenaData.getName(),
                arenaData.getLobbyPoint().toLocation(), arenaData.getSpectatorPoint().toLocation(),
                CollectionUtils.points2Locations(arenaData.getInnocentPoints()), arenaData.getMaxInnocents(),
                CollectionUtils.points2Locations(arenaData.getImposterPoints()), arenaData.getMaxImposters()));
    }

}