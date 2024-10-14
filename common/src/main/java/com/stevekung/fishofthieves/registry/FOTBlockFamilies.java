package com.stevekung.fishofthieves.registry;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

public class FOTBlockFamilies
{
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily COCONUT_PLANKS = familyBuilder(FOTBlocks.COCONUT_PLANKS)
            .button(FOTBlocks.COCONUT_BUTTON)
            .fence(FOTBlocks.COCONUT_FENCE)
            .fenceGate(FOTBlocks.COCONUT_FENCE_GATE)
            .pressurePlate(FOTBlocks.COCONUT_PRESSURE_PLATE)
            .sign(FOTBlocks.COCONUT_SIGN, FOTBlocks.COCONUT_WALL_SIGN)
            .slab(FOTBlocks.COCONUT_SLAB)
            .stairs(FOTBlocks.COCONUT_STAIRS)
            .door(FOTBlocks.COCONUT_DOOR)
            .trapdoor(FOTBlocks.COCONUT_TRAPDOOR)
            .recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();

    public static BlockFamily.Builder familyBuilder(Block baseBlock)
    {
        var builder = new BlockFamily.Builder(baseBlock);
        var blockFamily = MAP.put(baseBlock, builder.getFamily());

        if (blockFamily != null)
        {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        }
        else
        {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies()
    {
        return MAP.values().stream();
    }
}