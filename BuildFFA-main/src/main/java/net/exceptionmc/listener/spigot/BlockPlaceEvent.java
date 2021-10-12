package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.util.ItemStackUtil;
import net.exceptionmc.utils.MapChangeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockPlaceEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.block.BlockPlaceEvent blockPlaceEvent) {

        Player player = blockPlaceEvent.getPlayer();

        if (player.getItemInHand() != null) {
            if (player.getItemInHand().getType() == Material.SANDSTONE) {

                player.getInventory().setItem(player.getInventory().getHeldItemSlot(),
                        new ItemStackUtil().getItem(Material.SANDSTONE, 0, 16, ""));
            }
        }

        if (!BuildFFA.builder.contains(player.getUniqueId())) {
            if (player.getLocation().getY() <= MapChangeUtil.damageHigh.getY()) {

                delayBlockBreak(blockPlaceEvent.getBlock());
            } else {

                blockPlaceEvent.setCancelled(true);
            }
        }
    }

    public static void delayBlockBreak(Block block) {

        Bukkit.getScheduler().scheduleSyncDelayedTask(BuildFFA.getBuildFFA(), ()
                -> block.setType(Material.RED_SANDSTONE), 20 * 3);

        Bukkit.getScheduler().scheduleSyncDelayedTask(BuildFFA.getBuildFFA(), ()
                -> block.setType(Material.AIR), 20 * 6);
    }
}
