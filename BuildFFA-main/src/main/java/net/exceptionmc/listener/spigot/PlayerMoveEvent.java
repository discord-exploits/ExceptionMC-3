package net.exceptionmc.listener.spigot;

import net.exceptionmc.gamework.kits.KitContent;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.utils.MapChangeUtil;
import net.exceptionmc.utils.ReSpawnUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class PlayerMoveEvent implements Listener {

    public static final ArrayList<Player> items = new ArrayList<>();

    @EventHandler
    public void on(org.bukkit.event.player.PlayerMoveEvent playerMoveEvent) {

        Player player = playerMoveEvent.getPlayer();

        if (!items.contains(player)) {
            if (player.getLocation().getY() <= MapChangeUtil.damageHigh.getY()) {

                items.add(player);

                player.getInventory().clear();
                new KitContent(new KitManager().getSelectedKit(player))
                        .setKitContentInPlayerInventory(player);
            }
        }

        if (player.getLocation().getY() <= MapChangeUtil.deathHigh.getY()) {

            player.setHealth(0);
        }
    }
}
