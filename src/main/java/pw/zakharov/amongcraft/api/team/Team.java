package pw.zakharov.amongcraft.api.team;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 04.10.2020 18:25
 */
public interface Team {

    /**
     * @return количество игроков в команде
     */
    int getSize();

    /**
     * @return максимальное количество игроков в команде
     */
    int getMaxSize();

    /**
     * Запихиваем игрока в команду, если в ней есть место или у игрока хватает прав на форсированное вступление
     *
     * @param player игрок, которого хотим запихнуть в команду
     * @return можем ли добавить игрока в команду
     */
    default boolean join(@NotNull Player player) {
        if (getMaxSize() > getSize() || player.hasPermission("among.admin")) {
            getPlayers().add(player);
            return true;
        }
        return false;
    }

    /**
     * @param player игрок которого нужно выгнать из команды
     */
    default void leave(@NotNull Player player) {
        getPlayers().remove(player);
    }

    /**
     * @return все точки спавна команду
     */
    @NotNull Set<Location> getSpawns();

    /**
     * Итератор цикличный
     *
     * @return следующий спавн этой команды.
     */
    @NotNull Location getNextSpawn();

    /**
     * @return список всех игроков в команде
     */
    @NotNull Set<Player> getPlayers();

    /**
     * @return контекст арены
     */
    @NotNull TeamContext getContext();

    /**
     * Контекст арены
     */
    interface TeamContext {

        /**
         * @return название команды
         */
        @NotNull String getName();

        /**
         * @return цвет команды
         */
        @NotNull Color getColor();

        /**
         * @return роль команды
         */
        @NotNull Role getRole();
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
