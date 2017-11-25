package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gamemode implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("gmc")) {
            String rank = plugin.getConfigManager().getPlayers().getString(player.getUniqueId() + ".Rank");
            if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator")) {

                if (args.length == 0) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(plugin.prefix + ChatColor.WHITE + " Gamemode set to creative.");
                    return true;
                }
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                    return true;
                }
                if (args.length == 1) {
                    target.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(plugin.prefix + ChatColor.DARK_AQUA + " " + target.getName() + ChatColor.WHITE + " has been set to creative mode.");
                    target.sendMessage(plugin.prefix + ChatColor.WHITE + " Gamemode set to creative.");
                    return true;
                }

            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("gms")) {
            String rank = plugin.getConfigManager().getPlayers().getString(player.getUniqueId() + ".Rank");
            if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator")) {

                if (args.length == 0) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(plugin.prefix + ChatColor.WHITE + " Gamemode set to survival.");
                    player.setFallDistance(-1.0E7F);
                    return true;
                }
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                    return true;
                }
                if (args.length == 1) {
                    target.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(plugin.prefix + ChatColor.DARK_AQUA + " " + target.getName() + ChatColor.WHITE + " has been set to survival mode.");
                    target.sendMessage(plugin.prefix + ChatColor.WHITE + " Gamemode set to survival.");
                    target.setFallDistance(-1.0E7F);
                    return true;

                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                }
            }
            }
            if (cmd.getName().equalsIgnoreCase("fly")) {
                String rank = plugin.getConfigManager().getPlayers().getString(player.getUniqueId() + ".Rank");
                if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator") || rank.equalsIgnoreCase("Moderator")) {
                    if (plugin.pvp.contains(player)) {
                        player.sendMessage(ChatColor.RED + "You can't do this while in pvp mode!");
                        return true;
                    }
                    if (args.length == 0) {
                        if (player.getAllowFlight()) {
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 10, 29);
                            player.sendMessage(plugin.prefix + ChatColor.WHITE + " Fly disabled.");
                            player.setFallDistance(-1.0E7F);
                            return true;
                        } else {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 10, 29);
                            player.sendMessage(plugin.prefix + ChatColor.WHITE + " Fly enabled.");
                            return true;

                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator")) {
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                            return true;
                        }
                        if (args.length == 1) {
                            if (target.getAllowFlight()) {
                                target.setAllowFlight(false);
                                target.setFlying(false);
                                target.playSound(target.getLocation(), Sound.BAT_TAKEOFF, 10, 29);
                                player.sendMessage(plugin.prefix + ChatColor.LIGHT_PURPLE + " " + ChatColor.WHITE + " Fly disabled for " + ChatColor.LIGHT_PURPLE +
                                        target.getName() + ".");
                                target.sendMessage(plugin.prefix + ChatColor.WHITE + " Fly disabled.");
                                target.setFallDistance(-1.0E7F);
                                return true;
                            } else {
                                target.setAllowFlight(true);
                                target.setFlying(true);
                                target.playSound(target.getLocation(), Sound.BAT_TAKEOFF, 10, 29);
                                player.sendMessage(plugin.prefix + ChatColor.LIGHT_PURPLE + " " + ChatColor.WHITE + " Fly enabled for " + ChatColor.LIGHT_PURPLE +
                                        target.getName() + ".");
                                target.sendMessage(plugin.prefix + ChatColor.WHITE + " Fly enabled.");
                                return true;
                            }
                        }
                    }

                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    if (cmd.getName().equalsIgnoreCase("gm3")) {
                        if (rank.equalsIgnoreCase("Owner") || rank.equalsIgnoreCase("Administrator")) {

                            if (args.length == 0) {
                                player.setGameMode(GameMode.SPECTATOR);
                                player.sendMessage(plugin.prefix + ChatColor.WHITE + " Gamemode set to spectator.");
                                player.setFallDistance(-1.0E7F);
                                return true;
                            }
                            Player target = Bukkit.getServer().getPlayer(args[0]);
                            if (target == null) {
                                sender.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\" !");
                                return true;
                            }
                            if (args.length == 1) {
                                target.setGameMode(GameMode.SPECTATOR);
                                player.sendMessage(plugin.prefix + ChatColor.LIGHT_PURPLE + " " + target.getName() + ChatColor.WHITE + " has been set to spectator mode.");
                                target.sendMessage(plugin.prefix + ChatColor.WHITE + " Gamemode set to spectator.");
                                return true;
                            }

                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                        }
                    }
                    }
                    return true;


        }

    }
