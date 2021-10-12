package net.exceptionmc;

import net.exceptionmc.commandexecutor.BuildCommand;
import net.exceptionmc.commandexecutor.BuildFFACommand;
import net.exceptionmc.gamework.Gamework;
import net.exceptionmc.gamework.kits.KitDatabase;
import net.exceptionmc.gamework.voting.MapManager;
import net.exceptionmc.listener.gamework.KitInventoryPreviewCloseEvent;
import net.exceptionmc.listener.gamework.KitInventorySortEvent;
import net.exceptionmc.listener.spigot.*;
import net.exceptionmc.timer.MapChangeTimer;
import net.exceptionmc.utils.ActionBarUtil;
import net.exceptionmc.utils.MapChangeUtil;
import net.exceptionmc.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("all")
public class BuildFFA extends JavaPlugin {

    public static String prefix = "§8» §9§lBuildFFA §8× §7";
    private static BuildFFA buildFFA;

    public static ArrayList<UUID> builder = new ArrayList<>();

    @Override
    public void onEnable() {

        buildFFA = this;

        KitDatabase.setKitDatabase("buildffa", "buildffa", "YkhYZ2hRMnFnZGchOTYqWQ==");

        Gamework gamework = new Gamework();
        gamework.loadGameWork(true, false, false);

        if (new MapManager().getAllMaps().size() > 1) {

            MapChangeUtil.initNewMap();
            new MapChangeTimer().startMapChangeTimer();
        }

        new ActionBarUtil().startUpdating();

        StatsUtil.createTables();

        setGameRules();

        registerCommands();
        registerEvents();

        sendAscii();
    }

    @Override
    public void onDisable() {

        sendAscii();
    }

    public void registerCommands() {

        buildFFA.getCommand("build").setExecutor(new BuildCommand());
        buildFFA.getCommand("buildFFA").setExecutor(new BuildFFACommand());
    }

    public void registerEvents() {

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new AsyncPlayerChatEvent(), this);
        pluginManager.registerEvents(new BlockBreakEvent(), this);
        pluginManager.registerEvents(new BlockPlaceEvent(), this);
        pluginManager.registerEvents(new EntityDamageEvent(), this);
        pluginManager.registerEvents(new InventoryClickEvent(), this);
        pluginManager.registerEvents(new KitInventoryPreviewCloseEvent(), this);
        pluginManager.registerEvents(new KitInventorySortEvent(), this);
        pluginManager.registerEvents(new PlayerDeathEvent(), this);
        pluginManager.registerEvents(new PlayerDropItemEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new PlayerMoveEvent(), this);
        pluginManager.registerEvents(new PlayerPickupItemEvent(), this);
        pluginManager.registerEvents(new PlayerQuitEvent(), this);
        pluginManager.registerEvents(new PlayerRespawnEvent(), this);
        pluginManager.registerEvents(new PlayerTeleportEvent(), this);
        pluginManager.registerEvents(new WeatherChangeEvent(), this);
    }

    public static String getPrefix() {

        return prefix;
    }

    public static void setGameRules() {
        for (World world : Bukkit.getWorlds()) {

            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("mobGriefing", "false");
            world.setGameRuleValue("keepInventory", "true");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("doMobLoot", "false");
            world.setGameRuleValue("doTileDrop", "false");
            world.setGameRuleValue("commandBlockOutput", "false");
            world.setGameRuleValue("naturalRegeneration", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("logAdminCommands", "false");
            world.setGameRuleValue("showDeathMessages", "false");
            world.setGameRuleValue("randomTickSpeed", "0");
            world.setGameRuleValue("sendCommandFeedback", "false");
            world.setGameRuleValue("reducedDebugInfo", "false");

            world.setDifficulty(Difficulty.PEACEFUL);

            world.setFullTime(6000);
            world.setWeatherDuration(0);
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    public void sendAscii() {

        PluginDescriptionFile pluginDescriptionFile = getDescription();
        String version = pluginDescriptionFile.getVersion();
        String author = pluginDescriptionFile.getAuthors().get(0);

        System.out.println("    ____        _ __    _________________ ");
        System.out.println("   / __ )__  __(_) /___/ / ____/ ____/   |");
        System.out.println("  / __  / / / / / / __  / /_  / /_  / /| |");
        System.out.println(" / /_/ / /_/ / / / /_/ / __/ / __/ / ___ |");
        System.out.println("/_____/\\__,_/_/_/\\__,_/_/   /_/   /_/  |_|");
        System.out.println("v" + version + " by " + author);
        System.out.println(" ");
    }

    public static BuildFFA getBuildFFA() {

        return buildFFA;
    }
}
