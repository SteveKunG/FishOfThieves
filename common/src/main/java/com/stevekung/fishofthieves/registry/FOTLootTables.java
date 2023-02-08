package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.resources.ResourceLocation;

public interface FOTLootTables
{
    interface Blocks
    {
        ResourceLocation EARTHWORMS_DROPS = FishOfThieves.res("blocks/earthworms_drops");
        ResourceLocation GRUBS_DROPS = FishOfThieves.res("blocks/grubs_drops");
        ResourceLocation LEECHES_DROPS = FishOfThieves.res("blocks/leeches_drops");
    }

    interface Entities
    {
        ResourceLocation FISH_BONE_DROP = FishOfThieves.res("entities/fish_bone_drop");
    }

    interface Chests
    {
        ResourceLocation SEAPOST_BARREL_SUPPLY = FishOfThieves.res("chests/seapost_barrel_supply");
        ResourceLocation SEAPOST_BARREL_COMBAT = FishOfThieves.res("chests/seapost_barrel_combat");
        ResourceLocation SEAPOST_BARREL_FIREWORK = FishOfThieves.res("chests/seapost_barrel_firework");
    }
}