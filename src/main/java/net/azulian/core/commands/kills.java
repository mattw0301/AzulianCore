package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class kills implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kills")) {
            if (args.length == 0) {
                Player p = (Player) sender;
                UUID id = p.getUniqueId();
                Integer num = plugin.kills.get(id);
                if ((plugin.kills.get(id) == null)) {
                    p.sendMessage(plugin.prefix + " " + ChatColor.RED + "You are not currently in pvp mode!");
                    return true;
                } else {
                    p.sendMessage(plugin.prefix + ChatColor.GOLD + " " + "Your" + " kills: " + ChatColor.WHITE + num);
                }
                return true;
            } else if (args.length == 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                    return true;
                }
                Player p = (Player) sender;
                UUID id = target.getUniqueId();
                Integer num = plugin.kills.get(id);
                if ((plugin.kills.get(id) == null)) {
                    p.sendMessage(plugin.prefix + " " + ChatColor.GOLD + args[0] + ChatColor.WHITE + " is not currently in pvp mode!");
                } else {
                    p.sendMessage(plugin.prefix + ChatColor.GOLD + " " + target.getName() + "'s" + " kills: " + ChatColor.WHITE + num);
                }
                return true;
            } else {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/kills <username>");
            }
        }
    return true;
    }
}
