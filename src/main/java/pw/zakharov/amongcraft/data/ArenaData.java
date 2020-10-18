package pw.zakharov.amongcraft.data;

import me.lucko.helper.config.objectmapping.Setting;
import me.lucko.helper.config.objectmapping.serialize.ConfigSerializable;
import me.lucko.helper.serialize.Position;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 15.10.2020 22:42
 */
@ConfigSerializable
public class ArenaData {

    public ArenaData() {

    }

    public ArenaData(@NotNull String name, @NotNull String worldName,
                     int imposterAmount, int swordCooldown,
                     @NotNull Position lobbyPosition, @NotNull List<Position> innocentPositions,
                     @NotNull List<Position> imposterPositions, @NotNull List<Position> spectatorPositions) {
        this.name = name;
        this.worldName = worldName;
        this.imposterAmount = imposterAmount;
        this.swordCooldown = swordCooldown;
        this.lobbyPosition = lobbyPosition;
        this.innocentPositions = innocentPositions;
        this.imposterPositions = imposterPositions;
        this.spectatorPositions = spectatorPositions;
    }

    /* Arena info */
    @Setting(value = "arena-name")
    private String name;

    @Setting(value = "world-name")
    private String worldName;

    /* Imposter settings */
    @Setting(value = "team-imposter-size")
    private int imposterAmount;

    @Setting(value = "team-imposter-sword-cooldown")
    private int swordCooldown;

    /* Positions */
    @Setting(value = "position-lobby")
    private Position lobbyPosition;

    @Setting(value = "position-innocent")
    private List<Position> innocentPositions;

    @Setting(value = "position-imposter")
    private List<Position> imposterPositions;

    @Setting(value = "position-spectator")
    private List<Position> spectatorPositions;

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getImposterAmount() {
        return imposterAmount;
    }

    public int getSwordCooldown() {
        return swordCooldown;
    }

    public Position getLobbyPosition() {
        return lobbyPosition;
    }

    public List<Position> getInnocentPositions() {
        return innocentPositions;
    }

    public List<Position> getImposterPositions() {
        return imposterPositions;
    }

    public List<Position> getSpectatorPositions() {
        return spectatorPositions;
    }

    @Override
    public String toString() {
        return "ArenaData{" +
                "name='" + name + '\'' +
                ", worldName='" + worldName + '\'' +
                ", imposterAmount=" + imposterAmount +
                ", swordCooldown=" + swordCooldown +
                ", lobbyPosition=" + lobbyPosition +
                ", innocentPositions=" + innocentPositions +
                ", imposterPositions=" + imposterPositions +
                ", spectatorPositions=" + spectatorPositions +
                '}';
    }

}