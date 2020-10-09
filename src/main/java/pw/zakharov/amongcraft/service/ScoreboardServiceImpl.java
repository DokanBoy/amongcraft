package pw.zakharov.amongcraft.service;

import me.lucko.helper.scoreboard.Scoreboard;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 21:29
 */
public class ScoreboardServiceImpl implements ScoreboardService {

    private final @NotNull Plugin plugin;
    private final @NotNull Map<String, Scoreboard> scoreboards;

    public ScoreboardServiceImpl(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();
    }

    @Override
    public void register(@NotNull String id, @NotNull Scoreboard scoreboard) {
        scoreboards.put(id, scoreboard);
    }

    @Override
    public void unregister(@NotNull String id) {
        scoreboards.remove(id);
    }

    @Override
    public Optional<Scoreboard> getScoreboard(@NotNull String id) {
        return Optional.ofNullable(scoreboards.get(id));
    }

    @Override
    public @NotNull Set<Scoreboard> getScoreboards(@NotNull String id) {
        return new HashSet<>(scoreboards.values());
    }

}