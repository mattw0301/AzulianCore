package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tp implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        String rank = plugin.getConfigManager().getPlayers().getString(player.getUniqueId() + ".Rank");
        if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator") || rank.equalsIgnoreCase("Moderator")) {
            if (cmd.getName().equalsIgnoreCase("tp")) {
                if (!sender.hasPermission("minemen.admin")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/tp <username>");
                    return true;
                }
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                    return true;
                }
                if (args.length == 1) {
                    player.sendMessage(plugin.prefix + " " + ChatColor.WHITE + "You have teleported to " + ChatColor.GOLD + target.getName() + ".");
                    player.teleport(target.getLocation());
                    return true;
                }
                Player target2 = Bukkit.getServer().getPlayer(args[1]);
                if (target2 == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find \"" + args[1] + "\" !");
                    return true;
                }
                if (args.length == 2) {
                    player.sendMessage(plugin.prefix + " " + ChatColor.GOLD + target.getName() + ChatColor.WHITE + " has been teleported to " + ChatColor.GOLD + target2.getName() + ".");
                    target.sendMessage(plugin.prefix + " " + ChatColor.WHITE + "You have teleported to " + ChatColor.GOLD + target2.getName() + ".");
                    target2.sendMessage(plugin.prefix + " " + ChatColor.GOLD + target.getName() + ChatColor.WHITE + " has been teleported to you.");
                    target.teleport(target2.getLocation());
                }
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("tphere")) {
                if (!sender.hasPermission("minemen.admin")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                }

                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/tphere <username>");
                    return true;
                }
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                    return true;
                }
                if (args.length == 1) {
                    player.sendMessage(plugin.prefix + " " + ChatColor.GOLD + target.getName() + ChatColor.WHITE + " has been teleported to you.");
                    target.sendMessage(plugin.prefix + " " + ChatColor.WHITE + "You have teleported to " + ChatColor.GOLD + player.getName() + ".");
                    target.teleport(player.getLocation());
                }
            }

        }
        else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
return true;
    }
}

