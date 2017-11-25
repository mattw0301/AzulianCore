package net.azulian.core.utils;

import net.azulian.core.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class ConfigManager implements Listener{
    main plugin = main.getPlugin(main.class);
    // Files & File Configs Here
    public FileConfiguration playerscfg;
    public File playersfile;
    // --------------------------

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playersfile = new File(plugin.getDataFolder(), "players.yml");

        if (!playersfile.exists()) {
            try {
                playersfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(ChatColor.RED + "Could not create the players.yml file");
            }
        }

        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
    }

    public FileConfiguration getPlayers() {
        return playerscfg;
    }

    public void savePlayers() {
        try {
            playerscfg.save(playersfile);

        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the players.yml file");
        }
    }

    public void reloadPlayers() {
        playerscfg = YamlConfiguration.loadConfiguration(playersfile);

    }
}
