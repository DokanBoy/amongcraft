package pw.zakharov.amongcraft.api;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:25
 */
public interface Team {

    /**
     * @return количество игроков в команде
     */
    default int getSize() {
        return getPlayers().size();
    }

    /**
     * @return максимальное количество игроков в команде
     */
    int getMaxSize();

    /**
     * @param player игрок, которого хотим запихнуть в команду
     * @return можем ли запихнуть
     */
    default boolean canJoin(@NonNull Player player) {
        return getMaxSize() > getSize() || player.hasPermission("among.admin");
    }

    /**
     * Запихиваем игрока в команду
     *
     * @param player игрок, которого хотим запихнуть в команду
     */
    default void join(@NonNull Player player) {
        getPlayers().add(player);
    }

    /**
     * @param player игрок которого нужно выгнать из команды
     */
    default void leave(@NonNull Player player) {
        getPlayers().remove(player);
    }

    /**
     * @return все точки спавна команду
     */
    @NonNull Set<Location> getSpawns();

    /**
     * Итератор цикличный
     *
     * @return следующий спавн этой команды.
     */
    @NonNull Location getNextSpawn();

    /**
     * @return список всех игроков в команде
     */
    @NonNull Set<Player> getPlayers();

    /**
     * @return контекст арены
     */
    @NonNull TeamContext getContext();

    /**
     * Контекст арены
     */
    interface TeamContext {

        /**
         * @return название команды
         */
        @NonNull String getName();

        /**
         * @return цвет команды
         */
        @NonNull Color getColor();

        /**
         * @return роль команды
         */
        @NonNull Role getRole();
    }

    /**
     * Роли команд
     */
    enum Role {
        IMPOSTER,
        INNOCENT,
        SPECTATOR,
        ;
    }

}
