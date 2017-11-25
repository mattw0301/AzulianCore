package net.azulian.core.commands;


import java.util.ArrayList;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class buildmodecmd
        implements CommandExecutor {
    public String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "Minemen " + ChatColor.DARK_AQUA + "»";
    main plugin = main.getPlugin(main.class);
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bm")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Incorrect usage! Use this command like this: /bm <player>");
                    return true;
                }
                Player p = (Player) sender;
                if (((!p.hasPermission("buildmode.toggle")))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }
                if (args.length == 0) {
                    if (plugin.playerlist.contains(p)) {
                        plugin.playerlist.remove(p);
                        sender.sendMessage(prefix + ChatColor.WHITE + " Build mode has been " + ChatColor.RED + "disabled!");
                        return true;
                    } else {
                        plugin.playerlist.add(p);
                        sender.sendMessage(prefix + ChatColor.WHITE + " Build mode has been " + ChatColor.GREEN + "enabled!");
                        return true;
                    }
                }



        }
        if (((!sender.hasPermission("buildmode.others")))) {
            sender.sendMessage(
                    ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        Player target = Bukkit.getServer().getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
            return true;
        }
                if (args.length == 1) {
                if (plugin.playerlist.contains(target)) {
                    plugin.playerlist.remove(target);
                    sender.sendMessage(prefix + ChatColor.WHITE + " You have " + ChatColor.RED + "disabled " + ChatColor.GOLD +
                            target.getName() + "'s " + ChatColor.WHITE + "build mode!");
                    target.sendMessage(prefix + ChatColor.WHITE + " Build mode has been " + ChatColor.RED + "disabled!");
                    return true;
                } else {
                    plugin.playerlist.add(target);
                    sender.sendMessage(prefix + ChatColor.WHITE + " You have " + ChatColor.GREEN + "enabled " + ChatColor.GOLD +
                            target.getName() + "'s " + ChatColor.WHITE + "build mode!");
                    target.sendMessage(prefix + ChatColor.WHITE + " Build mode has been " + ChatColor.GREEN + "enabled!");
                    return true;

                }
}



        return true;
    }
}

