package net.exceptionmc.listener.spigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WeatherChangeEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.weather.WeatherChangeEvent weatherChangeEvent) {
        
        weatherChangeEvent.setCancelled(true);
    }
}
