package com.hushmcsmp.hushsmp.listeners;

import com.hushmcsmp.hushsmp.HushSMP;
import com.hushmcsmp.hushsmp.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {
    private final HushSMP plugin;
    private final EconomyManager economy;

    public ShopListener(HushSMP plugin) {
        this.plugin = plugin;
        this.economy = plugin.getEconomyManager();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§b§lSHOP")) {
            event.setCancelled(true);
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || clicked.getType() == Material.AIR) return;
            double price = getPriceFromLore(clicked);
            if (price <= 0) return;
            if (economy.canAfford(player.getUniqueId(), price)) {
                economy.removeMoney(player.getUniqueId(), price);
                ItemStack toGive = clicked.clone();
                toGive.setAmount(1);
                player.getInventory().addItem(toGive);
                player.sendMessage("§aPurchased " + clicked.getItemMeta().getDisplayName() + " for $" + price + "!");
                // Update scoreboard after purchase
                plugin.getScoreboardManager().updateScoreboard(player);
            } else {
                player.sendMessage("§cYou cannot afford this item!");
            }
        }
    }

    private double getPriceFromLore(ItemStack item) {
        if (item.getItemMeta() == null || item.getItemMeta().getLore() == null) return 0;
        for (String line : item.getItemMeta().getLore()) {
            if (line.contains("$")) {
                try {
                    return Double.parseDouble(line.replaceAll("[^0-9.]", ""));
                } catch (NumberFormatException ignored) {}
            }
        }
        return 0;
    }
}
