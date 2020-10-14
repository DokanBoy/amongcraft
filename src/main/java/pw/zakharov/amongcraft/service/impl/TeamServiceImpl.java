package pw.zakharov.amongcraft.service.impl;

import me.lucko.helper.utils.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.Arena;
import pw.zakharov.amongcraft.api.Team;
import pw.zakharov.amongcraft.service.TeamService;

import java.util.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:02
 */
public class TeamServiceImpl implements TeamService {

    private final @NotNull Plugin plugin;
    private final @NotNull Set<Team> teams;
    private final @NotNull Map<Arena, Set<Team>> teamArenaMap;

    public TeamServiceImpl(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.teams = new LinkedHashSet<>();
        this.teamArenaMap = new LinkedHashMap<>();
    }

    @Override
    public void register(@NotNull Team team) {
        if (teams.stream().anyMatch(t -> t.getContext().getName().equals(team.getContext().getName()))) {
            Log.warn("Team with name " + team.getContext().getName() + " already register!");
            return;
        }
        teams.add(team);
    }

    @Override
    public void unregister(@NotNull String name) {
        teams.removeIf(team -> team.getContext().getName().equals(name));
    }

    @Override
    public Optional<Team> getTeam(@NotNull String name) {
        return teams.stream().filter(team -> team.getContext().getName().equals(name)).findFirst();
    }

    @Override
    public Optional<Team> getPlayerTeam(@NotNull Player player) {
        return teams.stream()
                .filter(team -> team.getPlayers().contains(player))
                .findFirst();
    }

    @Override
    public @NotNull Set<Team> getTeams() {
        return teams;
    }

}