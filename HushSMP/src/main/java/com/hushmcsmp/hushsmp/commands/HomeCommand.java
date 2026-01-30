package com.hushmcsmp.hushsmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;
import java.util.Map;

public class HomeCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public HomeCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("sethome").setExecutor(new SetHomeExecutor(plugin));
        plugin.getCommand("home").setExecutor(this);
        plugin.getCommand("delhome").setExecutor(new DelHomeExecutor(plugin));
        plugin.getCommand("homes").setExecutor(new HomesListExecutor(plugin));
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§cUsage: /home <name>");
            return true;
        }
        
        String homeName = args[0];
        var location = plugin.getHomeManager().getHome(player.getUniqueId(), homeName);
        
        if (location == null) {
            player.sendMessage("§cHome '" + homeName + "' not found!");
            return true;
        }
        
        player.teleport(location);
        player.sendMessage("§a✓ Teleported to " + homeName + "!");
        return true;
    }
    
    public static class SetHomeExecutor implements CommandExecutor {
        private HushSMP plugin;
        
        public SetHomeExecutor(HushSMP plugin) {
            this.plugin = plugin;
        }
        
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            
            Player player = (Player) sender;
            
            String homeName = args.length > 0 ? args[0] : "home";
            
            if (plugin.getHomeManager().setHome(player.getUniqueId(), homeName, player.getLocation())) {
                player.sendMessage("§a✓ Home '" + homeName + "' set!");
            } else {
                player.sendMessage("§cYou have reached the home limit (2). Upgrade to Donut Plus for more!");
            }
            return true;
        }
    }
    
    public static class DelHomeExecutor implements CommandExecutor {
        private HushSMP plugin;
        
        public DelHomeExecutor(HushSMP plugin) {
            this.plugin = plugin;
        }
        
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            
            Player player = (Player) sender;
            
            if (args.length == 0) {
                player.sendMessage("§cUsage: /delhome <name>");
                return true;
            }
            
            String homeName = args[0];
            plugin.getHomeManager().deleteHome(player.getUniqueId(), homeName);
            player.sendMessage("§a✓ Home '" + homeName + "' deleted!");
            return true;
        }
    }
    
    public static class HomesListExecutor implements CommandExecutor {
        private HushSMP plugin;
        
        public HomesListExecutor(HushSMP plugin) {
            this.plugin = plugin;
        }
        
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            
            Player player = (Player) sender;
            Map<String, org.bukkit.Location> homes = plugin.getHomeManager().getHomes(player.getUniqueId());
            
            if (homes.isEmpty()) {
                player.sendMessage("§cYou have no homes set!");
                return true;
            }
            
            player.sendMessage("§b=== Your Homes ===");
            for (String homeName : homes.keySet()) {
                player.sendMessage("§b  /home " + homeName);
            }
            return true;
        }
    }
}
