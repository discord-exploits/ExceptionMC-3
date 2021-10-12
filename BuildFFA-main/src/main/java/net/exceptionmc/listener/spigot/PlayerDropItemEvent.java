package net.exceptionmc.listener.spigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDropItemEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerDropItemEvent playerDropItemEvent) {

        playerDropItemEvent.setCancelled(true);
    }
}
