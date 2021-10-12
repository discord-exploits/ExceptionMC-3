package net.exceptionmc.listener.spigot;

import net.exceptionmc.utils.ReSpawnUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerRespawnEvent playerRespawnEvent) {

        Player player = playerRespawnEvent.getPlayer();

        new ReSpawnUtil().playerDeath(player);
    }
}
