package net.exceptionmc.commandexecutor;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.gamework.kits.KitContent;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (player.hasPermission("exception.build")) {
                if (strings.length == 0) {
                    if (!BuildFFA.builder.contains(player.getPlayer().getUniqueId())) {

                        BuildFFA.builder.add(player.getPlayer().getUniqueId());

                        player.setGameMode(GameMode.CREATIVE);
                        player.getInventory().clear();

                        player.sendMessage(BuildFFA.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "TC6PYcGl"));
                    } else {

                        BuildFFA.builder.remove(player.getPlayer().getUniqueId());

                        player.setGameMode(GameMode.SURVIVAL);
                        player.getInventory().clear();

                        new KitContent(new KitManager().getSelectedKit(player))
                                .setKitContentInPlayerInventory(player);

                        player.sendMessage(BuildFFA.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "E8U7J4f7"));
                    }
                } else if (strings.length == 1) {

                    Player target = Bukkit.getPlayer(strings[0]);

                    if (target != null) {
                        if (target != player) {
                            if (!BuildFFA.builder.contains(target.getPlayer().getUniqueId())) {

                                BuildFFA.builder.add(target.getPlayer().getUniqueId());

                                target.setGameMode(GameMode.CREATIVE);
                                target.getInventory().clear();

                                target.sendMessage(BuildFFA.getPrefix() +
                                        new LanguageUtil().getString(player.getUniqueId().toString(), "TC6PYcGl"));

                                player.sendMessage(BuildFFA.getPrefix() +
                                        new LanguageUtil().getString(player.getUniqueId().toString(), "gherAHdq")
                                                .replace("$target", target.getName()));
                            } else {

                                BuildFFA.builder.remove(target.getPlayer().getUniqueId());

                                target.setGameMode(GameMode.SURVIVAL);
                                target.getInventory().clear();

                                new KitContent(new KitManager().getSelectedKit(target))
                                        .setKitContentInPlayerInventory(target);

                                target.sendMessage(BuildFFA.getPrefix() +
                                        new LanguageUtil().getString(player.getUniqueId().toString(), "E8U7J4f7"));

                                player.sendMessage(BuildFFA.getPrefix() +
                                        new LanguageUtil().getString(player.getUniqueId().toString(), "kCbBrnSb")
                                                .replace("$target", target.getName()));
                            }
                        } else {
                            if (!BuildFFA.builder.contains(player.getPlayer().getUniqueId())) {

                                BuildFFA.builder.add(player.getPlayer().getUniqueId());

                                player.setGameMode(GameMode.CREATIVE);
                                player.getInventory().clear();

                                player.sendMessage(BuildFFA.getPrefix() +
                                        new LanguageUtil().getString(player.getUniqueId().toString(), "TC6PYcGl"));
                            } else {

                                BuildFFA.builder.remove(player.getPlayer().getUniqueId());

                                player.setGameMode(GameMode.SURVIVAL);
                                player.getInventory().clear();

                                new KitContent(new KitManager().getSelectedKit(player))
                                        .setKitContentInPlayerInventory(player);

                                player.sendMessage(BuildFFA.getPrefix() +
                                        new LanguageUtil().getString(player.getUniqueId().toString(), "E8U7J4f7"));
                            }
                        }
                    } else {

                        player.sendMessage(BuildFFA.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "5JnIVxvj"));
                    }
                } else {

                    player.sendMessage(BuildFFA.getPrefix() +
                            new LanguageUtil().getString(player.getUniqueId().toString(), "J8BxpV7y") +
                            " §9/build §7<§9player§7>");
                }
            } else {

                player.sendMessage(BuildFFA.getPrefix() +
                        new LanguageUtil().getString(player.getUniqueId().toString(), "HwIw1MJu"));
            }
        }

        return false;
    }
}
