# HushSMP Plugin - Quick Command Reference

## All Commands At A Glance

| Category | Command | Arguments | Description |
|----------|---------|-----------|-------------|
| **Store** | `/store` | None | Get free items with hourly keys |
| **Shop** | `/shop` | None | Buy equipment and items |
| **Auction** | `/ah` | [sell/buy] | View or manage auctions |
| | `/ah sell` | `<price>` | List item in hand for sale |
| | `/ah buy` | `<id>` | Purchase auction item |
| | `/auction` | [sell/buy] | Alias for `/ah` |
| **Sell** | `/sell` | None | Quick sell item in hand |
| **RTP** | `/rtp` | [region] | Random teleport to region |
| **Homes** | `/sethome` | [name] | Set home location |
| | `/home` | [name] | Teleport to home |
| | `/delhome` | `<name>` | Delete a home |
| | `/homes` | None | List all your homes |
| **Teams** | `/team create` | `<name>` | Create a team |
| | `/team invite` | `<player>` | Invite player to team |
| | `/team leave` | None | Leave your team |
| | `/team home` | None | Go to team home |
| **PvP** | `/duel` | `<player>` | Challenge player to duel |
| **Shards** | `/shard` | [shop/balance] | View shard options |
| | `/shards` | None | Check shard balance |
| **Economy** | `/money` | None | Check money balance |
| **AFK** | `/afk` | None | Toggle AFK mode |

---

## Detailed Usage Examples

### Store System
```
/store
→ Get free key (hourly cooldown)
→ Opens store GUI with free items
```

### Auction House
```
/ah                    # View all auctions
/ah sell 20000         # Sell item in hand for $20,000
/ah buy 0              # Buy item #0 from auction
```

### Random Teleport
```
/rtp                   # List available regions
/rtp NA-East          # TP to NA-East region
/rtp EU-Central       # TP to EU-Central region
```

### Homes
```
/sethome              # Set home named "home"
/sethome base2        # Set home named "base2"
/home                 # Go to "home"
/home base2           # Go to "base2"
/homes                # List: home, base2
/delhome base2        # Delete "base2" home
```

### Teams
```
/team create MyTeam         # Create team
/team invite PlayerName     # Invite player
/team home                  # Go to team home
/team leave                 # Leave team
```

### Duels
```
/duel Playername           # Challenge Playername
→ Playername gets duel request
→ Fight in duel arena
→ Winner gets $5000
→ Loser loses all gear
```

### Shards & Economy
```
/shard shop            # View spawner prices
/shards                # Check shard balance
/money                 # Check money balance
```

### AFK Mode
```
/afk                   # Enable AFK (earn 1 shard/60s)
→ Move to disable AFK automatically
```

---

## Getting Started Guide

### For New Players
1. **Join server** → `/store` → Get free key
2. **Get items** → `/shop` → Buy gear with money
3. **Set home** → `/sethome` → Safe base location
4. **Explore** → `/rtp` → Find good farming area
5. **Build base** → Gather wood, farm, craft
6. **Make money** → `/sell` items or fight other players
7. **Upgrade gear** → `/shop` for better equipment
8. **Join team** → `/team invite` to join someone's team

### Money Flow
```
Grinding (sell items) → $
Killing players → $500 + 10 shards
Winning duels → $5000
AFK farming → 1 shard/60s
```

### Base Building
```
1. Find location with /rtp
2. Set home with /sethome base
3. Hide from x-ray hackers
4. Build storage, farms, enchanting
5. Use team home for backup base
6. Protect with terrain (under lava lakes)
```

---

## Spawner Prices (Shards)

| Spawner | Cost |
|---------|------|
| Skeleton | 500 |
| Iron Golem | 300 |
| Creeper | 200 |
| Zombie | 150 |

---

## Economy Breakdown

### Income Sources
- **Wood selling:** $5 per log
- **Ore selling:** $50-500+ per item
- **Player kills:** $500 + 10 shards
- **Duel wins:** $5000
- **AFK mode:** 1 shard per 60 seconds

### Spending
- **Shop items:** $1000-$7000
- **Auction house:** Variable prices
- **Spawner shop:** 150-500 shards

---

## Tips & Tricks

✅ **Do:**
- Hide your base far away (100,000+ blocks)
- Use `/team` to share base with friends
- Build under lava lakes to avoid detection
- Say "GG" after duels (it's respectful!)
- Check `/ah` for cheap spawners
- AFK in safe zones for passive shards
- Use `/sethome` as backup location

❌ **Don't:**
- Build near spawn (too dangerous)
- Share base coordinates publicly
- PvP without good gear
- Leave valuable items on ground
- Trust random players too much
- Go AFK in dangerous zones
- Underpriced items on auction

---

## Server Regions

### Available RTP Regions
- **NA-East:** Eastern North America coordinates
- **NA-West:** Western North America coordinates  
- **EU-Central:** Central Europe coordinates
- **Asia-Pacific:** Asia-Pacific region coordinates

Each region has PvP enabled. Good for grinding but watch for players!

---

## Common Questions

**Q: How do I get more than 2 homes?**  
A: You need to be in a team. Create/join a team to get shared team home.

**Q: Can I reset my home?**  
A: Yes, just `/sethome` again at new location to overwrite.

**Q: What happens if I die in a duel?**  
A: You lose all items in your inventory. Only fight with gear you can afford to lose!

**Q: How do I make money fast?**  
A: Kill players for $500 or win duels for $5000. Selling ore is slow but steady.

**Q: Is PvP always on?**  
A: Yes, in RTP zones. In your base/home you're safe from combat.

**Q: How do shards work?**  
A: Earn 10 per kill (1x per player per session), spend them for spawners.

---

## Version Info
**HushSMP v1.0.0**  
**Paper MC 1.20.4+**  
**All features active and tested** ✓
