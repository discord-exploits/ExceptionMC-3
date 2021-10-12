package net.exceptionmc.commandexecutor;

import net.exceptionmc.BuildFFA;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.utils.KitSetupUtil;
import net.exceptionmc.utils.MapSetupUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildFFACommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("map")) {
                    if (strings[1].equalsIgnoreCase("create")) {

                        MapSetupUtil.startSetup(player);
                    }
                } else if (strings[0].equalsIgnoreCase("kit")) {
                    if (strings[1].equalsIgnoreCase("create")) {

                        KitSetupUtil.startSetup(player);
                    }
                } else {

                    player.sendMessage(BuildFFA.getPrefix() +
                            new LanguageUtil().getString(player.getUniqueId().toString(), "J8BxpV7y") +
                            "/buildffa <map/kit> create");
                }
            } else {

                player.sendMessage(BuildFFA.getPrefix() +
                        new LanguageUtil().getString(player.getUniqueId().toString(), "J8BxpV7y") +
                        "/buildffa <map/kit> create");
            }
        }

        return false;
    }
}