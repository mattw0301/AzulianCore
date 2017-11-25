package net.azulian.core.events;

import net.azulian.core.main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.*;

public class enderpearl implements Listener {
    int velMult = 3;
    main plugin = main.getPlugin(main.class);

    @EventHandler
    public void onThrowEnderPearl2(ProjectileLaunchEvent event) {
        if ((!(event.getEntity().getShooter() instanceof Player)) ||
                (!(event.getEntity() instanceof EnderPearl))) {
            return;
        }
        Player shooter = (Player)event.getEntity().getShooter();
        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
        ItemMeta endmeta = enderpearl.getItemMeta();

        endmeta.setDisplayName(ChatColor.GOLD + "Ender Pearl");
        ArrayList<String> endlore = new ArrayList<String>();
        endlore.add(ChatColor.GRAY + "Use this to teleport around the map!");
        endmeta.setLore(endlore);
        enderpearl.setItemMeta(endmeta);
        shooter.getInventory().setItem(4, enderpearl);
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
    public void run() {
        shooter.getInventory().setItem(4, enderpearl);
    }
}, 1);
    }


    @EventHandler
    public void onThrowEnderPearl(ProjectileLaunchEvent event)
    {
        if ((!(event.getEntity().getShooter() instanceof Player)) ||
                (!(event.getEntity() instanceof EnderPearl))) {
        return;
        }
        Player shooter = (Player) event.getEntity().getShooter();
        if ((!plugin.items.contains(plugin.itemtoggle))) {
            Vector velocity = event.getEntity().getLocation().getDirection();
            if ((shooter.isOnGround()) && (shooter.getLocation().getPitch() < 15.0F)) {
                Location loc = shooter.getLocation();
                loc.setY(loc.getY() + 0.5D);
                shooter.teleport(loc);
            }
            velocity = velocity.setY(-velocity.getY() * this.velMult);
            velocity = velocity.setX(-velocity.getX() * this.velMult);
            velocity = velocity.setZ(velocity.getZ() * this.velMult);
            shooter.setVelocity(velocity);
            event.setCancelled(true);
        }
        else {
            shooter.sendMessage(ChatColor.RED + "This is currently disabled!");
            event.setCancelled(true);
        }
        }


        }








