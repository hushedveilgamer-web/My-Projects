# HushSMP Plugin

A complete all-in-one SMP plugin for Paper MC 1.21.1 based on Donut SMP features.

## Features

### Store System (/store)
- Get free keys every hour
- Hourly cooldown system
- Free items from store

### Shop System (/shop)
- Buy equipment (Diamond gear)
- Buy tools (Pickaxes, Axes, Shovels)
- Buy items (Blaze rods, Shulker boxes, Ender chests)
- Money-based economy

### Auction House (/ah, /auction)
- Sell items to other players
- Buy items from auctions
- `/ah sell <price>` - List item for sale
- `/ah buy <id>` - Purchase item

### Quick Sell (/sell)
- Instantly sell items for money
- Configurable prices per item type
- Wood, minerals, ores, etc.

### RTP - Random Teleport (/rtp)
- Multiple regions (NA-East, NA-West, EU-Central, Asia-Pacific)
- Random spawn in selected region
- PvP-enabled zones for fighting

### Home System
- `/sethome [name]` - Set home location
- `/home [name]` - Teleport to home
- `/delhome [name]` - Delete home
- `/homes` - List all homes
- Default 2 homes per player

### Team System (/team)
- `/team create <name>` - Create a team
- `/team invite <player>` - Invite players
- `/team leave` - Leave team
- `/team home` - Teleport to team home
- Team shared home location

### Duel System (/duel)
- Challenge other players
- Fight in duel arena
- Reward: $5000 for winning
- Clear drops on death
- Losses result in full inventory loss

### Shards System
- Earn 10 shards per kill
- `/shard [shop/balance]` - View shard options
- Shard shop prices:
  - Skeleton Spawner: 500 shards
  - Iron Golem Spawner: 300 shards
  - Zombie Spawner: 150 shards
  - Creeper Spawner: 200 shards

### AFK Mode (/afk)
- Toggle AFK mode
- Earn 1 shard every 60 seconds while AFK
- Automatically disables on movement

### Economy (/money)
- Check money balance
- Earn money from kills ($500)
- Earn money from duels ($5000)
- Spend money in shops

## Commands

| Command | Usage | Description |
|---------|-------|-------------|
| /store | /store | Open store with free items |
| /shop | /shop | Open shop to buy items |
| /ah | /ah [sell/buy] | Auction house |
| /sell | /sell | Quick sell items |
| /rtp | /rtp [region] | Random teleport |
| /sethome | /sethome [name] | Set home |
| /home | /home [name] | Teleport to home |
| /delhome | /delhome [name] | Delete home |
| /homes | /homes | List homes |
| /team | /team [create/invite/leave/home] | Team management |
| /duel | /duel [player] | Challenge to duel |
| /shard | /shard [shop/balance] | Shard shop |
| /money | /money | Check balance |
| /afk | /afk | Toggle AFK mode |

## Installation

1. Build with Maven: `mvn clean package`
2. Place JAR in plugins folder
3. Restart server
4. Plugin creates data folder automatically

## Data Storage

All player data is saved to:
- `money.dat` - Money balances
- `homes.dat` - Home locations
- `teams.dat` - Team information
- `shards.dat` - Shard balances
- `auctions.dat` - Auction house listings

Data is saved on player quit and server shutdown.

## Configuration

Edit prices in:
- `SellCommand.java` - SELL_PRICES map
- `ShardManager.java` - shardPrices map
- `DuelListener.java` - duel rewards

## Customization

### Add New Shop Items
Edit `ShopCommand.java` to add more items to the shop GUI.

### Add New RTP Regions
Edit `RTPManager.java` initializeRegions() to add regions.

### Adjust Prices
Modify the price constants in respective manager and command files.

## Requirements

- Paper MC 1.21.1+
- Java 21+

## Version

1.0.0

## Author

HushSMP Team
