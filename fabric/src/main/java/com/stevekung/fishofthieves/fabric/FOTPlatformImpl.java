package com.stevekung.fishofthieves.fabric;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static void addComposting(ItemLike item, float value)
    {
        CompostingChanceRegistry.INSTANCE.add(item, value);
    }

    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        FlammableBlockRegistry.getDefaultInstance().add(block, encouragement, flammability);
    }

    public static float getGrowthSpeedFromCropBlock(BlockState state, ServerLevel level, BlockPos pos)
    {
        return CropBlock.getGrowthSpeed(state.getBlock(), level, pos);
    }
}