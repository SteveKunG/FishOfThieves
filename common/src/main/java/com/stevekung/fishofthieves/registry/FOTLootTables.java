package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public interface FOTLootTables
{
    interface Blocks
    {
        ResourceKey<LootTable> EARTHWORMS_DROPS = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("blocks/earthworms_drops"));
        ResourceKey<LootTable> GRUBS_DROPS = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("blocks/grubs_drops"));
        ResourceKey<LootTable> LEECHES_DROPS = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("blocks/leeches_drops"));
    }

    interface Entities
    {
        ResourceKey<LootTable> FISH_BONE_DROP = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("entities/fish_bone_drop"));
    }

    interface Chests
    {
        ResourceKey<LootTable> SEAPOST_BARREL_SUPPLY = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("chests/seapost_barrel_supply"));
        ResourceKey<LootTable> SEAPOST_BARREL_COMBAT = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("chests/seapost_barrel_combat"));
        ResourceKey<LootTable> SEAPOST_BARREL_FIREWORK = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("chests/seapost_barrel_firework"));
    }

    interface Advancements
    {
        ResourceKey<LootTable> FISH_COLLECTORS = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("rewards/fish_collectors"));
        ResourceKey<LootTable> MASTER_FISH_COLLECTORS = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("rewards/master_fish_collectors"));
        ResourceKey<LootTable> LEGENDARY_FISH_COLLECTORS = ResourceKey.create(Registries.LOOT_TABLE, FishOfThieves.id("rewards/legendary_fish_collectors"));
    }
}