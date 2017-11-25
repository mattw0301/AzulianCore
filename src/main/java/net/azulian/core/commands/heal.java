package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class heal implements CommandExecutor{
    main plugin = main.getPlugin(main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (!sender.hasPermission("minemen.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            if (args.length == 0) {
                player.setHealth(20);
                player.setFoodLevel(20);
                player.sendMessage(plugin.prefix + ChatColor.WHITE + " You have been healed!");
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                return true;
            }
            if (args.length == 1) {
                target.setHealth(20);
                target.setFoodLevel(20);
                player.sendMessage(plugin.prefix + ChatColor.GOLD + " " + target.getName() + ChatColor.WHITE + " has been healed!");
                target.sendMessage(plugin.prefix + ChatColor.WHITE + " You have been healed!");
            }

        }

        return true;
    }
}