package net.azulian.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class kaboom implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("minemen.kaboom")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setVelocity(new Vector(0, 200, 0));
            player.playSound(player.getLocation(), Sound.EXPLODE, 10, 29);
            player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "KAAABOOM!");
            Location loc = player.getLocation();
            player.getWorld().strikeLightningEffect(loc);
        }


        return true;
    }

}
