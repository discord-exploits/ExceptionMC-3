package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.utils.KillStreakUtil;
import net.exceptionmc.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerQuitEvent playerQuitEvent) {

        playerQuitEvent.setQuitMessage("");

        Player player = playerQuitEvent.getPlayer();

        for (Player players : Bukkit.getOnlinePlayers()) {

            players.sendMessage(BuildFFA.getPrefix() +
                    new LanguageUtil().getString(players.getUniqueId().toString(), "e9aUJvyE")
                            .replace("$player", player.getName()));
        }

        KillStreakUtil.streakHashMap.remove(player);
        StatsUtil.saveToDatabase(player);
    }
}
