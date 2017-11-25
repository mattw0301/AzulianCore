package net.azulian.core.events;

import net.azulian.core.main;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class kill implements Listener {

    main plugin = main.getPlugin(main.class);

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player p = event.getEntity().getKiller();
        p.setHealth(20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player killer=e.getEntity().getKiller();
        if (killer != null) {
            UUID id = killer.getUniqueId();
            int current = plugin.kills.remove(id);
            plugin.kills.put(id, current + 1);
            Integer num = plugin.kills.get(id);
        }
    }


}

