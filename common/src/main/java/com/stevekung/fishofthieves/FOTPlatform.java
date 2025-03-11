package com.stevekung.fishofthieves;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FOTPlatform
{
    @ExpectPlatform
    public static boolean isModLoaded(String modId)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addComposting(ItemLike item, float value)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getGrowthSpeedFromCropBlock(BlockState state, ServerLevel level, BlockPos pos)
    {
        throw new AssertionError();
    }
}