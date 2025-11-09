package com.stevekung.fishofthieves.neoforge;

import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.world.level.block.Block;

public interface AxeStrippableDummy
{
    Map<Block, Block> STRIPPED_BLOCKS = Map.of(
            FOTBlocks.COCONUT_LOG, FOTBlocks.STRIPPED_COCONUT_LOG,
            FOTBlocks.COCONUT_WOOD, FOTBlocks.STRIPPED_COCONUT_WOOD
    );

    interface Small
    {
        Map<Block, Block> CUSTOM_STRIPPABLES = Map.of(
                FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD,
                FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG,
                FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG,
                FOTBlocks.SMALL_TOP_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD
        );
    }

    interface Medium
    {
        Map<Block, Block> CUSTOM_STRIPPABLES = Map.of(
                FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG,
                FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD
        );
    }
}