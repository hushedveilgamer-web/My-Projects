package com.hushmcsmp.hushsmp.managers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.*;

public class AuctionManager {
    
    private Plugin plugin;
    private List<AuctionItem> auctions = new ArrayList<>();
    private File dataFile;
    
    public AuctionManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "auctions.dat");
        plugin.getDataFolder().mkdirs();
        loadAuctions();
    }
    
    public void addAuction(UUID seller, ItemStack item, double price) {
        auctions.add(new AuctionItem(seller, item, price));
    }
    
    public boolean buyAuction(UUID buyer, int auctionId, EconomyManager economy) {
        if (auctionId < 0 || auctionId >= auctions.size()) {
            return false;
        }
        
        AuctionItem auction = auctions.get(auctionId);
        if (economy.canAfford(buyer, auction.getPrice())) {
            economy.removeMoney(buyer, auction.getPrice());
            economy.addMoney(auction.getSeller(), auction.getPrice());
            auctions.remove(auctionId);
            return true;
        }
        return false;
    }
    
    public List<AuctionItem> getAuctions() {
        return new ArrayList<>(auctions);
    }
    
    public void saveAuctions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(auctions);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save auctions: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadAuctions() {
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                auctions = (List<AuctionItem>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                plugin.getLogger().warning("Failed to load auctions: " + e.getMessage());
                auctions = new ArrayList<>();
            }
        }
    }
    
    public static class AuctionItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private UUID seller;
        private ItemStack item;
        private double price;
        private long createdAt;
        
        public AuctionItem(UUID seller, ItemStack item, double price) {
            this.seller = seller;
            this.item = item;
            this.price = price;
            this.createdAt = System.currentTimeMillis();
        }
        
        public UUID getSeller() {
            return seller;
        }
        
        public ItemStack getItem() {
            return item;
        }
        
        public double getPrice() {
            return price;
        }
        
        public long getCreatedAt() {
            return createdAt;
        }
    }
}
