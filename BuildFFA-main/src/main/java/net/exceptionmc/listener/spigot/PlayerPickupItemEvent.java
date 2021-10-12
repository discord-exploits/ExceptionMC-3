package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPickupItemEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerPickupItemEvent playerPickupItemEvent) {

        playerPickupItemEvent.setCancelled(!BuildFFA.builder.contains(playerPickupItemEvent.getPlayer().getUniqueId()));
    }
}
