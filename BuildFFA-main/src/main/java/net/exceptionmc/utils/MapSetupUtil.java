package net.exceptionmc.utils;

import com.google.common.collect.Maps;
import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.voting.MapLocation;
import net.exceptionmc.gamework.voting.MapManager;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("all")
public class MapSetupUtil {

    private static final ArrayList<Player> setupPlayersArrayList = new ArrayList<>();
    private static final HashMap<Player, String> mapNameHashMap = Maps.newHashMap();
    private static final HashMap<Player, String> mapBuilderHashMap = Maps.newHashMap();
    private static final HashMap<Player, Location> mapSpawnLocationHashMap = Maps.newHashMap();
    private static final HashMap<Player, Location> mapDamageHighHashMap = Maps.newHashMap();
    private static final HashMap<Player, Location> mapDeathHighHashMap = Maps.newHashMap();

    public static void startSetup(Player player) {

        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "zfLpkBfa"));

        setupPlayersArrayList.add(player);
    }

    public static void listenForSetup(AsyncPlayerChatEvent asyncPlayerChatEvent) {

        Player player = asyncPlayerChatEvent.getPlayer();
        String sendMessage = asyncPlayerChatEvent.getMessage();

        if (setupPlayersArrayList.contains(player)) {

            asyncPlayerChatEvent.setCancelled(true);

            if (!mapNameHashMap.containsKey(player)) {

                setMapName(player, sendMessage);
            } else if (!mapBuilderHashMap.containsKey(player)) {

                setMapBuilder(player, sendMessage);
            } else if (!mapSpawnLocationHashMap.containsKey(player)) {

                if (sendMessage.equals("set"))
                    setMapSpawnLocation(player);
            } else if (!mapDamageHighHashMap.containsKey(player)) {

                if (sendMessage.equals("set"))
                    setMapDamageHigh(player);
            } else if (!mapDeathHighHashMap.containsKey(player)) {

                if (sendMessage.equals("set"))
                    setMapDeathHigh(player);
            }
        }
    }

    public static void setMapName(Player player, String mapName) {

        mapNameHashMap.put(player, mapName);
        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "0FvljNU6"));
    }

    public static void setMapBuilder(Player player, String mapBuilder) {

        mapBuilderHashMap.put(player, mapBuilder);
        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "vB2mkGnp"));
    }

    public static void setMapSpawnLocation(Player player) {

        mapSpawnLocationHashMap.put(player, player.getLocation());
        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "TgsAZ6Ul"));
    }

    public static void setMapDamageHigh(Player player) {

        mapDamageHighHashMap.put(player, player.getLocation());
        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "Bq8zySA5"));
    }

    public static void setMapDeathHigh(Player player) {

        mapDeathHighHashMap.put(player, player.getLocation());
        saveMap(player);
    }

    public static void saveMap(Player player) {

        new MapManager().createMap(mapNameHashMap.get(player), mapBuilderHashMap.get(player));

        new MapLocation(mapNameHashMap.get(player)).setLocation("spawnLocation",
                mapSpawnLocationHashMap.get(player));

        new MapLocation(mapNameHashMap.get(player)).setHigh("damageHigh",
                mapDamageHighHashMap.get(player));

        new MapLocation(mapNameHashMap.get(player)).setHigh("deathHigh",
                mapDeathHighHashMap.get(player));

        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "UDVYhoi4")
                        .replace("$map", mapNameHashMap.get(player)));

        setupPlayersArrayList.remove(player);
        mapNameHashMap.remove(player);
        mapBuilderHashMap.remove(player);
        mapSpawnLocationHashMap.remove(player);
        mapDamageHighHashMap.remove(player);
        mapDeathHighHashMap.remove(player);
    }
}
