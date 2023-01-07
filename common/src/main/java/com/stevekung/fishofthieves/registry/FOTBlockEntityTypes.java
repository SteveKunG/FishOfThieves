package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FOTBlockEntityTypes
{
    public static final BlockEntityType<FishPlaqueBlockEntity> FISH_PLAQUE = FOTPlatform.createBlockEntityType(FishPlaqueBlockEntity::new, FOTBlocks.FISH_PLAQUE);

    public static void init()
    {
        FOTPlatform.registerBlockEntity("fish_plaque", FISH_PLAQUE);
    }
}