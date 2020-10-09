package pw.zakharov.amongcraft.api.member;

import org.jetbrains.annotations.NotNull;
import pw.zakharov.amongcraft.api.stat.Statistic;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:37
 */
@Deprecated
public interface Member {

    @NotNull UUID getId();

    @NotNull List<Statistic> getStats();

    @NotNull
    default Statistic findStatByType(Statistic.Type type) {
        return getStats().stream()
                .filter(stat -> stat.getType() == type)
                .findFirst().orElseThrow(NullPointerException::new);
    }

}