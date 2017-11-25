package net.azulian.core.events;

import java.util.ArrayList;

import net.azulian.core.main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class buildmode
        implements Listener

{
    main plugin = main.getPlugin(main.class);

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if ((plugin.playerlist.contains(p))) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if ((plugin.playerlist.contains(p))) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (plugin.playerlist.contains(p)) {
            plugin.playerlist.remove(p);
        }


    }

    @EventHandler
    public void noUproot(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            if ((plugin.playerlist.contains(p))) {
                return;
            }
                event.setCancelled(true);
            }
        }
    }

