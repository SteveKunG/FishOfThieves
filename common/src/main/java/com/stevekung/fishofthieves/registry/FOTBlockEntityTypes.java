package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.blockentity.FOTHangingSignBlockEntity;
import com.stevekung.fishofthieves.blockentity.FOTSignBlockEntity;
import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FOTBlockEntityTypes
{
    //@formatter:off
    public static final BlockEntityType<FishPlaqueBlockEntity> FISH_PLAQUE = FOTPlatform.createBlockEntityType(FishPlaqueBlockEntity::new,
            // Wooden
            FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE,
            FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE,
            FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE,
            FOTBlocks.COCONUT_FISH_PLAQUE,
            // Iron Frame
            FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE,
            FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE,
            FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE,
            FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE,
            // Golden Frame
            FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE,
            FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE,
            FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE,
            FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE,
            // Gilded
            FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE,
            FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE,
            FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE,
            FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.GILDED_COCONUT_FISH_PLAQUE);
    public static final BlockEntityType<FOTSignBlockEntity> SIGN = FOTPlatform.createBlockEntityType(FOTSignBlockEntity::new,
            FOTBlocks.COCONUT_SIGN, FOTBlocks.COCONUT_WALL_SIGN);
    public static final BlockEntityType<FOTHangingSignBlockEntity> HANGING_SIGN = FOTPlatform.createBlockEntityType(FOTHangingSignBlockEntity::new,
            FOTBlocks.COCONUT_HANGING_SIGN, FOTBlocks.COCONUT_WALL_HANGING_SIGN);
    //@formatter:on

    public static void init()
    {
        FOTPlatform.registerBlockEntity("fish_plaque", FISH_PLAQUE);
        FOTPlatform.registerBlockEntity("sign", SIGN);
        FOTPlatform.registerBlockEntity("hanging_sign", HANGING_SIGN);
    }
}