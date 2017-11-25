package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.Console;
import java.util.UUID;

public class rank implements CommandExecutor{
    main plugin = main.getPlugin(main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        FileConfiguration config = plugin.getConfigManager().getPlayers();
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /rank set <username> <rank>");
                sender.sendMessage(ChatColor.RED + "/rank <username>");
                sender.sendMessage(ChatColor.RED + "/rank list");
            } else if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Ranks: " + ChatColor.WHITE + "Owner, Administrator, Moderator, Default");
            } else if (args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (target.hasPlayedBefore()) {
                    String targetrank = plugin.getConfigManager().getPlayers().getString(target.getUniqueId() + ".Rank");
                    sender.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " has the rank of " + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + targetrank + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" has never joined the server before!");
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target.hasPlayedBefore()) {
                    UUID uuid = target.getUniqueId();
                    if (args[2].equalsIgnoreCase("Owner") || args[2].equalsIgnoreCase("Administrator") || args[2].equalsIgnoreCase("Moderator") || args[2].equalsIgnoreCase("Default")) {
                        config.set(uuid.toString() + ".Rank", args[2].toUpperCase());
                        plugin.getConfigManager().savePlayers();
                        sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + target.getName() + "'s" + ChatColor.WHITE + " rank has been set to " + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + args[2].toUpperCase() + "!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid Rank!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "\"" + args[1] + "\" has never joined the server before!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /rank set <username> <rank>");
                sender.sendMessage(ChatColor.RED + "/rank <username>");
            }
            return true;
        }
        Player p = (Player) sender;
        String rank = plugin.getConfigManager().getPlayers().getString(p.getUniqueId() + ".Rank");
        if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Usage: /rank set <username> <rank>");
                p.sendMessage(ChatColor.RED + "/rank <username>");
                p.sendMessage(ChatColor.RED + "/rank list");
            } else if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Ranks: " + ChatColor.WHITE + "Owner, Administrator, Moderator, Default");
            } else if (args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (target.hasPlayedBefore()) {
                    String targetrank = plugin.getConfigManager().getPlayers().getString(target.getUniqueId() + ".Rank");
                    p.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " has the rank of " + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + targetrank + "!");
                } else {
                    p.sendMessage(ChatColor.RED + "\"" + args[0] + "\" has never joined the server before!");
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                Player target2 = Bukkit.getServer().getPlayer(args[1]);
                if (target.hasPlayedBefore()) {
                    UUID uuid = target.getUniqueId();
                    if (args[2].equalsIgnoreCase("Owner") || args[2].equalsIgnoreCase("Administrator") || args[2].equalsIgnoreCase("Moderator") || args[2].equalsIgnoreCase("Default")) {
                        config.set(uuid.toString() + ".Rank", args[2].toUpperCase());
                        plugin.getConfigManager().savePlayers();
                        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + target.getName() + "'s" + ChatColor.WHITE + " rank has been set to " + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + args[2].toUpperCase() + "!");
                        if (target2 == null) {

                        }
                        else {

                            target2.sendMessage(ChatColor.WHITE + "Your rank has been updated to " + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + args[2].toUpperCase() + "!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid Rank!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "\"" + args[1] + "\" has never joined the server before!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Usage: /rank set <username> <rank>");
                p.sendMessage(ChatColor.RED + "/rank <username>");
            }
        }
        else {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
}
