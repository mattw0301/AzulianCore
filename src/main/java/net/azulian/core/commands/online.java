package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;

public class online implements CommandExecutor {
    main plugin = main.getPlugin(main.class);
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
            int onlinePlayers = plugin.getOnlinePlayers("ALL");
            Player p = (Player) sender;
            p.sendMessage(ChatColor.GOLD + "There is currently " + ChatColor.DARK_AQUA + ChatColor.BOLD + onlinePlayers + ChatColor.GOLD + " players on " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Minemen Games.");


        return true;
    }
}
