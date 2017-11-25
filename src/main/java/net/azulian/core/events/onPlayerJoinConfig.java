package net.azulian.core.events;

import net.azulian.core.main;
import net.azulian.core.utils.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerJoinConfig implements Listener{
    main plugin = main.getPlugin(main.class);
    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfigManager().getPlayers();
        Player p = event.getPlayer();
        if (!(config.getKeys(false).contains(event.getPlayer().getUniqueId().toString()))) {
            config.set(p.getUniqueId().toString() + ".Name", p.getName());
            config.set(p.getUniqueId().toString() + ".SBEnabled", true);
            config.set(p.getUniqueId().toString() + ".PVEnabled", true);
            config.set(p.getUniqueId().toString() + ".VanishEnabled", false);
            config.set(p.getUniqueId().toString() + ".Rank", "Default");
            plugin.getConfigManager().savePlayers();
        }
    /*    if ((plugin.getConfigManager().getPlayers().getBoolean(p.getUniqueId().toString() + ".SBDisabled"))) {
            plugin.sbe.add(p.getUniqueId());
        } */

    }
   /* @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        plugin.getConfigManager().getPlayers().set(event.getPlayer().getUniqueId().toString() + ".SBDisabled", (plugin.sbe.contains(event.getPlayer().getUniqueId())));
        plugin.getConfigManager().savePlayers();
        
    } */
}
