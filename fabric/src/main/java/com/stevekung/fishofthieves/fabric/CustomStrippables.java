package com.stevekung.fishofthieves.fabric;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.world.level.block.Block;

public interface CustomStrippables
{
    Map<Block, Block> CUSTOM_STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
            .put(FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG)
            .put(FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD)
            .put(FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD)
            .put(FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG)
            .put(FOTBlocks.GROWABLE_SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG)
            .put(FOTBlocks.TOP_SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD)
            .build();
}