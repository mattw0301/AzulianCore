package net.azulian.core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.azulian.core.commands.*;
import net.azulian.core.events.*;
import net.azulian.core.utils.ActionBarAPI;
import net.azulian.core.utils.ConfigManager;
import net.azulian.core.utils.SimpleScoreboard;
import net.azulian.core.utils.TitleAPI;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

public class main extends JavaPlugin implements Listener, PluginMessageListener {

    public boolean itemtoggle = true;
    public boolean sbenabled = true;
    public boolean sbedisabled = false;


    public List<UUID> sbe = new ArrayList<>();
    public List<Player> pv = new ArrayList<>();
    public HashMap<UUID, Integer> kills = new HashMap<>();
    public ArrayList<Player> playerlist = new ArrayList();
    public ArrayList<Player> move = new ArrayList();
    public ArrayList<Boolean> sbenabledlist = new ArrayList();
    public ArrayList<Boolean> items = new ArrayList();
    public ArrayList<Player> pvp = new ArrayList<>();
    public HashMap<Player, Long> cooldown = new HashMap<>();
    public ArrayList<Player> cooldownEnabled = new ArrayList<>();
    public String prefix = ChatColor.YELLOW + "" + ChatColor.BOLD + "Palific " + ChatColor.GREEN + "»";

    public int taskId;
    public int taskId2;
    public int taskId1;
    public int taskId3;
    public int taskId4;
    public int tab2;
    public int tab3;
    private ConfigManager cfgm;


    @Override
    public void onDisable() {

        System.out.println("Minemen Core has been disabled!");
    }

    private HashMap<String, Integer> onlinePlayers = new HashMap<String, Integer>();


    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String command = in.readUTF();

