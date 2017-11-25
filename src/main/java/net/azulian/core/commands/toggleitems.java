package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.ArrayList;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class toggleitems
        implements CommandExecutor {
    public String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "Minemen " + ChatColor.DARK_AQUA + "»";
    main plugin = main.getPlugin(main.class);

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("toggleitems")) {
            Player p = (Player) sender;
            if (((!p.hasPermission("items.toggle")))) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }
            if (args.length == 0) {


                if (!plugin.items.contains(plugin.itemtoggle)) {
                    plugin.items.add(plugin.itemtoggle);
                    sender.sendMessage(prefix + ChatColor.WHITE + " Items have been " + ChatColor.RED + "disabled!");
                    return true;
                } else {
                    plugin.items.remove(plugin.itemtoggle);
                    sender.sendMessage(prefix + ChatColor.WHITE + " Items have been " + ChatColor.GREEN + "enabled!");
                    return true;
                }
            }

        }
        return true;
    }
}




