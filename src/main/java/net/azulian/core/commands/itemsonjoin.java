package net.azulian.core.commands;

import com.avaje.ebean.annotation.EnumValue;
import net.azulian.core.main;
import net.azulian.core.utils.ActionBarAPI;
import net.azulian.core.utils.ActionBarMessageEvent;
import net.azulian.core.utils.TitleAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static org.bukkit.Material.DIAMOND_SWORD;
import static org.bukkit.Material.ENDER_PEARL;

public class itemsonjoin implements Listener {
    main plugin = main.getPlugin(main.class);
    HashMap<Player, Long> cooldown = new HashMap();
    private int cooldowntime = 3;

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player p = event.getPlayer();
        World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("Spawn." + ".World"));
        float yaw = plugin.getConfig().getInt("Spawn." + ".Yaw");
        float pitch = plugin.getConfig().getInt("Spawn." + ".Pitch");
        float x = plugin.getConfig().getInt("Spawn." + ".X");
        float y = plugin.getConfig().getInt("Spawn." + ".Y");
        float z = plugin.getConfig().getInt("Spawn." + ".Z");

        Location loc = new Location(world, x, y, z, yaw, pitch);
        p.teleport(loc);

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Server Selector");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Use this to select a server!");
        meta.setLore(lore);
        compass.setItemMeta(meta);

        ItemStack head = new ItemStack(Material.SKULL_ITEM,1,(byte) 3);
        SkullMeta skull = (SkullMeta) head.getItemMeta();
        skull.setOwner(p.getName());
        skull.setDisplayName(ChatColor.GOLD + "Options");
        ArrayList<String> headlore = new ArrayList<String>();
        headlore.add(ChatColor.GRAY + "Use this to edit your options!");
        skull.setLore(headlore);
        head.setItemMeta(skull);

        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
        ItemMeta endmeta = enderpearl.getItemMeta();

        endmeta.setDisplayName(ChatColor.GOLD + "Ender Pearl");
        ArrayList<String> endlore = new ArrayList<String>();
        endlore.add(ChatColor.GRAY + "Use this to teleport around the map!");
        endmeta.setLore(endlore);
        enderpearl.setItemMeta(endmeta);

        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        p.getInventory().setItem(4, enderpearl);
        p.getInventory().setItem(0, compass);
        p.getInventory().setItem(8, head);
        p.updateInventory();
        if (p.hasPermission("minemen.donor")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 3));
        } else {
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }
        p.setHealth(20);
        p.setFoodLevel(20);
            TitleAPI.sendTitle(p, 40, 40, 40, ChatColor.GOLD + "" + ChatColor.BOLD + "MinemenGames", ChatColor.WHITE + "play.minemen.games");


    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player p = event.getPlayer();


        if ((item == null) || (item.getType() == Material.AIR)) {
            return;
        }

        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            if (p.getPlayer().getInventory().getItemInHand().getItemMeta() != null) {
                if (p.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName() != null) {
                    if ((item.getType() == Material.COMPASS && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Server Selector"))) {
                        p.chat("/selector");
                    }
                }
            }
            else {
                return;
            }
            if (p.getPlayer().getInventory().getItemInHand().getItemMeta() != null) {
                if (p.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName() != null) {
                    if ((item.getType() == Material.SKULL_ITEM && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Options"))) {
                        p.chat("/settings");
                    }
                }
            }
        }
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (p.getPlayer().getInventory().getItemInHand().getItemMeta() != null) {
                if (p.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName() != null) {

                    if ((item.getType() == Material.INK_SACK) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Leave PvP Mode")) {
                        p.chat("/pvp");
                    }
                }
            }

        }
    }



    @EventHandler
    public void onQuit (PlayerQuitEvent event){
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onFoodLoss (FoodLevelChangeEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerFall(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
        }


}









