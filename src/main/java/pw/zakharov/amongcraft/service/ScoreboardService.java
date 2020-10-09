package pw.zakharov.amongcraft.service;

import me.lucko.helper.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.10.2020 21:25
 */
public interface ScoreboardService {

    void register(@NotNull String id, @NotNull Scoreboard scoreboard);

    void unregister(@NotNull String id);

    Optional<Scoreboard> getScoreboard(@NotNull String id);

    @NotNull Set<Scoreboard> getScoreboards(@NotNull String id);

}