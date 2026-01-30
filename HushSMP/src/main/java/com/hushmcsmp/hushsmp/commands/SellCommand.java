package com.hushmcsmp.hushsmp.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hushmcsmp.hushsmp.HushSMP;
import java.util.HashMap;
import java.util.Map;

public class SellCommand implements CommandExecutor {
    
    private HushSMP plugin;
    private static final Map<Material, Double> SELL_PRICES = new HashMap<>();
    
    static {
        SELL_PRICES.put(Material.OAK_LOG, 5.0);
        SELL_PRICES.put(Material.BIRCH_LOG, 5.0);
        SELL_PRICES.put(Material.SPRUCE_LOG, 5.0);
        SELL_PRICES.put(Material.IRON_INGOT, 50.0);
        SELL_PRICES.put(Material.GOLD_INGOT, 75.0);
        SELL_PRICES.put(Material.DIAMOND, 500.0);
        SELL_PRICES.put(Material.ENDER_CHEST, 165.0);
        SELL_PRICES.put(Material.BLAZE_ROD, 50.0);
        SELL_PRICES.put(Material.SUGAR_CANE, 10.0);
    }
    
    public SellCommand(HushSMP plugin) {
        this.plugin = plugin;
        plugin.getCommand("sell").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (player.getInventory().getItemInMainHand().getType().isAir()) {
            player.sendMessage("§cYou must hold an item to sell!");
            return true;
        }
        
        Material itemType = player.getInventory().getItemInMainHand().getType();
        Double price = SELL_PRICES.getOrDefault(itemType, 1.0);
        
        int amount = player.getInventory().getItemInMainHand().getAmount();
        double totalPrice = price * amount;
        
        plugin.getEconomyManager().addMoney(player.getUniqueId(), totalPrice);
        player.getInventory().getItemInMainHand().setAmount(0);
        
        player.sendMessage("§a✓ Sold " + amount + "x " + itemType.toString() + " for $" + totalPrice);
        return true;
    }
}
