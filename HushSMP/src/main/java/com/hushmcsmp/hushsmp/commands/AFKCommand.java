package com.hushmcsmp.hushsmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;

public class AFKCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public AFKCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("afk").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        plugin.getAFKManager().toggleAFK(player.getUniqueId());
        
        if (plugin.getAFKManager().isAFK(player.getUniqueId())) {
            player.sendMessage("§bAFK mode enabled! You will earn 1 shard every 60 seconds.");
        } else {
            player.sendMessage("§bAFK mode disabled!");
        }
        
        return true;
    }
}
