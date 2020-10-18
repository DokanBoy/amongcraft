package pw.zakharov.amongcraft.service;

import lombok.NonNull;
import me.lucko.helper.scoreboard.Scoreboard;

import java.util.Optional;
import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.10.2020 21:25
 */
public interface ScoreboardService {

    void register(@NonNull String id, @NonNull Scoreboard scoreboard);

    void unregister(@NonNull String id);

    Optional<Scoreboard> getScoreboard(@NonNull String id);

    @NonNull Set<Scoreboard> getScoreboards();

}