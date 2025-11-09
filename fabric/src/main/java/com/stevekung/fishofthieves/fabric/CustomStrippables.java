package com.stevekung.fishofthieves.fabric;

import java.util.Map;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.world.level.block.Block;

public interface CustomStrippables
{
    Map<Block, Block> CUSTOM_STRIPPABLES = Map.of(
            FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG,
            FOTBlocks.MEDIUM_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD,
            FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD,
            FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG,
            FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_LOG,
            FOTBlocks.SMALL_TOP_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD
    );
}