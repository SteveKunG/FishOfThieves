package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.resources.ResourceLocation;

public interface FOTLootTables
{
    interface Blocks
    {
        ResourceLocation EARTHWORMS_DROPS = new ResourceLocation(FishOfThieves.MOD_ID, "blocks/earthworms_drops");
        ResourceLocation GRUBS_DROPS = new ResourceLocation(FishOfThieves.MOD_ID, "blocks/grubs_drops");
        ResourceLocation LEECHES_DROPS = new ResourceLocation(FishOfThieves.MOD_ID, "blocks/leeches_drops");
    }

    interface Entities
    {
        ResourceLocation FISH_BONE_DROP = new ResourceLocation(FishOfThieves.MOD_ID, "entities/fish_bone_drop");
    }

    interface Chests
    {
        ResourceLocation SEAPOST_BARREL_SUPPLY = new ResourceLocation(FishOfThieves.MOD_ID, "chests/seapost_barrel_supply");
        ResourceLocation SEAPOST_BARREL_COMBAT = new ResourceLocation(FishOfThieves.MOD_ID, "chests/seapost_barrel_combat");
        ResourceLocation SEAPOST_BARREL_FIREWORK = new ResourceLocation(FishOfThieves.MOD_ID, "chests/seapost_barrel_firework");
    }
}