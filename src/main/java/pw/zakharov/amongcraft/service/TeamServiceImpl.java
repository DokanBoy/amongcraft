package pw.zakharov.amongcraft.service;

import me.lucko.helper.utils.Log;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.team.Team;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:02
 */
public class TeamServiceImpl implements TeamService {

    private final @NotNull Plugin plugin;
    private final @NotNull Set<Team> teams;

    public TeamServiceImpl(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.teams = new LinkedHashSet<>();
    }

    @Override
    public void register(@NotNull Team team) {
        if (teams.stream().anyMatch(t -> t.getData().getName().equals(team.getData().getName()))) {
            Log.warn("Team with name " + team.getData().getName() + " already register!");
            return;
        }
        teams.add(team);
    }

    @Override
    public void unregister(@NotNull String name) {
        teams.removeIf(team -> team.getData().getName().equals(name));
    }

    @Override
    public Optional<Team> getTeam(@NotNull String name) {
        return teams.stream().filter(team -> team.getData().getName().equals(name)).findFirst();
    }

    @Override
    public @NotNull Set<Team> getTeams() {
        return teams;
    }

}