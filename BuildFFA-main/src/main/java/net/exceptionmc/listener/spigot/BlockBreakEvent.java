package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.block.BlockBreakEvent blockBreakEvent) {

        blockBreakEvent.setCancelled(!BuildFFA.builder.contains(blockBreakEvent.getPlayer().getUniqueId()));
    }
}
