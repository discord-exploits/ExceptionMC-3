package net.exceptionmc.utils;

import com.google.common.collect.Maps;
import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class KitSetupUtil {

    private static final ArrayList<Player> setupPlayersArrayList = new ArrayList<>();
    private static final HashMap<Player, String> kitNameHashMap = Maps.newHashMap();
    private static final HashMap<Player, Long> kitPriceHashMap = Maps.newHashMap();

    public static void startSetup(Player player) {

        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "bS0Pfu96"));

        setupPlayersArrayList.add(player);
    }

    public static void listenForSetup(AsyncPlayerChatEvent asyncPlayerChatEvent) {

        Player player = asyncPlayerChatEvent.getPlayer();
        String sendMessage = asyncPlayerChatEvent.getMessage();

        if (setupPlayersArrayList.contains(player)) {

            asyncPlayerChatEvent.setCancelled(true);

            if (!kitNameHashMap.containsKey(player))
                setKitName(player, sendMessage);
            else if (!kitPriceHashMap.containsKey(player))
                setKitPrice(player, Long.valueOf(sendMessage));
        }
    }

    public static void setKitName(Player player, String kitName) {

        kitNameHashMap.put(player, kitName);

        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "i0DqkNYG"));
    }

    public static void setKitPrice(Player player, Long kitPrice) {

        kitPriceHashMap.put(player, kitPrice);
        saveKit(player);
    }

    public static void saveKit(Player player) {

        new KitManager().createKit(player, kitNameHashMap.get(player), kitPriceHashMap.get(player));

        player.sendMessage(BuildFFA.getPrefix() +
                new LanguageUtil().getString(player.getUniqueId().toString(), "V2vEJsqP")
                        .replace("$kit", kitNameHashMap.get(player)));

        setupPlayersArrayList.remove(player);
        kitNameHashMap.remove(player);
        kitPriceHashMap.remove(player);
    }
}
