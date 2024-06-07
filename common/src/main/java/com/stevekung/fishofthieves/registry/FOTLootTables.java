package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.resources.ResourceLocation;

public interface FOTLootTables
{
    interface Blocks
    {
        ResourceLocation EARTHWORMS_DROPS = FishOfThieves.id("blocks/earthworms_drops");
        ResourceLocation GRUBS_DROPS = FishOfThieves.id("blocks/grubs_drops");
        ResourceLocation LEECHES_DROPS = FishOfThieves.id("blocks/leeches_drops");
    }

    interface Entities
    {
        ResourceLocation FISH_BONE_DROP = FishOfThieves.id("entities/fish_bone_drop");

        ResourceLocation POLAR_BEAR = FishOfThieves.id("entities/polar_bear");
    }

    interface Chests
    {
        ResourceLocation SEAPOST_BARREL_SUPPLY = FishOfThieves.id("chests/seapost_barrel_supply");
        ResourceLocation SEAPOST_BARREL_COMBAT = FishOfThieves.id("chests/seapost_barrel_combat");
        ResourceLocation SEAPOST_BARREL_FIREWORK = FishOfThieves.id("chests/seapost_barrel_firework");

        ResourceLocation VILLAGE_FISHER = FishOfThieves.id("chests/village/village_fisher");
        ResourceLocation BURIED_TREASURE = FishOfThieves.id("chests/buried_treasure");
    }

    interface Advancements
    {
        ResourceLocation FISH_COLLECTORS = FishOfThieves.id("rewards/fish_collectors");
        ResourceLocation MASTER_FISH_COLLECTORS = FishOfThieves.id("rewards/master_fish_collectors");
        ResourceLocation LEGENDARY_FISH_COLLECTORS = FishOfThieves.id("rewards/legendary_fish_collectors");
    }

    interface Fishing
    {
        ResourceLocation FISHING_FISH = FishOfThieves.id("gameplay/fishing/fish");
    }

    interface Gift
    {
        ResourceLocation FISHERMAN_GIFT = FishOfThieves.id("gameplay/hero_of_the_village/fisherman_gift");
    }

    interface Archaeology
    {
        ResourceLocation OCEAN_RUINS = FishOfThieves.id("archaeology/ocean_ruins");
    }
}