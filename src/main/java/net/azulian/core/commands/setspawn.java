package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    public String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "Minemen " + ChatColor.DARK_AQUA + "»";
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        Location loc = player.getLocation();
        plugin.getConfig().set("Spawn." + ".World", player.getLocation().getWorld().getName());
        plugin.getConfig().set("Spawn."  + ".Yaw", loc.getYaw());
        plugin.getConfig().set("Spawn." + ".Pitch", loc.getPitch());
        plugin.getConfig().set("Spawn." + ".X", loc.getX());
        plugin.getConfig().set("Spawn."  + ".Y", loc.getY());
        plugin.getConfig().set("Spawn." + ".Z", loc.getZ());
        plugin.saveConfig();

        player.sendMessage(prefix + ChatColor.WHITE + " The spawn has been set!");

        return true;
    }
}