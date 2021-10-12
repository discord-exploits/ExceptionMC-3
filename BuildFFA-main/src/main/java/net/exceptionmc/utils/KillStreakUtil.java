package net.exceptionmc.utils;

import com.google.common.collect.Maps;
import net.exceptionmc.BuildFFA;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KillStreakUtil {

    public static final HashMap<Player, Integer> streakHashMap = Maps.newHashMap();

    public static void addKill(Player player) {

        if (!streakHashMap.containsKey(player))
            streakHashMap.put(player, 1);
        else
            streakHashMap.replace(player, streakHashMap.get(player) + 1);

        checkForStreak(player);
    }

    public static void stopStreak(Player player) {

        if (streakHashMap.containsKey(player)) {
            if (streakHashMap.get(player) > 4)
                for (Player players : Bukkit.getOnlinePlayers())

                    players.sendMessage(BuildFFA.getPrefix() +
                            new LanguageUtil().getString(player.getUniqueId().toString(), "nMNzbQP6")
                                    .replace("$player", player.getName())
                                    .replace("$streak", String.valueOf(streakHashMap.get(player))));

            streakHashMap.remove(player);
        }
    }

    public static void checkForStreak(Player player) {
        switch (streakHashMap.get(player)) {

            case 5:
            case 10:
            case 15:
            case 20:
            case 25:
            case 30:
            case 35:
            case 40:
            case 45:
            case 50:
            case 100:
                for (Player players : Bukkit.getOnlinePlayers())
                    players.sendMessage(BuildFFA.getPrefix() +
                            new LanguageUtil().getString(players.getUniqueId().toString(), "RZnrBfaZ")
                                    .replace("$player", player.getName())
                                    .replace("$streak", String.valueOf(streakHashMap.get(player))));
        }
    }
}
