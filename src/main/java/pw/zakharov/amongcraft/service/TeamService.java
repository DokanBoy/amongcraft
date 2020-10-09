package pw.zakharov.amongcraft.service;

import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.arena.Team;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.10.2020 21:32
 */
public interface TeamService {

    void register(@NotNull Team team);

    void unregister(@NotNull String name);

    Optional<Team> getTeam(@NotNull String name);

    @NotNull Set<Team> getTeams();

}