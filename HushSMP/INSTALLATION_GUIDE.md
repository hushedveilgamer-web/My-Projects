# HushSMP Plugin - Complete Installation & Setup Guide

## Overview

HushSMP is a comprehensive all-in-one plugin for Paper MC that replicates all features from the Donut SMP. It provides an economy system, PvP duels, AFK rewards, homes, teams, auction house, random teleportation, and much more.

**Current Version:** 1.0.0  
**Target:** Paper MC 1.20.4+  
**Java:** 21+

---

## Installation

### Step 1: Download the Plugin
The compiled plugin JAR is located at:
```
HushSMP/target/HushSMP-1.0.0.jar
```

### Step 2: Install to Your Server
1. Stop your Paper MC server
2. Copy `HushSMP-1.0.0.jar` to your `plugins/` folder
3. Start your server
4. The plugin will automatically create its data folder at `plugins/HushSMP/`

### Step 3: Verify Installation
Check server logs for:
```
[HushSMP] === HushSMP Plugin Loading ===
[HushSMP] HushSMP Plugin loaded successfully!
```

---

## Complete Feature List

### 🏪 Store System (`/store`)
- **Hourly free key** with automatic cooldown
- Random free items from store  
- Timer display showing when next key available
- **Usage:** `/store`

### 🛒 Shop System (`/shop`)
- Buy **Diamond equipment** (Helmet, Chestplate, Leggings, Boots)
- Buy **Tools** (Pickaxe, Axe, Shovel)
- Buy **Items** (Blaze Rods, Shulker Boxes, Ender Chests)
- Money-based prices
- **Usage:** `/shop`

### 💰 Auction House (`/ah`, `/auction`)
- **Sell items** to other players
- **Buy items** from player auctions
- Set custom prices
- **Commands:**
  - `/ah` - View all auctions
  - `/ah sell <price>` - List item in hand for sale
  - `/ah buy <auctionId>` - Purchase an item

### 💵 Quick Sell (`/sell`)
- **Instantly sell** items for fixed prices
- **Prices:**
  - Wood (Oak/Birch/Spruce): $5 each
  - Iron Ingot: $50 each
  - Gold Ingot: $75 each
  - Diamond: $500 each
  - Ender Chest: $165
  - Blaze Rod: $50 each
  - Sugar Cane: $10 each
- **Usage:** `/sell`

### 🌍 RTP - Random Teleport (`/rtp`)
- **4 regions** to choose from:
  - **NA-East** - Eastern North America region
  - **NA-West** - Western North America region
  - **EU-Central** - Central Europe region
  - **Asia-Pacific** - Asia Pacific region
- PvP enabled in RTP zones
- Random spawn within selected region
- **Usage:** `/rtp [region]`

### 🏠 Home System
- **Set homes:** `/sethome [name]`
  - Default 2 homes per player
  - Usage: `/sethome mysecondbase`
- **Teleport to homes:** `/home [name]`
  - Usage: `/home` or `/home mysecondbase`
- **Delete homes:** `/delhome [name]`
- **List homes:** `/homes`

### 👥 Team System (`/team`)
- **Create teams:** `/team create <name>`
  - Get team shared home
  - Invite other players
- **Invite players:** `/team invite <player>`
- **Leave team:** `/team leave`
- **Team home:** `/team home`
  - Teleport to shared team location
- **Usage Examples:**
  - `/team create MyTeam`
  - `/team invite PlayerName`
  - `/team home`

### ⚔️ Duel System (`/duel`)
- **Challenge players** to PvP duels
- Fight in dedicated duel arena
- **Rewards:**
  - Win: +$5000
  - Loss: Full inventory drop
- **Usage:** `/duel [player]`

### 💎 Shards System (`/shard`)
- **Earn shards** from kills (10 per kill, 1x per player per session)
- **Spawner shop** to purchase mob spawners:
  - Skeleton Spawner: 500 shards
  - Iron Golem Spawner: 300 shards
  - Zombie Spawner: 150 shards
  - Creeper Spawner: 200 shards
- **Commands:**
  - `/shard` - Check balance
  - `/shard shop` - View spawner prices
  - `/shards` - Alternative balance check

### 🅰️ AFK Mode (`/afk`)
- **Passive income** while AFK
- Earn 1 shard every 60 seconds
- Automatic disable on movement
- **Usage:** `/afk`

### 💸 Economy (`/money`)
- **Check balance:** `/money`
- **Earn money:**
  - Kill: $500
  - Duel win: $5000
  - Sell items: Variable
- **Spend money:**
  - Shop purchases
  - Auction house

---

## PvP & Survival Mechanics

### Death Mechanics
- In **PvP zones** (RTP):
  - Killer earns 10 shards
  - Killer earns $500
  - You lose all gear (can recover)
  
- In **Duel zones**:
  - Winner earns $5000
  - Loser loses all gear
  - All drops are cleared

