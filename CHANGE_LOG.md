# Overview

- 1.21.11 Initial Release
- New Blocks/Items
- New Fish Shoal
- New Fishing Hook mechanic
- New Advancements
- Huge bug fixes

## Shoal

- Can be caught fish inside by using Fishing Rod
  - Fish inside shoal will randomly select and spawn an entity after when caught successfully
- Can be controlled by game rule "fishofthieves:shoal_spawning". Default is: true
- Spawn locations are River, Beach, Tropical Island, Coastline region and occasionally in the Ocean and Deep Ocean
  - In the frozen river or frozen ocean it can spawn under the ice
  - Has configs to control spawn weight for each region or biomes
- Include natural fish e.g. Splashtail, Pondie, Islehopper, Ancientscale, Plentifin, Wildsplash, and Stormfish (Appear only when thundering)
- Added "Shoal Hunter" advancement
  - Any players that participate by catch at least one fish will get advancement as well
- Disappeared after 3 days in game (Can be configured)
- Removing water or filled with solid block around the shoal will make it disappear instantly
- Explosion damage will destroy shoal

## Fishing Hook with Bait

- Worms can now be attached to the Fishing Hook when casting
  - Will consume when held in the offhand or keep it in the inventory
- Can be enable/disable via configuration
- Can be catch specific fish easily
  - Loot weight is increased by 100
    - Leeches: Ancientscale and Stormfish
    - Earthworms: Plentifin, Wildsplash and Wrecker
    - Grubs: Devilfish and Battlegill
- Increase Lure speed by 10%
- New Bait Preserve system which will save bait item data when attached to the fishing hook
  - In case of game closed unexpectedly, this will spawn right on the last fishing hook position when chunk is loaded

## Treasured Fish

- Can be found only after the Treasured Fish map is revealed which bought from the Fisherman Villager or inside the Seapost Barrel Supply
  - Treasured Fish map can be trade with Villager Fisherman Stage 3 and 4
- Added Treasured Fish
  - Has 10 total variants
  - Tier 1 Fish:
    - Dewdrop Pondie
    - Briny Islehopper
    - Blossom Ancientscale
    - Leopard Devilfish
    - Emerald Wrecker
  - Tier 2 Fish:
    - Obsidian Splashtail
    - Crimson Plentifin
    - Calico Wildsplash
    - Zest Battlegill
    - Starshine Stormfish
  - Cannot despawn after caught from the shoal
  - Slightly bigger than Trophy sized
- Added "Treasured Fish Hunter" advancement
  - Catch all 10 variants of Treasured Fish

## Blocks

- [1.21.5+] Added Pale Oak Fish Plaques
- [1.21.10+] Added Coconut Shelf
- Added Copper Frame Fish Plaques

## Items

- Added Great Mouth Pottery Sherd
- Added Spawn Egg for Treasured Fish
- Added FoT's Fish Bucket with Non-trophy and Trophy

## Bug fixes

- [1.21.1+] Fixed crash when Island Rainwater is triggered
- Huge bug fixes for Coconut Tree and its decorator
- Fixed growing coconut tree/banana tree in a narrow block
- Fixed mob pathfinding stuck in certain blocks
- Fixed cannot join world when using custom datapacks which modified entity loots
- Fixed Fish Bone block particle with correct texture size
- Fixed z-fighting for Gilded Fish Plaque
- Fixed rare case of fish models are sync their animation with entity in world and inside the Fish Plaque
- Fixed FoT's flowers are not attracted to bee and cannot feed to bee

## Misc

- [1.21.5+] Added Firefly particle and buzz sound to Tropical Island at night
- [1.21.5+] Added data fixers for Coconut Signs
- [1.21.5+] Added entity type tags support for FoT's Fish
- [1.21.5+] Mango Tree and Big Oak Tree now generate with leaf litters in Tropical Island
- [1.21.5+] Mango Tree now generate with leaf litters in Sparse Jungle
- [1.21.5+] Added Fallen Jungle Tree in Tropical Island
- Increase rate of filling water in cauldron with Coconut Fronds and Banana Leaves
- Island Rainwater advancement is now trigger only when in the Tropical Island biome
- Increased Coconut Trees generation around the coastline of Tropical Island
- Added sheep spawning in Tropical Island
- Fox can now interact with Pomegranate Plant and Tall Pomegranate Plant
- New algorithm to find structures within chunk radius while fishing Wrecker around the Ocean Shipwreck and Ocean Ruined Portal
- Rum Battlegill now follow living entity that has Nausea active
  - Added "Drunken Sailor" advancement
- [1.21.1+] Fishing Rum Battlegill now required Nausea active
- [1.21.1+] Fishing Raven Islehopper now required Blindness active with 1 in 3 chance
- Coconut fronds now has drop chance of 33%, 50% and 100%
  - Requires tool enchanted with Silk Touch or shears to make it always drop
- Better Coconut Fronds block state handling
- Added Fruits trade for Farmer Villager
- Fisherman Villager now trade with random FoT's Fish Bucket
- Fisherman Villager now accept any variant of fish instead of regular one
- Added Fish Bone in fishing junk loot
- Added compatibility with Aquaculture's Fishing Rod bait slot
  - Can put any type of worms inside the slot
- Disable fish breaching AI when already captured by bucket
- Remove unnecessary fish breaching AI ticking
- Better cullface for Fish Plaques
- Rewrite Fish Item drop with variant system
- Added displayTrophyBucketInCreativeTab configuration
- Minor continentalness parameter adjustment for Tropical Island biome
- Update Island Rainwater advancement description
- Minor change to Coconut Boat textures
- Balanced Fruits food point and saturation
- Balanced Fish Variant spawning conditions
  - Ancientscale and Plentifin are no longer spawn inside the Mineshaft or Stronghold
  - Bone Ancientscale and Plentifin are now spawn with 10% chance in a normal condition
  - Honey Islehopper is now required Beehive block in range of 12 blocks to spawn
  - Amethyst Islehopper is now required only Amethyst blocks in total of 12 blocks or more to spawn
  - Remove see sky condition from Coral Wildsplash and required 16 blocks in range of any coral blocks to spawn
  - [1.20.1] Sand Battlegill can be found in the Desert biome
- Wreckers are now swimming around their structures accurately
- Balanced food seed item return chance
  - Mango Pit now has 20% chance to return seed after consumed
  - Pomegranate Seed now has 60% chance to return seed after consumed
