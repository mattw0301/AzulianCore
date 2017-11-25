package net.azulian.core.commands;

import net.azulian.core.main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Material.*;

public class lobbysettings implements CommandExecutor, Listener{
    main plugin = main.getPlugin(main.class);
    private HashMap<String, Integer> online = new HashMap<String, Integer>();
    public String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "Minemen " + ChatColor.DARK_AQUA + "»";
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        Player p = (Player) sender;
        Inventory gui = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "Options");
        if ((!plugin.items.contains(plugin.itemtoggle))) {
            ItemStack practice;
            practice = new ItemStack(DIAMOND_SWORD);
            List<String> practiceLore = new ArrayList<>();
            ItemMeta practiceMeta = practice.getItemMeta();
            practiceMeta.setDisplayName(ChatColor.RED+ "Enter PvP mode!");
            practiceLore.add(ChatColor.GRAY + "Click to enter PvP mode!");
            practiceMeta.setLore(practiceLore);
            practiceMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            practice.setItemMeta(practiceMeta);

            gui.setItem(11, practice);

            ItemStack togglesb;
            togglesb = new ItemStack(PAINTING);
            List<String> togglesbLore = new ArrayList<>();
            ItemMeta togglesbMeta = togglesb.getItemMeta();
            togglesbMeta.setDisplayName(ChatColor.DARK_AQUA + "Toggle Scoreboard");
            togglesbLore.add(ChatColor.GRAY + "Toggle your scoreboard.");
            togglesbMeta.setLore(togglesbLore);
            togglesb.setItemMeta(togglesbMeta);

            gui.setItem(30, togglesb);

            ItemStack togglepv;
            togglepv = new ItemStack(ENDER_PEARL);
            List<String> togglepvLore = new ArrayList<>();
            ItemMeta togglepvMeta = togglepv.getItemMeta();
            togglepvMeta.setDisplayName(ChatColor.GREEN + "Toggle Player Visibility");
            togglepvLore.add(ChatColor.GRAY + "Toggle players.");
            togglepvMeta.setLore(togglepvLore);
            togglepv.setItemMeta(togglepvMeta);

            gui.setItem(32, togglepv);
            if ((p.hasPermission("minemen.donor"))) {

                ItemStack donorspeed;
                donorspeed = new ItemStack(GOLD_INGOT);
                List<String> donorspeedlore = new ArrayList<>();
                donorspeedlore.add(ChatColor.GRAY + "Click to toggle speed!");
                ItemMeta donorspeedItemMeta = donorspeed.getItemMeta();
                donorspeedItemMeta.setDisplayName(ChatColor.GOLD + "Toggle Speed");
                donorspeedItemMeta.setLore(donorspeedlore);
                donorspeed.setItemMeta(donorspeedItemMeta);
                gui.setItem(15, donorspeed);

            } else {
                ItemStack donorspeed;
                donorspeed = new ItemStack(GOLD_INGOT);
                List<String> donorspeedlore1 = new ArrayList<>();
                donorspeedlore1.add(ChatColor.RED + "You must be a donor to use this!");
                ItemMeta donorspeedItemMeta = donorspeed.getItemMeta();
                donorspeedItemMeta.setDisplayName(ChatColor.GOLD + "Toggle Speed");
                donorspeedItemMeta.setLore(donorspeedlore1);
                donorspeed.setItemMeta(donorspeedItemMeta);
                gui.setItem(15, donorspeed);
            }
            if ((p.hasPermission("minemen.donor"))) {



                ItemStack donorflight;
                donorflight = new ItemStack(FEATHER);
                List<String> donorflyLore = new ArrayList<>();
                donorflyLore.add(ChatColor.GRAY + "Click to toggle flight!");
                ItemMeta donorflightItemMeta = donorflight.getItemMeta();
                donorflightItemMeta.setDisplayName(ChatColor.GOLD + "Toggle Flight");
                donorflightItemMeta.setLore(donorflyLore);
                donorflight.setItemMeta(donorflightItemMeta);
                gui.setItem(13, donorflight);

            } else {
                ItemStack donorflight;
                donorflight = new ItemStack(FEATHER);
                List<String> donorfly1Lore = new ArrayList<>();
                donorfly1Lore.add(ChatColor.RED + "You must be a donor to use this!");
                ItemMeta donorflightItemMeta = donorflight.getItemMeta();
                donorflightItemMeta.setDisplayName(ChatColor.GOLD + "Toggle Flight");
                donorflightItemMeta.setLore(donorfly1Lore);
                donorflight.setItemMeta(donorflightItemMeta);
                gui.setItem(13, donorflight);


            }
            p.openInventory(gui);
        } else {
            p.sendMessage(ChatColor.RED + "This is currently disabled!");
        }
        return true;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ByteArrayOutputStream b = new ByteArrayOutputStream();

        DataOutputStream out = new DataOutputStream(b);
        if (ChatColor.translateAlternateColorCodes('&', e.getInventory().getTitle()).equals(ChatColor.DARK_GRAY + "Options")) {
            if (e.getCurrentItem() == null) {
                return;
            }
            switch (e.getCurrentItem().getType()) {
                case ENDER_PEARL:
                    UUID id = p.getUniqueId();
                    FileConfiguration config = plugin.getConfigManager().getPlayers();
                    if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".PVEnabled"))) {
                        config.set(p.getUniqueId().toString() + ".PVEnabled", false);
                        plugin.getConfigManager().savePlayers();
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (!(players.hasPermission("minemen.antivanish"))) {
                                p.hidePlayer(players);
                            }
                        }
                        p.sendMessage(prefix + ChatColor.WHITE + " Players have been " + ChatColor.RED + "disabled!");
                    }
                    else {
                        config.set(p.getUniqueId().toString() + ".PVEnabled", true);
                        plugin.getConfigManager().savePlayers();
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (!(plugin.getConfigManager().getPlayers().getBoolean(players.getUniqueId().toString() + ".VanishEnabled"))) {
                                p.showPlayer(players);
                            }
                        }
                        p.sendMessage(prefix + ChatColor.WHITE + " Players have been " + ChatColor.GREEN + "enabled!");
                    }
                    p.closeInventory();
                    break;
                case DIAMOND_SWORD:
                    p.chat("/pvp");
                    p.closeInventory();
                    break;

                case PAINTING:
                    p.chat("/togglesb");
                    p.closeInventory();
                    break;
                case GOLD_INGOT:
                    if ((!p.hasPermission("minemen.donor"))) {
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10, 29);
                        p.sendMessage(ChatColor.RED + "You must be a donor to use this!");
                        p.closeInventory();
                    }
                    else {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            for (PotionEffect effect : p.getActivePotionEffects())
                                p.removePotionEffect(effect.getType());
                            p.sendMessage(prefix + ChatColor.WHITE + " Speed disabled.");
                            p.closeInventory();
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 3));
                            p.sendMessage(prefix + ChatColor.WHITE + " Speed enabled.");
                            p.closeInventory();
                        }
                    }
                    break;
                case FEATHER:
                    if ((!p.hasPermission("minemen.donor"))) {
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10, 29);
                        p.sendMessage(ChatColor.RED + "You must be a donor to use this!");
                        p.closeInventory();
                    }
                    else {
                        p.chat("/fly");
                        p.closeInventory();
                    }
                    break;
                default:
                    return;
            }
        }

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String id = p.getUniqueId().toString();
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!(p.hasPermission("minemen.antivanish"))) {
                if (!(plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".PVEnabled"))) {
                    p.hidePlayer(players);

                }
                if (!(plugin.getConfigManager().getPlayers().getBoolean(players.getUniqueId().toString() + ".PVEnabled"))) {
                    players.hidePlayer(p);

                }
            }
            else {
                if (!(plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".PVEnabled"))) {
                    p.hidePlayer(players);
                }
            }
           if (players.hasPermission("minemen.antivanish")){
                if ((!(plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".PVEnabled")))) {
                   p.showPlayer(players);
               }
            }
            if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                if (!(players.hasPermission("minemen.vanish.see"))) {
                    players.hidePlayer(p);
                }
            }
            if ((plugin.getConfigManager().getPlayers().getBoolean(players.getUniqueId().toString() + ".VanishEnabled"))) {
                if (!(p.hasPermission("minemen.vanish.see"))) {
                    p.hidePlayer(players);
                }
            }
        }
    }

}

