package net.exceptionmc.listener.spigot;

import net.exceptionmc.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.entity.PlayerDeathEvent playerDeathEvent) {

        Player player = playerDeathEvent.getEntity();

        Bukkit.getScheduler().scheduleSyncDelayedTask(BuildFFA.getBuildFFA(), new Runnable() {
            @Override
            public void run() {

                player.spigot().respawn();
            }
        }, 1);

        playerDeathEvent.setDeathMessage("");
    }
}
