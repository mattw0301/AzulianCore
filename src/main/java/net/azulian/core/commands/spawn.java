package net.azulian.core.commands;

        import net.azulian.core.main;
        import org.bukkit.*;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandExecutor;
        import org.bukkit.command.CommandSender;
        import org.bukkit.entity.Player;

public class spawn implements CommandExecutor{
    main plugin = main.getPlugin(main.class);
    public String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "Minemen " + ChatColor.DARK_AQUA + "»";
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;


        World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("Spawn." + ".World"));
        float yaw = plugin.getConfig().getInt("Spawn." + ".Yaw");
        float pitch = plugin.getConfig().getInt("Spawn." +  ".Pitch");
        float x = plugin.getConfig().getInt("Spawn." + ".X");
        float y = plugin.getConfig().getInt("Spawn."  + ".Y");
        float z = plugin.getConfig().getInt("Spawn."  + ".Z");

        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 29);
        player.sendMessage(prefix + ChatColor.WHITE + " You have teleported to spawn!");

        return true;
    }


}
