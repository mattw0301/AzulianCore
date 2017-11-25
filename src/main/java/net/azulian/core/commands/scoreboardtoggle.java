package net.azulian.core.commands;

import net.azulian.core.main;
import net.azulian.core.utils.SimpleScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.UUID;

public class scoreboardtoggle
     implements CommandExecutor {
    public String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "Minemen " + ChatColor.DARK_AQUA + "»";
    main plugin = main.getPlugin(main.class);
    int taskIDD;
    FileConfiguration config = plugin.getConfigManager().getPlayers();

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("togglescoreboard")) {
            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();
            if (args.length == 0) {

                if (!(plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".SBEnabled"))) {
                    SimpleScoreboard scoreboard = new SimpleScoreboard("§e§lPalific");
                    scoreboard.send(p);
                    taskIDD = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                        public void run() {
                            try {
                                String rank = plugin.getConfigManager().getPlayers().getString(p.getUniqueId() + ".Rank");
                                UUID id = p.getUniqueId();
                                Integer num = plugin.kills.get(id);
                                int onlinePlayers = plugin.getOnlinePlayers("ALL");
                                if (plugin.kills.get(id) == null) {
                                    if (scoreboard.get(12, "&7&m----------------------") != null) {
                                        scoreboard.remove(12, "&7&m----------------------");
                                    }
                                    if (scoreboard.get(10, "&f" + onlinePlayers + "/250") != null) {
                                        scoreboard.remove(10, "&f" + onlinePlayers + "/250");
                                    }
                                    if (scoreboard.get(11, "&7&m----------------------") != null) {
                                        scoreboard.remove(11, "&7&m----------------------");
                                    }
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&c&7&m----------------------"), 9);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&a&lOnline Players:"), 8);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&f" + onlinePlayers + "/250"), 7);
                                    scoreboard.add("   ", 6);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&a&lRank:"), 5);
                                    if (rank.equalsIgnoreCase("Owner")) {
                                        scoreboard.add(ChatColor.RED + "Owner", 4);
                                    }
                                    else if (rank.equalsIgnoreCase("Administrator")) {
                                        scoreboard.add(ChatColor.RED + "Administrator", 4);
                                    }
                                    else if (rank.equalsIgnoreCase("Moderator")) {
                                        scoreboard.add(ChatColor.LIGHT_PURPLE + "Moderator", 4);
                                    }
                                    else {
                                        scoreboard.add(ChatColor.GRAY + "None", 4);
                                    }
                                    scoreboard.add("  ", 3);
                                    scoreboard.add(ChatColor.YELLOW.toString() + "www.palific.net", 2);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"), 1);
                                    scoreboard.update();
                                } else {
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&c&7&m----------------------"), 12);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&a&lOnline Players:"), 11);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&f" + onlinePlayers + "/250"), 10);
                                    scoreboard.add("   ", 9);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&a&lRank:"), 8);
                                    if (rank.equalsIgnoreCase("Owner")) {
                                        scoreboard.add(ChatColor.RED + "Owner", 7);
                                    }
                                    else if (rank.equalsIgnoreCase("Administrator")) {
                                        scoreboard.add(ChatColor.RED + "Administrator", 7);
                                    }
                                    else if (rank.equalsIgnoreCase("Moderator")) {
                                        scoreboard.add(ChatColor.LIGHT_PURPLE + "Moderator", 7);
                                    }
                                    else {
                                        scoreboard.add(ChatColor.GRAY + "None", 7);
                                    }
                                    scoreboard.add("  ", 6);
                                    scoreboard.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Kills:", 5);
                                    scoreboard.add(ChatColor.WHITE + "" + num, 4);
                                    scoreboard.add("       ", 3);
                                    scoreboard.add(ChatColor.YELLOW.toString() + "www.palific.net", 2);
                                    scoreboard.add(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"), 1);
                                    scoreboard.update();
                                }
                            } catch (Exception e) {
                            }
                            /* Team team = scoreboard.getScoreboard().getTeam("Vanished");
                            if (team == null) {
                                team = scoreboard.getScoreboard().registerNewTeam("Vanished");
                            }
                            team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[VANISHED] "));
                            team.addPlayer(p);
                            scoreboard.update();
                            */
                        }

                    }, 5L, 5L);
                    config.set(p.getUniqueId().toString() + ".SBEnabled", true);
                    sender.sendMessage(prefix + ChatColor.WHITE + " Your scoreboard has been " + ChatColor.GREEN + "enabled!");
                    return true;
                } else {
                    config.set(p.getUniqueId().toString() + ".SBEnabled", false);
                    Bukkit.getScheduler().cancelTask(plugin.taskId2);
                    Bukkit.getScheduler().cancelTask(plugin.taskId1);
                    Bukkit.getScheduler().cancelTask(taskIDD);
                    sender.sendMessage(prefix + ChatColor.WHITE + " Your scoreboard has been " + ChatColor.RED + "disabled!");
                 /*   if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                        SimpleScoreboard scoreboard = new SimpleScoreboard("");
                        Team team = scoreboard.getScoreboard().getTeam("Vanished");
                        if (team == null) {
                            team = scoreboard.getScoreboard().registerNewTeam("Vanished");
                        }
                        team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[VANISHED] "));
                        team.addPlayer(p);
                        scoreboard.send(p);
                        return true;
                    } else {
                        SimpleScoreboard scoreboard1 = new SimpleScoreboard("");
                        scoreboard1.send(p);
                    }
                    */
                    SimpleScoreboard scoreboard = new SimpleScoreboard("");
                    scoreboard.send(p);
                }
                return true;


            }
        }
        return true;
    }
}

