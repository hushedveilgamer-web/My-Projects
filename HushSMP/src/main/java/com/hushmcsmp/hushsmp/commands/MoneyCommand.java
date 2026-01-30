package com.hushmcsmp.hushsmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;

public class MoneyCommand implements CommandExecutor {
    
    private HushSMP plugin;
    
    public MoneyCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("money").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        double money = plugin.getEconomyManager().getMoney(player.getUniqueId());
        player.sendMessage("§bYour Money: §7$" + money);
        return true;
    }
}
