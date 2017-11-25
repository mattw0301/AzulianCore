package net.azulian.core.events;

import net.azulian.core.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class moveevent implements Listener{
    main plugin = main.getPlugin(main.class);
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if ((plugin.move.contains(p))) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();
        if ((plugin.move.contains(p))) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if ((plugin.move.contains(p))) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (plugin.move.contains(p)) {
            plugin.move.remove(p);
        }
    }
}
