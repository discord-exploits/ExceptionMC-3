package net.exceptionmc.listener.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.inventory.InventoryClickEvent inventoryClickEvent) {

        Player player = (Player) inventoryClickEvent.getWhoClicked();

        if (!net.exceptionmc.gamework.listener.InventoryClickEvent.inventorySortPlayers.contains(player)) {

            inventoryClickEvent.setCancelled(true);
        }
    }
}
