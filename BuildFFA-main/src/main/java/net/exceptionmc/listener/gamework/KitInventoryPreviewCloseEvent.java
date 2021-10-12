package net.exceptionmc.listener.gamework;

import net.exceptionmc.utils.ReSpawnUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitInventoryPreviewCloseEvent implements Listener {

    @EventHandler
    public void on(net.exceptionmc.gamework.events.kits.KitInventoryPreviewCloseEvent kitInventoryPreviewCloseEvent) {

        Player player = kitInventoryPreviewCloseEvent.getPlayer();

        new ReSpawnUtil().setJoinItems(player);
    }
}
