package net.azulian.core.commands;

import net.azulian.core.main;
import net.azulian.core.utils.SimpleScoreboard;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Material.DIAMOND_SWORD;
import static org.bukkit.Material.ENDER_PEARL;
import static org.bukkit.Material.FEATHER;

public class GUI implements CommandExecutor, Listener {

    main plugin = main.getPlugin(main.class);
    private HashMap<String, Integer> online = new HashMap<String, Integer>();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            int onlinePlayers = plugin.getOnlinePlayers("ALL");
            Player p = (Player) sender;
            Inventory gui = Bukkit.createInventory(null, 45, ChatColor.DARK_GRAY + "Server Selector");
            if ((!plugin.items.contains(plugin.itemtoggle))) {
                ItemStack practice;
                practice = new ItemStack(DIAMOND_SWORD);
                List<String> practiceLore = new ArrayList<>();
                practiceLore.add(ChatColor.DARK_GRAY + "PvP");
                practiceLore.add(ChatColor.GRAY + "");
                practiceLore.add(ChatColor.GRAY + "Practice your pvp skills");
                practiceLore.add(ChatColor.GRAY + "in an arena fight with other players!");
                practiceLore.add(ChatColor.GRAY + "");
                practiceLore.add(ChatColor.RED + "" + ChatColor.BOLD + "COMING SOON!");
                ItemMeta practiceMeta = practice.getItemMeta();
                practiceMeta.setDisplayName(ChatColor.AQUA + "Practice");
                practiceMeta.setLore(practiceLore);
                practiceMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                practice.setItemMeta(practiceMeta);

                gui.setItem(11, practice);

                ItemStack spawn;
                spawn = new ItemStack(ENDER_PEARL);
                List<String> spawnLore = new ArrayList<>();
                spawnLore.add(ChatColor.GRAY + "Teleport to spawn.");
                ItemMeta spawnMeta = spawn.getItemMeta();
                spawnMeta.setDisplayName(ChatColor.GREEN + "Spawn");
                spawnMeta.setLore(spawnLore);
                spawn.setItemMeta(spawnMeta);
                gui.setItem(40, spawn);

                ItemStack Skywars;
                Skywars = new ItemStack(FEATHER);
                List<String> SkywarsLore = new ArrayList<>();
                SkywarsLore.add(ChatColor.DARK_GRAY + "PvP");
                SkywarsLore.add(ChatColor.DARK_GRAY + "");
                SkywarsLore.add(ChatColor.GRAY + "Fight players on islands.");
                SkywarsLore.add(ChatColor.GRAY + "Gather loot from chests.");
                SkywarsLore.add(ChatColor.GRAY + "May the best player win!");
                SkywarsLore.add(ChatColor.DARK_GRAY + "");
                SkywarsLore.add(ChatColor.RED + "" + ChatColor.BOLD + "COMING SOON!");
                ItemMeta SkywarsMeta = Skywars.getItemMeta();
                SkywarsMeta.setDisplayName(ChatColor.GOLD + "Skywars");
                SkywarsMeta.setLore(SkywarsLore);
                Skywars.setItemMeta(SkywarsMeta);
                gui.setItem(13, Skywars);
                p.openInventory(gui);
            } else {
                p.sendMessage(ChatColor.RED + "This is currently disabled!");
            }
        }
        catch (Exception e){
            Player p = (Player) sender;
            p.sendMessage(ChatColor.RED + "Please wait...");
        }
        return true;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ByteArrayOutputStream b = new ByteArrayOutputStream();

        DataOutputStream out = new DataOutputStream(b);
        if (e.getCurrentItem() == null) {
            return;
        }
            if (ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle()).equals(ChatColor.DARK_GRAY + "Server Selector")) {
                if (e.getCurrentItem() == null) {
                    return;
                }

                switch (e.getCurrentItem().getType()) {
                    case DIAMOND_SWORD:
                        p.sendMessage(ChatColor.GOLD + "Practice is coming soon!");
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10, 29);
                       /* try {
                            //out.writeUTF("Connect");
                            //out.writeUTF("lobby");

                        } catch (IOException eee) {
                            eee.printStackTrace();
                        }*/
                       // p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
                        p.closeInventory();
                        break;
                    case ENDER_PEARL:
                        p.chat("/spawn");
                        p.closeInventory();
                        break;
                    case FEATHER:
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10, 29);
                        p.closeInventory();
                        p.sendMessage(ChatColor.GOLD + "Skywars is coming soon!");
                        break;
                    default:
                        return;
                }
            }


        }




    }

