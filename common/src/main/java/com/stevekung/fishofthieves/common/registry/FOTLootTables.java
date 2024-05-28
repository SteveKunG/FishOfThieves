package com.stevekung.fishofthieves.common.registry;

import com.stevekung.fishofthieves.common.FishOfThieves;
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
    }

    interface Chests
    {
        ResourceLocation SEAPOST_BARREL_SUPPLY = FishOfThieves.id("chests/seapost_barrel_supply");
        ResourceLocation SEAPOST_BARREL_COMBAT = FishOfThieves.id("chests/seapost_barrel_combat");
        ResourceLocation SEAPOST_BARREL_FIREWORK = FishOfThieves.id("chests/seapost_barrel_firework");
    }

    interface Advancements
    {
        ResourceLocation FISH_COLLECTORS = FishOfThieves.id("rewards/fish_collectors");
        ResourceLocation MASTER_FISH_COLLECTORS = FishOfThieves.id("rewards/master_fish_collectors");
        ResourceLocation LEGENDARY_FISH_COLLECTORS = FishOfThieves.id("rewards/legendary_fish_collectors");
    }
}