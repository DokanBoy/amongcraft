package pw.zakharov.amongcraft.service.impl;

import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import me.lucko.helper.scoreboard.Scoreboard;
import me.lucko.helper.utils.Log;
import pw.zakharov.amongcraft.service.ScoreboardService;

import java.util.*;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 21:29
 */
@Singleton
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScoreboardServiceImpl implements ScoreboardService {

    @NonNull Map<String, Scoreboard> scoreboards;

    public ScoreboardServiceImpl() {
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