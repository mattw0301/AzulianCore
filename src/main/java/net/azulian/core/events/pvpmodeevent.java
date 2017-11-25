package net.azulian.core.events;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.UUID;

public class pvpmodeevent implements Listener{
    main plugin = main.getPlugin(main.class);
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player) {
            Entity p = event.getDamager();
            if (plugin.pvp.contains(p)) {
            }
            else {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onHit2(EntityDamageEvent event){
        if (event.getEntity() instanceof Player) {
            Entity p = event.getEntity();
            if (plugin.pvp.contains(p)) {
            }
            else {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        event.setKeepInventory(true);
        Player p = event.getEntity();
        String killer = event.getEntity().getKiller().getName();
        event.setDeathMessage(plugin.prefix + " " + ChatColor.RED + p.getName() + ChatColor.WHITE + " has been killed by " + ChatColor.GOLD + killer + "!");
            }
            @EventHandler
            public void onRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
                World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("Spawn." + ".World"));
                float yaw = plugin.getConfig().getInt("Spawn." + ".Yaw");
                float pitch = plugin.getConfig().getInt("Spawn." + ".Pitch");
                float x = plugin.getConfig().getInt("Spawn." + ".X");
                float y = plugin.getConfig().getInt("Spawn." + ".Y");
                float z = plugin.getConfig().getInt("Spawn." + ".Z");

                Location loc = new Location(world, x, y, z, yaw, pitch);
                event.setRespawnLocation(loc);
            }


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {

        boolean rain = event.toWeatherState();
        if(rain)
            event.setCancelled(true);
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {

        boolean storm = event.toThunderState();
        if(storm)
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        plugin.kills.remove(id);
        if (plugin.pvp.contains(p)) {
            plugin.pvp.remove(p);
        }


    }




}
