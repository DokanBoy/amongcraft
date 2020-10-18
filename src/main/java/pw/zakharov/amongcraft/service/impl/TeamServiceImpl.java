package pw.zakharov.amongcraft.service.impl;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
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
            Set<Team> storedTeams = teamArenaMap.get(arenaName);
            if (storedTeams.stream().anyMatch(t -> t.getContext().getName().equals(team.getContext().getName()))) {
                Log.warn("Team with name " + team.getContext().getName() + " already register!");
                return;
            }
            storedTeams.add(team);
        } else {
            teamArenaMap.put(arenaName, ImmutableSet.of(team));
            Log.warn("Team with name " + team.getContext().getName() + " successfully registered!");
        }
    }

    @Override
    public void unregister(@NonNull String arenaName, @NonNull String teamName) {
        teamArenaMap.get(arenaName).removeIf(team -> team.getContext().getName().equals(teamName));
    }

    @Override
    public Optional<Team> getTeam(@NonNull String arenaName, @NonNull String teamName) {
        return teamArenaMap.get(arenaName).stream().filter(team -> team.getContext().getName().equals(teamName)).findFirst();
    }

    @Override
    public Optional<Team> getPlayerTeam(@NonNull Player player) {
        Collection<Set<Team>> storedTeams = teamArenaMap.values();
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