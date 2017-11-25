package net.azulian.core.commands;

import net.azulian.core.main;
import net.azulian.core.utils.ActionBarAPI;
import net.azulian.core.utils.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class broadcast implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("bc")) {
                {
                    String rank = plugin.getConfigManager().getPlayers().getString(p.getUniqueId() + ".Rank");
                    if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator")) {
                        if (args.length == 0) {
                            sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/bc <message>");
                            return true;
                        }
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            str.append(args[i]);
                            str.append(" ");
                        }
                        String msg = str.toString().trim();
                        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "BROADCAST" + ChatColor.GRAY + "] " + "» " + ChatColor.WHITE +
                                ChatColor.translateAlternateColorCodes('&', msg));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            TitleAPI.sendTitle(player, 40, 40, 40, ChatColor.GOLD + "" + ChatColor.BOLD + "BROADCAST", ChatColor.translateAlternateColorCodes('&', msg));
                        }

                    }
                    else {
                        p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                }
            }
            return true;
        }
    }

