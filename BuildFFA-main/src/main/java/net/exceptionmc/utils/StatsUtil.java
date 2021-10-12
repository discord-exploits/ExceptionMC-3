package net.exceptionmc.utils;

import com.google.common.collect.Maps;
import net.exceptionmc.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@SuppressWarnings("all")
public class StatsUtil {

    private static final DatabaseUtil databaseUtil = new DatabaseUtil("buildffa", "buildffa",
            "YkhYZ2hRMnFnZGchOTYqWQ==");

    private static final HashMap<Player, Long> monthlyPoints = Maps.newHashMap();
    private static final HashMap<Player, Long> monthlyKills = Maps.newHashMap();
    private static final HashMap<Player, Long> monthlyDeaths = Maps.newHashMap();

    private static final HashMap<Player, Long> lifetimePoints = Maps.newHashMap();
    private static final HashMap<Player, Long> lifetimeKills = Maps.newHashMap();
    private static final HashMap<Player, Long> lifetimeDeaths = Maps.newHashMap();

    public static void createTables() {

        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS monthlyStats" +
                "(uniqueId VARCHAR(255), points BIGINT, kills BIGINT, deaths BIGINT)");
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS lifetimeStats" +
                "(uniqueId VARCHAR(255), points BIGINT, kills BIGINT, deaths BIGINT)");
    }

    public static void createPlayer(Player player) {

        if (!databaseUtil.exists("monthlyStats", "uniqueId", player.getUniqueId().toString())) {

            databaseUtil.create("monthlyStats", "uniqueId", player.getUniqueId().toString());

            databaseUtil.setLong("monthlyStats", "points", 0L,
                    "uniqueId", player.getUniqueId().toString());

            databaseUtil.setLong("monthlyStats", "kills", 0L,
                    "uniqueId", player.getUniqueId().toString());

            databaseUtil.setLong("monthlyStats", "deaths", 0L,
                    "uniqueId", player.getUniqueId().toString());
        }

        if (!databaseUtil.exists("lifetimeStats", "uniqueId", player.getUniqueId().toString())) {

            databaseUtil.create("lifetimeStats", "uniqueId", player.getUniqueId().toString());

            databaseUtil.setLong("lifetimeStats", "points", 0L,
                    "uniqueId", player.getUniqueId().toString());

            databaseUtil.setLong("lifetimeStats", "kills", 0L,
                    "uniqueId", player.getUniqueId().toString());

            databaseUtil.setLong("lifetimeStats", "deaths", 0L,
                    "uniqueId", player.getUniqueId().toString());
        }
    }

    public static void initCache(Player player) {

        monthlyPoints.put(player, getPointsMonthly(player));
        monthlyKills.put(player, getKillsMonthly(player));
        monthlyDeaths.put(player, getDeathsMonthly(player));

        lifetimePoints.put(player, getPointsLifetime(player));
        lifetimeKills.put(player, getKillsLifetime(player));
        lifetimeDeaths.put(player, getDeathsLifetime(player));
    }

    public static void addPoints(Player player, Integer amount) {

        if (monthlyPoints.containsKey(player))
            monthlyPoints.replace(player, getPointsMonthly(player) + amount);
        else
            databaseUtil.setLong("monthlyStats", "points",
                    getPointsMonthly(player) + amount,
                    "uniqueId", player.getUniqueId().toString());

        if (lifetimePoints.containsKey(player))
            lifetimePoints.replace(player, getPointsLifetime(player) + amount);
        else
            databaseUtil.setLong("lifetimeStats", "points",
                    getPointsLifetime(player) + amount,
                    "uniqueId", player.getUniqueId().toString());

        updateScoreboard(player);
    }

    public static void removePoints(Player player, Integer amount) {
        if (getPointsMonthly(player) - amount > 0)
            if (monthlyPoints.containsKey(player))
                monthlyPoints.replace(player, getPointsMonthly(player) - amount);
            else
                databaseUtil.setLong("monthlyStats", "points",
                        getPointsMonthly(player) - amount, "uniqueId", player.getUniqueId().toString());
        else if (monthlyPoints.containsKey(player))
            monthlyPoints.replace(player, 0L);
        else
            databaseUtil.setLong("monthlyStats", "points",
                    0L,
                    "uniqueId", player.getUniqueId().toString());

        if (getPointsLifetime(player) - amount > 0)
            if (lifetimePoints.containsKey(player))
                lifetimePoints.replace(player, getPointsLifetime(player) - amount);
            else
                databaseUtil.setLong("lifetimeStats", "points",
                        getPointsLifetime(player) - amount,
                        "uniqueId", player.getUniqueId().toString());
        else if (lifetimePoints.containsKey(player))
            lifetimePoints.replace(player, 0L);
        else
            databaseUtil.setLong("lifetimeStats", "points",
                    0L,
                    "uniqueId", player.getUniqueId().toString());

        updateScoreboard(player);
    }

    public static void addKill(Player player) {
        if (monthlyKills.containsKey(player))
            monthlyKills.replace(player, getKillsMonthly(player) + 1);
        else
            databaseUtil.setLong("monthlyStats", "kills",
                    getKillsMonthly(player) + 1,
                    "uniqueId", player.getUniqueId().toString());

        if (lifetimeKills.containsKey(player))
            lifetimeKills.replace(player, getKillsLifetime(player) + 1);
        else
            databaseUtil.setLong("lifetimeStats", "kills",
                    getKillsLifetime(player) + 1,
                    "uniqueId", player.getUniqueId().toString());

        addPoints(player, 5);
    }

    public static void addDeath(Player player) {
        if (monthlyDeaths.containsKey(player))
            monthlyDeaths.replace(player, getDeathsMonthly(player) + 1);
        else
            databaseUtil.setLong("monthlyStats", "deaths",
                    getDeathsMonthly(player) + 1,
                    "uniqueId", player.getUniqueId().toString());

        if (lifetimeDeaths.containsKey(player))
            lifetimeDeaths.replace(player, getDeathsLifetime(player) + 1);
        else
            databaseUtil.setLong("lifetimeStats", "deaths",
                    getDeathsLifetime(player) + 1,
                    "uniqueId", player.getUniqueId().toString());

        removePoints(player, 2);
    }

    public static Long getPointsMonthly(Player player) {
        if (monthlyPoints.containsKey(player))
            return monthlyPoints.get(player);
        else
            return databaseUtil.getLong("monthlyStats", "points",
                    "uniqueId", player.getUniqueId().toString());
    }

    public static Long getPointsLifetime(Player player) {
        if (lifetimePoints.containsKey(player))
            return lifetimePoints.get(player);
        else
            return databaseUtil.getLong("lifetimeStats", "points",
                    "uniqueId", player.getUniqueId().toString());
    }

    public static Long getKillsMonthly(Player player) {
        if (monthlyKills.containsKey(player))
            return monthlyKills.get(player);
        else
            return databaseUtil.getLong("monthlyStats", "kills",
                    "uniqueId", player.getUniqueId().toString());
    }

    public static Long getKillsLifetime(Player player) {
        if (lifetimeKills.containsKey(player))
            return lifetimeKills.get(player);
        else
            return databaseUtil.getLong("lifetimeStats", "kills",
                    "uniqueId", player.getUniqueId().toString());
    }

    public static Long getDeathsMonthly(Player player) {
        if (monthlyDeaths.containsKey(player))
            return monthlyDeaths.get(player);
        else
            return databaseUtil.getLong("monthlyStats", "deaths",
                    "uniqueId", player.getUniqueId().toString());
    }

    public static Long getDeathsLifetime(Player player) {
        if (lifetimeDeaths.containsKey(player))
            return lifetimeDeaths.get(player);
        else
            return databaseUtil.getLong("lifetimeStats", "deaths",
                    "uniqueId", player.getUniqueId().toString());
    }

    public static Integer getRankingMonthly(Player player) {
        return databaseUtil.getRanking("monthlyStats", "points",
                "uniqueId", player.getUniqueId().toString());
    }

    public static Integer getRankingLifetime(Player player) {
        return databaseUtil.getRanking("lifetimeStats", "points",
                "uniqueId", player.getUniqueId().toString());
    }

    public static void saveToDatabase(Player player) {

        databaseUtil.setLong("monthlyStats", "points", getPointsMonthly(player),
                "uniqueId", player.getUniqueId().toString());

        databaseUtil.setLong("monthlyStats", "kills", getKillsMonthly(player),
                "uniqueId", player.getUniqueId().toString());

        databaseUtil.setLong("monthlyStats", "deaths", getDeathsMonthly(player),
                "uniqueId", player.getUniqueId().toString());

        databaseUtil.setLong("lifetimeStats", "points", getPointsLifetime(player),
                "uniqueId", player.getUniqueId().toString());

        databaseUtil.setLong("lifetimeStats", "kills", getKillsLifetime(player),
                "uniqueId", player.getUniqueId().toString());

        databaseUtil.setLong("lifetimeStats", "deaths", getDeathsLifetime(player),
                "uniqueId", player.getUniqueId().toString());

        monthlyPoints.remove(player);
        monthlyKills.remove(player);
        monthlyDeaths.remove(player);

        lifetimePoints.remove(player);
        lifetimeKills.remove(player);
        lifetimeDeaths.remove(player);
    }

    public static void updateScoreboard(Player player) {

        String color = "§" + new LanguageUtil().getColor(player.getUniqueId().toString());
        HashMap<Integer, String> scoreboardContent = new HashMap<>();

        scoreboardContent.put(12, "§8➥ " + color + new CoinsUtil().getCoins(player.getUniqueId().toString()));
        scoreboardContent.put(9, "§8➥ " + color + getPointsLifetime(player));
        scoreboardContent.put(6, "§8➥ " + color + getKillsLifetime(player));
        scoreboardContent.put(3, "§8➥ " + color + getDeathsLifetime(player));
        scoreboardContent.put(0, "§8➥ §7#" + color + getRankingLifetime(player));

        new ScoreboardUtil().update(player, scoreboardContent);
    }

    public static Inventory openStatsInventory(Player player) {

        HashMap<Integer, ItemStack> items = Maps.newHashMap();
        items.put(11, new ItemStackUtil().getItem(Material.PAPER, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "qt7MFCN5")));
        items.put(15, new ItemStackUtil().getItem(Material.PAPER, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "GlJw47cL")));
        items.put(19, new ItemStackUtil().getItem(Material.IRON_SWORD, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "AqVv9mS8"),
                new String[]{"§8» §" + new LanguageUtil().getColor(player.getUniqueId().toString()) +
                        getKillsMonthly(player)}));
        items.put(21, new ItemStackUtil().getItem(Material.DEAD_BUSH, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "tSygpy0Z"),
                new String[]{"§8» §" + new LanguageUtil().getColor(player.getUniqueId().toString()) +
                        getDeathsMonthly(player)}));
        items.put(23, new ItemStackUtil().getItem(Material.DEAD_BUSH, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "tSygpy0Z"),
                new String[]{"§8» §" + new LanguageUtil().getColor(player.getUniqueId().toString()) +
                        getDeathsLifetime(player)}));
        items.put(25, new ItemStackUtil().getItem(Material.IRON_SWORD, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "AqVv9mS8"),
                new String[]{"§8» §" + new LanguageUtil().getColor(player.getUniqueId().toString()) +
                        getKillsLifetime(player)}));

        String[] monthlyStats = new String[10];
        for (String uniqueId : databaseUtil.getTop(
                "monthlyStats", "points", "uniqueId", 10)) {

            Integer position =
                    databaseUtil.getRanking("monthlyStats", "points", "uniqueId", uniqueId);
            String name = new UniqueIdFetcher().getLastUsedName(uniqueId);

            monthlyStats[position - 1] = "§8» §7#§" + new LanguageUtil().getColor(uniqueId) + position + "§7. §8» §" +
                    new LanguageUtil().getColor(uniqueId) + name;

        }

        items.put(29, new ItemStackUtil().getItem(Material.GOLD_INGOT, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "JYruIpKz"), monthlyStats));

        String[] lifetimeStats = new String[10];
        for (String uniqueId : databaseUtil.getTop(
                "lifetimeStats", "points", "uniqueId", 10)) {

            Integer position =
                    databaseUtil.getRanking("lifetimeStats", "points", "uniqueId", uniqueId);
            String name = new UniqueIdFetcher().getLastUsedName(uniqueId);

            lifetimeStats[position - 1] = "§8» §7#§" + new LanguageUtil().getColor(uniqueId) + position + "§7. §8» §" +
                            new LanguageUtil().getColor(uniqueId) + name;

        }

        items.put(33, new ItemStackUtil().getItem(Material.GOLD_INGOT, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "JYruIpKz"), lifetimeStats));

        return GuiUtil.open5RowInventory(player,
                new LanguageUtil().getString(player.getUniqueId().toString(), "lVfMrh5j"), items);
    }
}
