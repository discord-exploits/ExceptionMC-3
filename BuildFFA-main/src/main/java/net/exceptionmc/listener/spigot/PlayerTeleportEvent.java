package net.exceptionmc.listener.spigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTeleportEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerTeleportEvent playerTeleportEvent) {

        Player player = playerTeleportEvent.getPlayer();

        for (Player players : Bukkit.getOnlinePlayers()) {

            player.hidePlayer(players);
            player.showPlayer(players);

            players.hidePlayer(player);
            players.showPlayer(player);
        }
    }
}
