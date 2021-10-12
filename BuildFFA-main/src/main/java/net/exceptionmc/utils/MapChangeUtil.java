package net.exceptionmc.utils;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.voting.MapLocation;
import net.exceptionmc.gamework.voting.MapManager;
import net.exceptionmc.gamework.voting.MapVoting;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class MapChangeUtil {

    private static String currentMap;
    public static Location damageHigh;
    public static Location deathHigh;

    public static void initNewMap() {

        setCurrentMap(MapVoting.getWinnerMap());

        damageHigh =
                new MapLocation(MapChangeUtil.getCurrentMap()).getHigh("damageHigh");
        deathHigh =
                new MapLocation(MapChangeUtil.getCurrentMap()).getHigh("deathHigh");
    }

    public static void changeMap() {

        initNewMap();

        for (Player player : Bukkit.getOnlinePlayers()) {

            new ReSpawnUtil().teleportPlayerToSpawnLocation(player);

            player.sendMessage(BuildFFA.getPrefix() +
                    new LanguageUtil().getString(player.getUniqueId().toString(), "dj7ksbFw")
                            .replace("$map", currentMap)
                            .replace("$builder", new MapManager().getBuilder(currentMap))
                            .replace("$votes", String.valueOf(MapVoting.getVotes(currentMap))));
        }

        MapVoting.clearVoting();
    }

    public static void setCurrentMap(String currentMap) {

        MapChangeUtil.currentMap = currentMap;
    }

    public static String getCurrentMap() {

        return currentMap;
    }

    public static Location getDamageHigh() {

        return damageHigh;
    }

    public static Location getDeathHigh() {

        return deathHigh;
    }
}
