package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class BlockTagsProvider extends FabricTagProvider.BlockTagProvider
{
    public BlockTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(FOTBlocks.FISH_BONE);
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).forceAddTag(FOTTags.Blocks.FISH_PLAQUE).add(FOTBlocks.COCONUT_FRUIT);
        this.getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS).add(FOTBlocks.COCONUT_LOG, FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.MEDIUM_COCONUT_LOG);
        this.getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).forceAddTag(FOTTags.Blocks.COCONUT_LOGS);
        this.getOrCreateTagBuilder(BlockTags.LEAVES).add(FOTBlocks.COCONUT_FRONDS);
        this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(FOTBlocks.PINK_PLUMERIA);
        this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(FOTBlocks.POTTED_PINK_PLUMERIA);
        this.getOrCreateTagBuilder(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS).add(Blocks.MAGMA_BLOCK);
        this.getOrCreateTagBuilder(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON).forceAddTag(BlockTags.CORALS).forceAddTag(BlockTags.CORAL_BLOCKS).forceAddTag(BlockTags.WALL_CORALS);
        this.getOrCreateTagBuilder(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON).forceAddTag(BlockTags.CRYSTAL_SOUND_BLOCKS);
        this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROPS).forceAddTag(BlockTags.DIRT);
        this.getOrCreateTagBuilder(FOTTags.Blocks.GRUBS_DROPS).forceAddTag(BlockTags.SAND);
        this.getOrCreateTagBuilder(FOTTags.Blocks.LEECHES_DROPS).forceAddTag(BlockTags.SAND).add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
        this.getOrCreateTagBuilder(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST).add(Blocks.MOSS_BLOCK, Blocks.COARSE_DIRT, Blocks.MYCELIUM, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
        this.getOrCreateTagBuilder(FOTTags.Blocks.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE);
        this.getOrCreateTagBuilder(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);
        this.getOrCreateTagBuilder(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);
        this.getOrCreateTagBuilder(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE).add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE);
        this.getOrCreateTagBuilder(FOTTags.Blocks.FISH_REPELLENTS).add(Blocks.MAGMA_BLOCK, Blocks.BUBBLE_COLUMN);
        this.getOrCreateTagBuilder(FOTTags.Blocks.FISH_PLAQUE).forceAddTag(FOTTags.Blocks.WOODEN_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE);
        this.getOrCreateTagBuilder(FOTTags.Blocks.NON_FULL_LOGS).add(FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.MEDIUM_COCONUT_WOOD);
        this.getOrCreateTagBuilder(FOTTags.Blocks.COCONUT_LOGS).add(FOTBlocks.COCONUT_LOG, FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.COCONUT_WOOD, FOTBlocks.SMALL_COCONUT_WOOD, FOTBlocks.MEDIUM_COCONUT_WOOD);
    }
}