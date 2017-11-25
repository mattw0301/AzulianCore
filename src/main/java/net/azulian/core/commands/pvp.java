package net.azulian.core.commands;

import net.azulian.core.main;
import net.azulian.core.utils.ActionBarAPI;
import net.azulian.core.utils.TitleAPI;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.rmi.server.UID;
import java.util.*;

public class pvp implements CommandExecutor, Listener {
    main plugin = main.getPlugin(main.class);
    HashMap<UUID, BukkitRunnable> countdown = new HashMap();
    BukkitTask taskID;
    List<Player> ex = new ArrayList();


    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

        final Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("pvp")) {
            if (!plugin.pvp.contains(p)) {
                plugin.pvp.add(p);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                        players.showPlayer(p);
                    }
                }
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.setFallDistance(-1.0E7F);
                }
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                p.getInventory().clear();
                p.getInventory().addItem(sword);
                p.getInventory().setHelmet(helmet);
                p.getInventory().setChestplate(chestplate);
                p.getInventory().setLeggings(leggings);
                p.getInventory().setBoots(boots);
                ItemStack leave = new ItemStack(Material.INK_SACK, 1, (short) DyeColor.RED.getData());
                ItemMeta meta = leave.getItemMeta();
                UUID id = p.getUniqueId();
                Integer num = plugin.kills.get(id);
                plugin.kills.put(id, 0);
                num = 0;
                meta.setDisplayName(ChatColor.GOLD + "Leave PvP Mode");
                ArrayList<String> dyelore = new ArrayList<String>();
                dyelore.add(ChatColor.GRAY + "Click this to leave pvp mode!");
                meta.setLore(dyelore);
                leave.setItemMeta(meta);
                p.getInventory().setItem(8, leave);
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 29);
                p.sendMessage(plugin.prefix + ChatColor.WHITE + " You have entered pvp mode!");
                for (PotionEffect effect : p.getActivePotionEffects())
                    p.removePotionEffect(effect.getType());
                TitleAPI.sendTitle(p, 40, 40, 40, ChatColor.GOLD + "" + ChatColor.BOLD + "MinemenGames", ChatColor.WHITE + "You have entered pvp mode!");
                return true;
            } else {
               /* ex.add(p);
                p.sendMessage(plugin.prefix + ChatColor.WHITE + " Teleporting... Don't move. Wait 5 seconds.");
                // p.sendMessage(ChatColor.RED + "Counting down..");
                Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                            p.sendMessage("Teleported");

                        if (ex.contains(p)) {
                            p.sendMessage("§cYou waiting for teleport");
                        } else

                        {
                            ex.add(p);
                            p.sendMessage("§aDon't move you will be teleported in 5 seconds");
                        }
                    }
                    }, 100L);
                    // p.sendMessage(ChatColor.RED + "" + i1);
                    */
                    plugin.pvp.remove(p);
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

                for (Player players : Bukkit.getOnlinePlayers()) {
                    if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                        players.hidePlayer(p);
                    }
                }

                    meta.setDisplayName(ChatColor.GOLD + "Server Selector");
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.GRAY + "Use this to select a server!");
                    meta.setLore(lore);
                    compass.setItemMeta(meta);
                    p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 29);
                    plugin.cooldown.put(p, System.currentTimeMillis() + (5 * 1000));

                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta skull = (SkullMeta) head.getItemMeta();
                    skull.setOwner(p.getName());
                    skull.setDisplayName(ChatColor.GOLD + "Options");
                    ArrayList<String> headlore = new ArrayList<String>();
                    headlore.add(ChatColor.GRAY + "Use this to edit your options!");
                    skull.setLore(headlore);
                    head.setItemMeta(skull);
                    p.sendMessage(plugin.prefix + ChatColor.WHITE + " You have left pvp mode!");
                    p.setHealth(20);
                    ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
                    ItemMeta endmeta = enderpearl.getItemMeta();
                    UUID id = p.getUniqueId();
                    Integer num = plugin.kills.get(id);

                    plugin.kills.remove(id);
                    num = 0;
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
                    p.getInventory().setItem(0, compass);
                    p.getInventory().setItem(8, head);
                    p.getInventory().setItem(4, enderpearl);
                    p.updateInventory();
                    if (p.hasPermission("minemen.donor")) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 3));
                    } else {
                        for (PotionEffect effect : p.getActivePotionEffects())
                            p.removePotionEffect(effect.getType());
                    }
                    TitleAPI.sendTitle(p, 40, 40, 40, ChatColor.GOLD + "" + ChatColor.BOLD + "MinemenGames", ChatColor.WHITE + "You have left pvp mode!");

                }

            }
            return true;
        }

   /* @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (ex.contains(p)) {
            p.sendMessage("moved");
            ex.remove(p);
        }
    }*/

}



















