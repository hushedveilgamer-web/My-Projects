package com.hushmcsmp.hushsmp.listeners;

import com.hushmcsmp.hushsmp.HushSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RTPListener implements Listener {
    private final HushSMP plugin;

    public RTPListener(HushSMP plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        if (inv == null || !event.getView().getTitle().equalsIgnoreCase("§b§lRTP Menu")) return;
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;
        String region = null;
        if (clicked.getType() == Material.GRASS_BLOCK) region = "overworld";
        if (clicked.getType() == Material.NETHERRACK) region = "nether";
        if (clicked.getType() == Material.END_STONE) region = "end";
        if (region != null) {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            plugin.getRTPManager().teleportRandom(player, region);
        }
    }
}
