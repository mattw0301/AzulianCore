package net.azulian.core.events;

import net.azulian.core.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class playerchat implements Listener{
    main plugin = main.getPlugin(main.class);

@EventHandler
    public void ASync(AsyncPlayerChatEvent event) {

       Player p = event.getPlayer();
    String rank = plugin.getConfigManager().getPlayers().getString(p.getUniqueId() + ".Rank");
    if (rank.equalsIgnoreCase("Owner")) {
        event.setFormat(ChatColor.RED + "[OWNER] " + p.getName() + ChatColor.YELLOW + " » " + ChatColor.WHITE + event.getMessage());
    }
    else if (rank.equalsIgnoreCase("Administrator")) {
        event.setFormat(ChatColor.RED + "[ADMIN] " + p.getName() + ChatColor.YELLOW + " » " + ChatColor.WHITE + event.getMessage());
    }
    else if (rank.equalsIgnoreCase("Moderator")) {
        event.setFormat(ChatColor.LIGHT_PURPLE + "[MOD] " + p.getName() + ChatColor.YELLOW + " » " + ChatColor.WHITE + event.getMessage());
    }
    else
        event.setFormat(ChatColor.GRAY + p.getName() + ChatColor.YELLOW + " » " + ChatColor.GRAY + event.getMessage());
    }

    }

