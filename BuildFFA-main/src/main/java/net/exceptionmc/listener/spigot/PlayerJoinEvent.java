package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.util.CoinsUtil;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.util.ScoreboardUtil;
import net.exceptionmc.utils.ReSpawnUtil;
import net.exceptionmc.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerJoinEvent playerJoinEvent) {

        playerJoinEvent.setJoinMessage("");

        Player player = playerJoinEvent.getPlayer();

        StatsUtil.createPlayer(player);
        sendScoreboard(player);

        new KitManager().createPlayer(player, "Default");

        new ReSpawnUtil().teleportPlayerToSpawnLocation(player);

        for (Player players : Bukkit.getOnlinePlayers())
            players.sendMessage(BuildFFA.getPrefix() +
                    new LanguageUtil().getString(players.getUniqueId().toString(), "XF9Hg4fj")
                            .replace("$player", player.getName()));

        player.setGameMode(GameMode.SURVIVAL);
        StatsUtil.initCache(player);
    }

    public static void sendScoreboard(Player player) {

        String color = "§" + new LanguageUtil().getColor(player.getUniqueId().toString());

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("§0");
        arrayList.add("§8× §7Coins");
        arrayList.add("§8➥ " + color + new CoinsUtil().getCoins(player.getUniqueId().toString()));
        arrayList.add("§0 ");
        arrayList.add("§8× §7Points");
        arrayList.add("§8➥ " + color + StatsUtil.getPointsLifetime(player));
        arrayList.add("§0  ");
        arrayList.add("§8× §7Kills");
        arrayList.add("§8➥ " + color + StatsUtil.getKillsLifetime(player));
        arrayList.add("§0   ");
        arrayList.add("§8× §7Deaths");
        arrayList.add("§8➥ " + color + StatsUtil.getDeathsLifetime(player));
        arrayList.add("§0    ");
        arrayList.add("§8× §7Ranking");
        arrayList.add("§8➥ §7#" + color + StatsUtil.getRankingLifetime(player));

        new ScoreboardUtil().sendScoreboard(player, "§8× §" +
                new LanguageUtil().getColor(player.getUniqueId().toString()) + "§lBuildFFA §8×", arrayList);
    }
}
