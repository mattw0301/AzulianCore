package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class vanish implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        FileConfiguration config = plugin.getConfigManager().getPlayers();

        if(!(p.hasPermission("minemen.vanish"))) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (plugin.pvp.contains(p)) {
            p.sendMessage(ChatColor.RED + "You can't do this while in pvp mode!");
            return true;
        }
            if (!(plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                config.set(p.getUniqueId().toString() + ".VanishEnabled", true);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!(players.hasPermission("minemen.vanish.see"))) {
                        players.hidePlayer(p);
                    }
                }
               /* if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                    Team team = plugin.scoreboard.getScoreboard().getTeam("Vanished");
                    if(team == null){
                        team = plugin.scoreboard.getScoreboard().registerNewTeam("Vanished");
                    }
                    team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[VANISHED] "));
                    team.addPlayer(p);
                }
                else {
                    Team team = plugin.scoreboard.getScoreboard().getTeam("Vanished");
                    team.setPrefix("");
                }
                */
                    p.sendMessage(plugin.prefix + ChatColor.WHITE + " You are now vanished!");

            } else {
            /*
                Team team = plugin.scoreboard.getScoreboard().getTeam("Vanished");
                team.setPrefix("");
                */
                config.set(p.getUniqueId().toString() + ".VanishEnabled", false);
                for (Player players : Bukkit.getOnlinePlayers()) {
                        players.showPlayer(p);
                }
                p.sendMessage(plugin.prefix + ChatColor.WHITE + " You are now unvanished!");
            }
        return true;
    }
}
