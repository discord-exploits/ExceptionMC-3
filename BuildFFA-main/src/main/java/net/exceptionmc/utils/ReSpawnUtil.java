package net.exceptionmc.utils;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.kits.KitContent;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.gamework.voting.MapLocation;
import net.exceptionmc.listener.spigot.PlayerMoveEvent;
import net.exceptionmc.util.CoinsUtil;
import net.exceptionmc.util.ItemStackUtil;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@SuppressWarnings("all")
public class ReSpawnUtil {

    public void teleportPlayerToSpawnLocation(Player player) {

        Vector vector = new Vector();
        vector.setX(0);
        vector.setY(0);
        vector.setZ(0);
        player.setVelocity(vector);

        Location location = new MapLocation(MapChangeUtil.getCurrentMap()).getLocation("spawnLocation");
        player.teleport(location);

        PlayerMoveEvent.items.remove(player);

        setJoinItems(player);
        player.setHealth(20);
        player.getActivePotionEffects().clear();

        if (player.hasPotionEffect(PotionEffectType.ABSORPTION))
            player.removePotionEffect(PotionEffectType.ABSORPTION);

        if (player.hasPotionEffect(PotionEffectType.REGENERATION))
            player.removePotionEffect(PotionEffectType.REGENERATION);
    }

    public void setJoinItems(Player player) {

        ItemStack mapVoting = new ItemStackUtil().getItem(Material.PAPER, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "E47iUSeq"));

        ItemStack kitMenu = new ItemStackUtil().getItem(Material.CHEST, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "hKnXB8Jb"));

        ItemStack stats = new ItemStackUtil().getItem(Material.BOOK, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "ZtdQIA0q"));

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);

        player.getInventory().setItem(2, mapVoting);
        player.getInventory().setItem(4, kitMenu);
        player.getInventory().setItem(6, stats);
    }

    public void playerDeath(Player player) {

        Player killer = null;

        if (player.getKiller() != null)
            killer = player.getKiller();

        if (killer != null) {

            killer.setHealth(20);

            new KitContent(new KitManager().getSelectedKit(killer)).setKitContentInPlayerInventory(killer);

            new CoinsUtil().addCoins(killer.getUniqueId().toString(), 3);

            player.sendMessage(BuildFFA.getPrefix() +
                    new LanguageUtil().getString(player.getUniqueId().toString(), "OmqtIcR4")
                            .replace("$killer", killer.getName()));
            killer.sendMessage(BuildFFA.getPrefix() +
                    new LanguageUtil().getString(killer.getUniqueId().toString(), "1iSi0qwj")
                            .replace("$player", player.getName()));

            KillStreakUtil.addKill(killer);

            StatsUtil.addKill(killer);
        } else {

            player.sendMessage(BuildFFA.getPrefix() +
                    new LanguageUtil().getString(player.getUniqueId().toString(), "gttXsmuy"));
        }

        KillStreakUtil.stopStreak(player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(BuildFFA.getBuildFFA(), new Runnable() {
            @Override
            public void run() {

                StatsUtil.addDeath(player);
                new ReSpawnUtil().teleportPlayerToSpawnLocation(player);
            }
        }, 1);
    }
}
