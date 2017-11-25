package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class killmobs implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    int killed = 0;
    private void killall()
    {
        World[] arrayOfWorld;
        int j = (arrayOfWorld = (World[])Bukkit.getServer().getWorlds().toArray(new World[0])).length;
        for (int i = 0; i < j; i++)
        {
            World world = arrayOfWorld[i];
            for (LivingEntity mobs : world.getLivingEntities()) {
                if (!(mobs instanceof Player))
                {
                    mobs.remove();
                    this.killed += 1;
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if ((cmd.getName().equalsIgnoreCase("killmobs")) &&
                ((sender instanceof Player)))
        {
            Player player = (Player)sender;
            if (player.hasPermission("minemen.admin"))
            {
                killall();
                player.sendMessage(plugin.prefix + ChatColor.GOLD + " " + this.killed + ChatColor.WHITE + " mobs have been cleared!");
                this.killed = 0;
            }
            else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        }
        return true;
    }
    }