            if (command.equals("PlayerCount")) {
                String server = in.readUTF();
                int playerCount = in.readInt();

                onlinePlayers.put(server, playerCount);

            } else {
                return;
            }
        } catch (Exception e) {

        }
    }

    public void refreshOnline(String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            out.writeUTF("PlayerCount");
            out.writeUTF(server);

            Bukkit.getServer().sendPluginMessage(this, "BungeeCord", b.toByteArray());
        } catch (Exception e) {

        }
    }

    public int getOnlinePlayers(String server) {
        return this.onlinePlayers.get(server);
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                try {
                    int players = Bukkit.getOnlinePlayers().size();
                    int onlinePlayers = getOnlinePlayers("ALL");
                    TitleAPI.sendTabTitle(p, ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH +
                            "[-" + ChatColor.RESET + " " + ChatColor.YELLOW + ChatColor.BOLD + "Palific" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH
                            + "-]" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------\n", "\n" + ChatColor.GREEN + ChatColor.BOLD + "* " + ChatColor.YELLOW + ChatColor.BOLD + onlinePlayers + " PLAYERS ONLINE" + ChatColor.GREEN + "" + ChatColor.BOLD + " *" + "\n" +
                            ChatColor.GRAY + "      (" + players + " in your server) " + ChatColor.YELLOW + "\n\nwww.palific.net");
                } catch (Exception e) {

                }
                if (pvp.contains(p)) {
                    ActionBarAPI.sendActionBar(p, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "» " + ChatColor.GOLD + ChatColor.BOLD + "You are currently in pvp mode!" + ChatColor.DARK_AQUA + ChatColor.BOLD + " «");

                } else if ((getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                    ActionBarAPI.sendActionBar(p, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "» " + ChatColor.GOLD + ChatColor.BOLD + "You are currently vanished!" + ChatColor.DARK_AQUA + ChatColor.BOLD + " «");
                } else {
                    ActionBarAPI.sendActionBar(p, "");
                }
            }
        }, 5L, 5L);
        if ((getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".SBEnabled"))) {
            SimpleScoreboard scoreboard = new SimpleScoreboard("§e§lPalific");
            scoreboard.send(p);
            taskId1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    try {
                        String rank = getConfigManager().getPlayers().getString(p.getUniqueId() + ".Rank");
                        UUID id = p.getUniqueId();
                        Integer num = kills.get(id);
                        int onlinePlayers = getOnlinePlayers("ALL");
                        if (kills.get(id) == null) {
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
                }
            }, 5L, 5L);
        }
    }


      /*  if ((getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
            Team team = scoreboard.getScoreboard().getTeam("Vanished");
            if(team == null){
                team = scoreboard.getScoreboard().registerNewTeam("Vanished");
            }
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[VANISHED] "));
            team.addPlayer(p);
        }
        else {
            Team team = scoreboard.getScoreboard().getTeam("Vanished");
            team.setPrefix("");
        }
*/


    @Override
    public void onEnable() {
        System.out.println("Minemen Core has been enabled!");
        getCommand("spawn").setExecutor(new spawn());
        getCommand("setspawn").setExecutor(new setspawn());
        getCommand("kaboom").setExecutor(new kaboom());
        getCommand("list").setExecutor(new online());
        getCommand("selector").setExecutor(new GUI());
        getCommand("settings").setExecutor(new lobbysettings());
        getCommand("bm").setExecutor(new buildmodecmd());
        getCommand("moveitems").setExecutor(new move());
        getCommand("gmc").setExecutor(new gamemode());
        getCommand("gms").setExecutor(new gamemode());
        getCommand("fly").setExecutor(new gamemode());
        getCommand("gm3").setExecutor(new gamemode());
        getCommand("pvp").setExecutor(new pvp());
        getCommand("killmobs").setExecutor(new killmobs());
        getCommand("toggleitems").setExecutor(new toggleitems());
        getCommand("bc").setExecutor(new broadcast());
        getCommand("heal").setExecutor(new heal());
        getCommand("tp").setExecutor(new tp());
        getCommand("v").setExecutor(new vanish());
        getCommand("tphere").setExecutor(new tp());
        getCommand("rank").setExecutor(new rank());
        getConfig().options().copyDefaults(true);
        saveConfig();
        loadConfigManager();
        getCommand("kills").setExecutor(new kills());
        getCommand("togglescoreboard").setExecutor(new scoreboardtoggle());
        getServer().getPluginManager().registerEvents(new pvp(), this);
        getServer().getPluginManager().registerEvents(new buildmode(), this);
        getServer().getPluginManager().registerEvents(new enderpearl(), this);
        getServer().getPluginManager().registerEvents(new playerchat(), this);
        getServer().getPluginManager().registerEvents(new moveevent(), this);
        getServer().getPluginManager().registerEvents(new itemsonjoin(), this);
        getServer().getPluginManager().registerEvents(new GUI(), this);
        getServer().getPluginManager().registerEvents(new lobbysettings(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new kill(), this);
        getServer().getPluginManager().registerEvents(new pvpmodeevent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoinConfig(), this);
        getServer().getPluginManager().registerEvents(new ActionBarAPI(), this);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                refreshOnline("ALL");
            }
        }, 50L, 50L);
        for (Player p : Bukkit.getOnlinePlayers()) {
            World world = Bukkit.getServer().getWorld(getConfig().getString("Spawn." + ".World"));
            float yaw = getConfig().getInt("Spawn." + ".Yaw");
            float pitch = getConfig().getInt("Spawn." + ".Pitch");
            float x = getConfig().getInt("Spawn." + ".X");
            float y = getConfig().getInt("Spawn." + ".Y");
            float z = getConfig().getInt("Spawn." + ".Z");

            Location loc = new Location(world, x, y, z, yaw, pitch);
            p.teleport(loc);

            ItemStack compass = new ItemStack(Material.COMPASS);
            ItemMeta meta = compass.getItemMeta();

            meta.setDisplayName(ChatColor.GOLD + "Server Selector");
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY + "Use this to select a server!");
            meta.setLore(lore);
            compass.setItemMeta(meta);

            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
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
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    try {
                        int players = Bukkit.getOnlinePlayers().size();
                        int onlinePlayers = getOnlinePlayers("ALL");
                        TitleAPI.sendTabTitle(p, ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH +
                                "[-" + ChatColor.RESET + " " + ChatColor.YELLOW + ChatColor.BOLD + "Palific" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH
                                + "-]" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------\n", "\n" + ChatColor.GREEN + ChatColor.BOLD + "* " + ChatColor.YELLOW + ChatColor.BOLD + onlinePlayers + " PLAYERS ONLINE" + ChatColor.GREEN + "" + ChatColor.BOLD + " *" + "\n" +
                                ChatColor.GRAY + "      (" + players + " in your server) " + ChatColor.YELLOW + "\n\nwww.palific.net");
                    } catch (Exception e) {

                    }
                    if (pvp.contains(p)) {
                        ActionBarAPI.sendActionBar(p, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "» " + ChatColor.GOLD + ChatColor.BOLD + "You are currently in pvp mode!" + ChatColor.DARK_AQUA + ChatColor.BOLD + " «");
                    } else if ((getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".VanishEnabled"))) {
                        ActionBarAPI.sendActionBar(p, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "» " + ChatColor.GOLD + ChatColor.BOLD + "You are currently vanished!" + ChatColor.DARK_AQUA + ChatColor.BOLD + " «");
                    } else {
                        ActionBarAPI.sendActionBar(p, "");
                    }
                }
            }, 5L, 5L);

            if ((getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".SBEnabled"))) {
                SimpleScoreboard scoreboard = new SimpleScoreboard("§e§lPalific");
                scoreboard.send(p);
                taskId2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    public void run() {
                        try {
                            String rank = getConfigManager().getPlayers().getString(p.getUniqueId() + ".Rank");
                            UUID id = p.getUniqueId();
                            Integer num = kills.get(id);
                            int onlinePlayers = getOnlinePlayers("ALL");
                            if (kills.get(id) == null) {
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
                    }
                }, 5L, 5L);
            }
        }
    }


    public void loadConfigManager(){
        cfgm = new ConfigManager();
        cfgm.setup();
        cfgm.savePlayers();
        cfgm.reloadPlayers();

    }

    public ConfigManager getConfigManager() {
        return cfgm;
    }

}