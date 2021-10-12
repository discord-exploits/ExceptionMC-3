package net.exceptionmc.listener.spigot;

import net.exceptionmc.utils.KitSetupUtil;
import net.exceptionmc.utils.MapSetupUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncPlayerChatEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.AsyncPlayerChatEvent asyncPlayerChatEvent) {

        KitSetupUtil.listenForSetup(asyncPlayerChatEvent);
        MapSetupUtil.listenForSetup(asyncPlayerChatEvent);
    }
}
