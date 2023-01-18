package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FOTBlockEntityTypes
{
    public static final BlockEntityType<FishPlaqueBlockEntity> FISH_PLAQUE = FOTPlatform.createBlockEntityType(FishPlaqueBlockEntity::new, FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);

    public static void init()
    {
        FOTPlatform.registerBlockEntity("fish_plaque", FISH_PLAQUE);
    }
}