package net.exceptionmc.utils;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.handler.ActionbarHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class ActionBarUtil {

    public void startUpdating() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(BuildFFA.getBuildFFA(), () -> {

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Bukkit.getOnlinePlayers().size() > 4) {

                    new ActionbarHandler().sendActionbarPacket(player, "§a§lTeams allowed §8§l(§7§lmax. 2§8§l)");
                } else {

                    new ActionbarHandler().sendActionbarPacket(player, "§c§lTeams not allowed");
                }
            }
        }, 20, 20);
    }
}
