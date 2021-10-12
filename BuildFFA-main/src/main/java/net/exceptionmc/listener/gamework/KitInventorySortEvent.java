package net.exceptionmc.listener.gamework;

import net.exceptionmc.utils.ReSpawnUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitInventorySortEvent implements Listener {

    @EventHandler
    public void on(net.exceptionmc.gamework.events.kits.KitInventorySortEvent kitInventorySortEvent) {

        Player player = kitInventorySortEvent.getPlayer();

        new ReSpawnUtil().setJoinItems(player);
    }
}
