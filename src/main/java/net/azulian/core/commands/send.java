package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class send implements CommandExecutor, Listener{
    main plugin = main.getPlugin(main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        Player player = (Player) sender;
        ByteArrayOutputStream b = new ByteArrayOutputStream();

        DataOutputStream out = new DataOutputStream(b);
        if (cmd.getName().equalsIgnoreCase("send"))

        {
            if (!sender.hasPermission("enderraids.mod")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/send <username>");
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Could not find player!");
                return true;
            }
            if (args.length == 2) {
                player.sendMessage(ChatColor.BLUE + "Sending " + target.getName() + " to practice");
                try {

                    out.writeUTF("ConnectOther");
                    out.writeUTF(target.getName());
                    out.writeUTF(args[1]);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
                return true;
            }
            Player target2 = Bukkit.getServer().getPlayer(args[1]);
            if (target2 == null) {
                player.sendMessage(ChatColor.RED + "Could not find player!");
                return true;


            }
            String test = "test";
        }
        return true;
    }



    }

