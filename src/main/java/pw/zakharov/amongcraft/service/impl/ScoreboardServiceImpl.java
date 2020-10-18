package pw.zakharov.amongcraft.service.impl;

import lombok.NonNull;
import me.lucko.helper.scoreboard.Scoreboard;
import me.lucko.helper.utils.Log;
import org.bukkit.plugin.Plugin;
import pw.zakharov.amongcraft.service.ScoreboardService;

import java.util.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 21:29
 */
public class ScoreboardServiceImpl implements ScoreboardService {

    private final @NonNull Plugin plugin;
    private final @NonNull Map<String, Scoreboard> scoreboards;

    public ScoreboardServiceImpl(@NonNull Plugin plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();
    }

    @Override
    public void register(@NonNull String id, @NonNull Scoreboard scoreboard) {
        if (scoreboards.containsKey(id)) {
            Log.warn("Scoreboard with ID " + id + " already register!");
            return;
        }
        scoreboards.put(id, scoreboard);
    }

    @Override
    public void unregister(@NonNull String id) {
        scoreboards.remove(id);
    }

    @Override
    public Optional<Scoreboard> getScoreboard(@NonNull String id) {
        return Optional.ofNullable(scoreboards.get(id));
    }

    @Override
    public @NonNull Set<Scoreboard> getScoreboards() {
        return new HashSet<>(scoreboards.values());
    }

}