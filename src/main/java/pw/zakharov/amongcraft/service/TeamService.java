package pw.zakharov.amongcraft.service;

import lombok.NonNull;
import org.bukkit.entity.Player;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.Team;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.10.2020 21:32
 */
public interface TeamService {

    void register(@NonNull String arenaName, @NonNull Team team);

    void unregister(@NonNull String arenaName, @NonNull String teamName);

    Optional<Team> getTeam(@NonNull String arenaName, @NonNull String teamName);

    Optional<Team> getTeam(@NonNull String arenaName, @NonNull Team.Role role);

    static Team getTeam(@NonNull Arena arena, @NonNull Team.Role role) {
        return arena
                .getContext()
                .getTeams()
                .stream()
                .filter(team -> team.getContext().getRole() == role)
                .findFirst().orElseThrow(NullPointerException::new);
    }

    Optional<Team> getPlayerTeam(@NonNull Player player);

    @NonNull Set<Team> getArenaTeams(@NonNull String arenaName);

}