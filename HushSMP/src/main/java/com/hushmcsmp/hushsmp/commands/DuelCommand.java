package com.hushmcsmp.hushsmp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;

public class DuelCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public DuelCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("duel").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§cUsage: /duel <player>");
            return true;
        }
        
        if (plugin.getDuelManager().isInDuel(player.getUniqueId())) {
            player.sendMessage("§cYou are already in a duel!");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cPlayer not found!");
            return true;
        }
        
        if (target.equals(player)) {
            player.sendMessage("§cYou cannot duel yourself!");
            return true;
        }
        
        plugin.getDuelManager().sendDuelRequest(player.getUniqueId(), target.getUniqueId());
        player.sendMessage("§bDuel request sent to " + target.getName());
        target.sendMessage("§b" + player.getName() + " sent you a duel request!");
        target.sendMessage("§bType /accept " + player.getName() + " to accept");
        
        return true;
    }
}
