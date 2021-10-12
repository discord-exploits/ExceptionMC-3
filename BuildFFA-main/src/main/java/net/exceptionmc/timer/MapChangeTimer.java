package net.exceptionmc.timer;

import com.connorlinfoot.titleapi.TitleAPI;
import net.exceptionmc.BuildFFA;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.utils.MapChangeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class MapChangeTimer {

    private int seconds = 60;
    private int taskId;

    public void startMapChangeTimer() {

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(BuildFFA.getBuildFFA(),
                this::startMapChangeCountDown, 20 * 60 * 14, 20 * 60 * 15);
    }

    public void startMapChangeCountDown() {

        seconds = 60;

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(BuildFFA.getBuildFFA(), () -> {
            switch (seconds) {

                case 60:
                case 30:
                case 15:
                case 10:
                case 5:
                case 3:
                case 2:
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        player.sendMessage(BuildFFA.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "iy4iANhU")
                                        .replace("$seconds", String.valueOf(seconds)));

                        String colorCode = "§" + new LanguageUtil().getColor(player.getUniqueId().toString());
                        if (seconds < 11)
                            TitleAPI.sendTitle(player, 5,20, 5, "§7§lMapchange", colorCode + "§l" + seconds + " §7§lSeconds");
                    }
                    break;
                case 1:
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        player.sendMessage(BuildFFA.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "6K8VFcKs")
                                        .replace("$second", String.valueOf(seconds)));

                        String colorCode = "§" + new LanguageUtil().getColor(player.getUniqueId().toString());
                        TitleAPI.sendTitle(player, 5,20, 5, "§7§lMapchange", colorCode + "§l" + seconds + " §7§lSecond");
                    }
                    break;
                case 0:
                    Bukkit.getScheduler().cancelTask(taskId);
                    MapChangeUtil.changeMap();
                    break;
            }

            seconds--;
        }, 20, 20);
    }
}
