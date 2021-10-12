package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.gamework.voting.MapVoting;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.utils.StatsUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerInteractEvent playerInteractEvent) {

        Player player = playerInteractEvent.getPlayer();

        if (playerInteractEvent.getItem() != null) {
            if (playerInteractEvent.getItem().getType() == Material.MUSHROOM_SOUP) {
                if (player.getHealth() < 20)
                    if (player.getHealth() < 16)
                        player.setHealth(player.getHealth() + 4);
                    else
                        player.setHealth(20);

                player.getItemInHand().setType(Material.BOWL);
            }

            if (playerInteractEvent.getItem().getItemMeta() != null)
                if (playerInteractEvent.getItem().getItemMeta().getDisplayName() != null)
                    if (playerInteractEvent.getItem().getItemMeta().getDisplayName().equals(
                            new LanguageUtil().getString(player.getUniqueId().toString(), "E47iUSeq")))

                        MapVoting.openVotingInventory(player);
                    else if (playerInteractEvent.getItem().getItemMeta().getDisplayName().equals(
                            new LanguageUtil().getString(player.getUniqueId().toString(), "hKnXB8Jb")))

                        new KitManager().openKitSelectionInventory(player);
                    else if (playerInteractEvent.getItem().getItemMeta().getDisplayName().equals(
                            new LanguageUtil().getString(player.getUniqueId().toString(), "ZtdQIA0q"))) {

                        StatsUtil.openStatsInventory(player);
                    }
        }

        if (!BuildFFA.builder.contains(player.getUniqueId()))
            if (playerInteractEvent.getClickedBlock() != null)
                if (playerInteractEvent.getClickedBlock().getType() == Material.CHEST ||
                        playerInteractEvent.getClickedBlock().getType() == Material.ENDER_CHEST)
                    playerInteractEvent.setCancelled(true);
    }
}