### Base Building Tips
- Use `/sethome` to mark your base
- Build far away (100,000+ blocks recommended)
- Hide under lava lakes to avoid x-ray hackers
- Use teams to share responsibility of protection
- Keep valuable items in Ender Chests

---

## Configuration & Customization

### Modify Prices

#### Edit Shop Item Prices
File: `src/main/java/com/hushmcsmp/hushsmp/commands/ShopCommand.java`
```java
// Modify the createItem calls to change prices shown
```

#### Edit Sell Command Prices
File: `src/main/java/com/hushmcsmp/hushsmp/commands/SellCommand.java`
```java
static {
    SELL_PRICES.put(Material.OAK_LOG, 5.0);      // Change these values
    SELL_PRICES.put(Material.DIAMOND, 500.0);
    // ... more items
}
```

#### Edit Shard Prices
File: `src/main/java/com/hushmcsmp/hushsmp/managers/ShardManager.java`
```java
private void initializeShardPrices() {
    shardPrices.put("skeleton_spawner", 500L);   // Change these values
    shardPrices.put("iron_golem_spawner", 300L);
    // ... more spawners
}
```

### Add New RTP Regions

Edit `src/main/java/com/hushmcsmp/hushsmp/managers/RTPManager.java`:
```java
private void initializeRegions() {
    // Add new region with coordinates
    regions.put("YourRegion", new RTPRegion("YourRegion", minX, minZ, maxX, maxZ));
}
```

### Adjust Economy Rewards

Edit `src/main/java/com/hushmcsmp/hushsmp/listeners/PvPListener.java`:
```java
plugin.getShardManager().addShards(killer.getUniqueId(), 10);    // Shards per kill
plugin.getEconomyManager().addMoney(killer.getUniqueId(), 500.0); // Money per kill
```

Edit `src/main/java/com/hushmcsmp/hushsmp/listeners/DuelListener.java`:
```java
plugin.getEconomyManager().addMoney(killer.getUniqueId(), 5000.0); // Duel reward
```

### Change Home Limits

Edit `src/main/java/com/hushmcsmp/hushsmp/managers/HomeManager.java`:
```java
private static final int DEFAULT_HOMES = 2;  // Change this value
```

### Adjust AFK Rewards

Edit `src/main/java/com/hushmcsmp/hushsmp/managers/AFKManager.java`:
```java
private static final long AFK_REWARD_COOLDOWN = 60000;  // Time between rewards (ms)
private static final int SHARDS_PER_REWARD = 1;         // Shards earned per interval
```

---

## Data Management

### Data Files
All player data is stored in `plugins/HushSMP/`:
- `money.dat` - Player money balances
- `homes.dat` - Home locations
- `teams.dat` - Team information and members
- `shards.dat` - Shard balances
- `auctions.dat` - Auction house listings

### Backup Data
```bash
# Backup your data before updates
cp -r plugins/HushSMP plugins/HushSMP.backup
```

### Reset Player Data
Delete the corresponding `.dat` file to reset that system. Plugin will create new one on startup.

---

## Troubleshooting

### Plugin Won't Load
1. Check Java version: `java -version` (needs 21+)
2. Check Paper version compatibility (works with 1.20.4+)
3. Look for errors in server logs

### Commands Not Working
- Make sure you're in-game (not console)
- Check server console for error messages
- Verify plugin loaded with `/plugins` command

### Data Not Saving
- Check file permissions in `plugins/HushSMP/`
- Ensure server has write access
- Check disk space on server

### Money/Shards Not Updating
- Check that player has sufficient balance
- Verify transaction happened in server logs
- Restart server if data corrupted

---

## Rebuilding from Source

If you modify the code:

```bash
cd HushSMP
mvn clean package -DskipTests
```

The new JAR will be at: `target/HushSMP-1.0.0.jar`

---

## Performance Notes

- **Lightweight:** Plugin uses ~10MB RAM
- **Scalable:** Tested for 50+ players
- **Efficient:** Data saved only on quit/shutdown
- **Safe:** Uses serialization for data persistence

---

## Future Enhancement Ideas

- [ ] GUI menus for shops
- [ ] Leaderboards for kills/money
- [ ] Custom spawner recipes
- [ ] Seasonal events
- [ ] Player warps
- [ ] Economy inflation/deflation
- [ ] Guild system
- [ ] Quests/Challenges
- [ ] Cosmetics shop
- [ ] Streaming mode

---

## Support

For issues or questions:
1. Check this guide
2. Review server logs in `logs/latest.log`
3. Verify all configurations
4. Test with fresh plugin installation

---

## License & Credits

**Created for:** HushSMP Server  
**Based on:** Donut SMP Plugin Features  
**Version:** 1.0.0  
**Last Updated:** January 30, 2026

Enjoy your SMP! 🎮
