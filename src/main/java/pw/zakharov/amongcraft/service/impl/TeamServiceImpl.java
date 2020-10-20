package pw.zakharov.amongcraft.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.lucko.helper.utils.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.api.Team;
import pw.zakharov.amongcraft.service.TeamService;

import java.util.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.10.2020 22:02
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamServiceImpl implements TeamService {

    @NonNull Plugin plugin;
    @NonNull Map<String, Set<Team>> teamArenaMap;

    public TeamServiceImpl(@NonNull Plugin plugin) {
        this.plugin = plugin;
        this.teamArenaMap = new LinkedHashMap<>();
    }

    @Override
    public void register(@NonNull String arenaName, @NonNull Team team) {
        if (teamArenaMap.containsKey(arenaName)) {
            val storedTeams = teamArenaMap.get(arenaName);
            if (storedTeams.stream().anyMatch(t -> t.getContext().getName().equals(team.getContext().getName()))) {
                Log.info("Team with name " + team.getContext().getName() + " already register!");
                return;
            }
            storedTeams.add(team);
        } else {
            val singleTeam = new LinkedHashSet<Team>(1);
            singleTeam.add(team);
            teamArenaMap.put(arenaName, singleTeam);
        }
        Log.info("Team with name " + team.getContext().getName() + " successfully registered!");
    }

    @Override
    public void unregister(@NonNull String arenaName, @NonNull String teamName) {
        teamArenaMap.get(arenaName).removeIf(team -> team.getContext().getName().equals(teamName));
    }

    @Override
    public Optional<Team> getTeam(@NonNull String arenaName, @NonNull String teamName) {
        return teamArenaMap.get(arenaName)
                           .stream()
                           .filter(team -> team.getContext().getName().equals(teamName))
                           .findFirst();
    }

    @Override
    public Optional<Team> getTeam(@NonNull String arenaName, Team.@NonNull Role role) {
        return teamArenaMap.get(arenaName)
                           .stream()
                           .filter(team -> team.getContext().getRole() == role)
                           .findFirst();
    }

    @Override
    public Optional<Team> getPlayerTeam(@NonNull Player player) {
        val storedTeams = teamArenaMap.values();
        for (Set<Team> teams : storedTeams) {
            for (Team team : teams) {
                if (team.getPlayers().contains(player)) return Optional.of(team);
            }
        }
        return Optional.empty();
    }

    @Override
    public @NonNull Set<Team> getArenaTeams(@NonNull String arenaName) {
        return teamArenaMap.get(arenaName);
    }

}