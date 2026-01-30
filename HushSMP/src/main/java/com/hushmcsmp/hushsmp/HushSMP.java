package com.hushmcsmp.hushsmp;

import org.bukkit.plugin.java.JavaPlugin;
import com.hushmcsmp.hushsmp.managers.*;
import com.hushmcsmp.hushsmp.commands.*;
import com.hushmcsmp.hushsmp.listeners.*;

public class HushSMP extends JavaPlugin {
    
    private static HushSMP instance;
    private EconomyManager economyManager;
    private HomeManager homeManager;
    private TeamManager teamManager;
    private StoreManager storeManager;
    private AuctionManager auctionManager;
    private DuelManager duelManager;
    private ShardManager shardManager;
    private RTPManager rtpManager;
    private AFKManager afkManager;
    
    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("§b=== HushSMP Plugin Loading ===§r");
        
        // Initialize managers
        economyManager = new EconomyManager(this);
        homeManager = new HomeManager(this);
        teamManager = new TeamManager(this);
        storeManager = new StoreManager(this);
        auctionManager = new AuctionManager(this);
        duelManager = new DuelManager(this);
        shardManager = new ShardManager(this);
        rtpManager = new RTPManager(this);
        afkManager = new AFKManager(this);
        
        // Register commands
        new StoreCommand(this);
        new ShopCommand(this);
        new AuctionCommand(this);
        new SellCommand(this);
        new RTPCommand(this);
        new HomeCommand(this);
        new TeamCommand(this);
        new DuelCommand(this);
        new ShardCommand(this);
        new MoneyCommand(this);
        new AFKCommand(this);
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PvPListener(this), this);
        getServer().getPluginManager().registerEvents(new DuelListener(this), this);
        getServer().getPluginManager().registerEvents(new AFKListener(this), this);
        
        getLogger().info("§aHushSMP Plugin loaded successfully!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("§cHushSMP Plugin disabled!");
        // Save all data
        if (homeManager != null) homeManager.saveHomes();
        if (teamManager != null) teamManager.saveTeams();
        if (economyManager != null) economyManager.saveMoney();
        if (auctionManager != null) auctionManager.saveAuctions();
        if (shardManager != null) shardManager.saveShards();
    }
    
    public static HushSMP getInstance() {
        return instance;
    }
    
    public EconomyManager getEconomyManager() {
        return economyManager;
    }
    
    public HomeManager getHomeManager() {
        return homeManager;
    }
    
    public TeamManager getTeamManager() {
        return teamManager;
    }
    
    public StoreManager getStoreManager() {
        return storeManager;
    }
    
    public AuctionManager getAuctionManager() {
        return auctionManager;
    }
    
    public DuelManager getDuelManager() {
        return duelManager;
    }
    
    public ShardManager getShardManager() {
        return shardManager;
    }
    
    public RTPManager getRTPManager() {
        return rtpManager;
    }
    
    public AFKManager getAFKManager() {
        return afkManager;
    }
}
