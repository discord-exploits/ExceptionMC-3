package net.exceptionmc.listener.spigot;

import net.exceptionmc.utils.MapChangeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.entity.EntityDamageEvent entityDamageEvent) {

        if (entityDamageEvent.getEntity() instanceof Player) {

            Player player = (Player) entityDamageEvent.getEntity();

            if (entityDamageEvent.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL)
                entityDamageEvent.setCancelled(true);

            if (player.getLocation().getY() >= MapChangeUtil.damageHigh.getY())
                entityDamageEvent.setCancelled(true);
        }
    }
}
