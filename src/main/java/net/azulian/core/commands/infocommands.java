package net.azulian.core.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.azulian.core.main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class infocommands implements CommandExecutor, Listener{
    main plugin = main.getPlugin(main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        Player p = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("practice")) {
            p.sendMessage(ChatColor.AQUA + "You are being sent to practice!");
            ByteArrayOutputStream b = new ByteArrayOutputStream();

            DataOutputStream out = new DataOutputStream(b);

            try {

                out.writeUTF("Connect");

                out.writeUTF("Practice"); // Name of the server to connect to

            } catch (IOException eee) {
                p.sendMessage(ChatColor.RED + "Practice is offline.");
            }

            p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());

        }


        return true;
    }




}
