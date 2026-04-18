package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class BlockTagsProvider extends FabricTagsProvider.BlockTagsProvider
{
    public BlockTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(BlockTags.MINEABLE_WITH_PICKAXE).add(FOTBlocks.FISH_BONE);
        this.builder(BlockTags.MINEABLE_WITH_AXE).forceAddTag(FOTTags.Blocks.FISH_PLAQUE)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTER_PLANTS)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTERS)
                .add(FOTBlocks.COCONUT_FRUIT, FOTBlocks.PINEAPPLE_CROP,
                        FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlocks.RIPE_PINEAPPLE_BLOCK,
                        FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlocks.PRISMARIZED_LOG, FOTBlocks.BUDDING_PRISMARIZED_LOG);
        this.builder(BlockTags.MINEABLE_WITH_HOE).add(FOTBlocks.MANGO_LEAVES);
        this.builder(BlockTags.OVERWORLD_NATURAL_LOGS).add(FOTBlocks.COCONUT_LOG, FOTBlocks.SMALL_COCONUT_LOG,
                FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG, FOTBlocks.SMALL_TOP_COCONUT_LOG,
                FOTBlocks.MEDIUM_COCONUT_LOG).forceAddTag(FOTTags.Blocks.BANANA_STEMS);
        this.builder(BlockTags.LOGS_THAT_BURN).forceAddTag(FOTTags.Blocks.COCONUT_LOGS).forceAddTag(FOTTags.Blocks.SMALL_COCONUT_LOGS).forceAddTag(FOTTags.Blocks.BANANA_STEMS);
        this.builder(BlockTags.LEAVES).add(FOTBlocks.COCONUT_FRONDS, FOTBlocks.BANANA_LEAVES, FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.VERTICAL_COCONUT_FRONDS,
                FOTBlocks.MANGO_LEAVES);
        this.builder(BlockTags.SMALL_FLOWERS).add(FOTBlocks.PINK_PLUMERIA, FOTBlocks.LIGHT_BLUE_PLUMERIA, FOTBlocks.WHITE_PLUMERIA);
        this.builder(BlockTags.FLOWER_POTS).add(FOTBlocks.POTTED_PINK_PLUMERIA, FOTBlocks.POTTED_MANGO_PIT, FOTBlocks.POTTED_MANGO_SAPLING, FOTBlocks.POTTED_BANANA_SHOOTS,
                FOTBlocks.POTTED_POMEGRANATE_PLANT, FOTBlocks.POTTED_POMEGRANATE_SAPLING, FOTBlocks.POTTED_TROPICAL_RED_FERN, FOTBlocks.POTTED_TROPICAL_MONSTERA,
                FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA);
        this.builder(BlockTags.PLANKS).add(FOTBlocks.COCONUT_PLANKS);
        this.builder(BlockTags.WOODEN_BUTTONS).add(FOTBlocks.COCONUT_BUTTON);
        this.builder(BlockTags.WOODEN_FENCES).add(FOTBlocks.COCONUT_FENCE);
        this.builder(BlockTags.FENCE_GATES).add(FOTBlocks.COCONUT_FENCE_GATE);
        this.builder(BlockTags.WOODEN_PRESSURE_PLATES).add(FOTBlocks.COCONUT_PRESSURE_PLATE);
        this.builder(BlockTags.WOODEN_SLABS).add(FOTBlocks.COCONUT_SLAB);
        this.builder(BlockTags.WOODEN_STAIRS).add(FOTBlocks.COCONUT_STAIRS);
        this.builder(BlockTags.STANDING_SIGNS).add(FOTBlocks.COCONUT_SIGN);
        this.builder(BlockTags.WALL_SIGNS).add(FOTBlocks.COCONUT_WALL_SIGN);
        this.builder(BlockTags.CEILING_HANGING_SIGNS).add(FOTBlocks.COCONUT_HANGING_SIGN);
        this.builder(BlockTags.WALL_HANGING_SIGNS).add(FOTBlocks.COCONUT_WALL_HANGING_SIGN);
        this.builder(BlockTags.WOODEN_TRAPDOORS).add(FOTBlocks.COCONUT_TRAPDOOR);
        this.builder(BlockTags.WOODEN_DOORS).add(FOTBlocks.COCONUT_DOOR);
        this.builder(BlockTags.SAPLINGS).add(FOTBlocks.COCONUT_SAPLING, FOTBlocks.BANANA_SHOOTS, FOTBlocks.MANGO_SAPLING);
        this.builder(BlockTags.ENCHANTMENT_POWER_TRANSMITTER).add(FOTBlocks.BANANA_BLOSSOM, FOTBlocks.TROPICAL_RED_FERN, FOTBlocks.TROPICAL_MONSTERA);
        this.builder(BlockTags.BEE_GROWABLES).add(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlocks.PINEAPPLE_CROP, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT, FOTBlocks.MANGO_PIT,
                FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT, FOTBlocks.POMEGRANATE_SAPLING);
        this.builder(BlockTags.FLOWERS).add(FOTBlocks.BANANA_BLOSSOM, FOTBlocks.BANANA_BLOSSOM_PLANT, FOTBlocks.PINEAPPLE_CROP, FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT);
        this.builder(BlockTags.MAINTAINS_FARMLAND).add(FOTBlocks.PINEAPPLE_CROP);
        this.builder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.VERTICAL_COCONUT_FRONDS,
                FOTBlocks.TROPICAL_MONSTERA, FOTBlocks.TROPICAL_RED_FERN);
        this.builder(BlockTags.REPLACEABLE_BY_TREES).add(FOTBlocks.COCONUT_FRUIT, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT);
        this.builder(BlockTags.BEE_ATTRACTIVE).add(FOTBlocks.PINK_PLUMERIA, FOTBlocks.WHITE_PLUMERIA, FOTBlocks.LIGHT_BLUE_PLUMERIA,
                FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT, FOTBlocks.PINEAPPLE_CROP, FOTBlocks.BANANA_BLOSSOM, FOTBlocks.BANANA_BLOSSOM_PLANT);

        var replaceableList = provider.lookupOrThrow(Registries.BLOCK)
                .filterElements(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(FishOfThieves.MOD_ID) && block.defaultBlockState().canBeReplaced())
                .listElementIds().toList();

        if (!replaceableList.isEmpty())
        {
            replaceableList.forEach(this.builder(BlockTags.REPLACEABLE)::add);
        }

        this.builder(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS).add(Blocks.MAGMA_BLOCK);
        this.builder(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON).forceAddTag(BlockTags.CORALS).forceAddTag(BlockTags.CORAL_BLOCKS).forceAddTag(BlockTags.WALL_CORALS);
        this.builder(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON).forceAddTag(BlockTags.CRYSTAL_SOUND_BLOCKS);
        this.builder(FOTTags.Blocks.EARTHWORMS_DROPS).forceAddTag(BlockTags.DIRT);
        this.builder(FOTTags.Blocks.GRUBS_DROPS).forceAddTag(BlockTags.SAND);
        this.builder(FOTTags.Blocks.LEECHES_DROPS).forceAddTag(BlockTags.SAND).add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
        this.builder(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST).add(Blocks.MOSS_BLOCK, Blocks.COARSE_DIRT, Blocks.MYCELIUM, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
        this.builder(FOTTags.Blocks.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE,
                FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE,
                FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.PALE_OAK_FISH_PLAQUE,
                FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_PALE_OAK_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.COPPER_FRAME_FISH_PLAQUE).add(FOTBlocks.COPPER_FRAME_OAK_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_SPRUCE_FISH_PLAQUE,
                FOTBlocks.COPPER_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_ACACIA_FISH_PLAQUE,
                FOTBlocks.COPPER_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_PALE_OAK_FISH_PLAQUE,
                FOTBlocks.COPPER_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_WARPED_FISH_PLAQUE,
                FOTBlocks.COPPER_FRAME_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE).add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE,
                FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE,
                FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_PALE_OAK_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE,
                FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.GILDED_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.FISH_REPELLENTS).add(Blocks.MAGMA_BLOCK, Blocks.BUBBLE_COLUMN);
        this.builder(FOTTags.Blocks.FISH_PLAQUE).forceAddTag(FOTTags.Blocks.WOODEN_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.COPPER_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.NON_FULL_LOGS).forceAddTag(FOTTags.Blocks.SMALL_COCONUT_LOGS).forceAddTag(FOTTags.Blocks.BANANA_STEMS)
                .add(FOTBlocks.MEDIUM_COCONUT_LOG, FOTBlocks.MEDIUM_COCONUT_WOOD,
                        FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG,
                        FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD);
        this.builder(FOTTags.Blocks.COCONUT_LOGS).add(FOTBlocks.COCONUT_LOG, FOTBlocks.MEDIUM_COCONUT_LOG,
                FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.SMALL_COCONUT_WOOD,
                FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD,
                FOTBlocks.COCONUT_WOOD, FOTBlocks.MEDIUM_COCONUT_WOOD,
                FOTBlocks.STRIPPED_COCONUT_LOG, FOTBlocks.STRIPPED_COCONUT_WOOD, FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG,
                FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD);
        this.builder(FOTTags.Blocks.SMALL_COCONUT_LOGS).add(FOTBlocks.SMALL_COCONUT_LOG, FOTBlocks.SMALL_COCONUT_WOOD,
                FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG, FOTBlocks.SMALL_TOP_COCONUT_LOG,
                FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD);
        this.builder(FOTTags.Blocks.COCONUT_GROWABLE_LOG_SPAWNABLE).forceAddTag(FOTTags.Blocks.SMALL_COCONUT_LOGS).add(FOTBlocks.MEDIUM_COCONUT_LOG);
        this.builder(FOTTags.Blocks.BANANA_STEMS).add(FOTBlocks.BANANA_STEM, FOTBlocks.BANANA_CLUSTER_GROWABLE_STEM);
        this.builder(FOTTags.Blocks.BANANA_CLUSTER_PLANTS).add(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT);
        this.builder(FOTTags.Blocks.BANANA_CLUSTERS).add(FOTBlocks.RIPE_BANANA_CLUSTER, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER,
                FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
        this.builder(FOTTags.Blocks.BANANA_SHOOTS_PLACEABLE_ON).forceAddTag(BlockTags.DIRT);
        this.builder(FOTTags.Blocks.MANGO_FRUITS).add(FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT);

        this.builder(FOTTags.Blocks.SERENE_SEASONS_YEAR_ROUND_CROPS)
                .add(FOTBlocks.BANANA_SHOOTS, FOTBlocks.BANANA_SHOOTS_PLANT,
                        FOTBlocks.GUARDIAN_FRUIT)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTERS)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTER_PLANTS);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_SPRING_CROPS)
                .add(FOTBlocks.PINEAPPLE_CROP, FOTBlocks.MANGO_SAPLING, FOTBlocks.MANGO_PIT, FOTBlocks.POMEGRANATE_SAPLING,
                        FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT)
                .forceAddTag(FOTTags.Blocks.MANGO_FRUITS);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_SUMMER_CROPS)
                .add(FOTBlocks.COCONUT_SAPLING, FOTBlocks.COCONUT_FRUIT,
                        FOTBlocks.PINEAPPLE_CROP, FOTBlocks.MANGO_SAPLING, FOTBlocks.MANGO_PIT, FOTBlocks.POMEGRANATE_SAPLING,
                        FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT)
                .forceAddTag(FOTTags.Blocks.MANGO_FRUITS);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_AUTUMN_CROPS)
                .add(FOTBlocks.POMEGRANATE_SAPLING, FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_UNBREAKABLE_INFERTILE_CROPS)
                .add(FOTBlocks.BANANA_SHOOTS, FOTBlocks.BANANA_SHOOTS_PLANT, FOTBlocks.POMEGRANATE_SAPLING,
                        FOTBlocks.COCONUT_SAPLING, FOTBlocks.PINEAPPLE_CROP, FOTBlocks.MANGO_SAPLING, FOTBlocks.MANGO_PIT,
                        FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT);
        this.builder(BlockTags.WOODEN_SHELVES).add(FOTBlocks.COCONUT_SHELF);
    }
}