package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.references.FOTBlockIds;
import com.stevekung.fishofthieves.references.FOTBlockItemIds;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;

public class BlockTagsProvider extends FabricTagsProvider.BlockTagsProvider
{
    public BlockTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(BlockTags.MINEABLE_WITH_PICKAXE).add(FOTBlockItemIds.FISH_BONE);
        this.builder(BlockTags.MINEABLE_WITH_AXE)
                .add(FOTBlockItemIds.COCONUT_FRUIT, FOTBlockItemIds.PINEAPPLE_CROP,
                        FOTBlockItemIds.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.RIPE_PINEAPPLE_BLOCK,
                        FOTBlockItemIds.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.PRISMARIZED_LOG, FOTBlockItemIds.BUDDING_PRISMARIZED_LOG)
                .forceAddTag(FOTTags.Blocks.FISH_PLAQUE)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTER_PLANTS)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTERS);
        this.builder(BlockTags.MINEABLE_WITH_HOE).add(FOTBlockItemIds.MANGO_LEAVES);
        this.builder(BlockTags.OVERWORLD_NATURAL_LOGS).add(FOTBlockItemIds.COCONUT_LOG, FOTBlockItemIds.SMALL_COCONUT_LOG,
                FOTBlockItemIds.COCONUT_FRUIT_GROWABLE_LOG, FOTBlockItemIds.SMALL_TOP_COCONUT_LOG,
                FOTBlockItemIds.MEDIUM_COCONUT_LOG).forceAddTag(FOTTags.Blocks.BANANA_STEMS);
        this.builder(BlockTags.LEAVES).add(FOTBlockItemIds.COCONUT_FRONDS, FOTBlockItemIds.BANANA_LEAVES, FOTBlockItemIds.VERTICAL_BANANA_LEAVES, FOTBlockItemIds.VERTICAL_COCONUT_FRONDS,
                FOTBlockItemIds.MANGO_LEAVES);
        this.builder(BlockTags.SMALL_FLOWERS).add(FOTBlockItemIds.PINK_PLUMERIA, FOTBlockItemIds.LIGHT_BLUE_PLUMERIA, FOTBlockItemIds.WHITE_PLUMERIA);
        this.builder(BlockTags.FLOWER_POTS).add(FOTBlockIds.POTTED_PINK_PLUMERIA, FOTBlockIds.POTTED_MANGO_PIT, FOTBlockIds.POTTED_MANGO_SAPLING, FOTBlockIds.POTTED_BANANA_SHOOTS,
                FOTBlockIds.POTTED_POMEGRANATE_PLANT, FOTBlockIds.POTTED_POMEGRANATE_SAPLING, FOTBlockIds.POTTED_TROPICAL_RED_FERN, FOTBlockIds.POTTED_TROPICAL_MONSTERA,
                FOTBlockIds.POTTED_LIGHT_BLUE_PLUMERIA, FOTBlockIds.POTTED_WHITE_PLUMERIA);
        this.builder(BlockTags.PLANKS).add(FOTBlockItemIds.COCONUT_PLANKS);
        this.builder(BlockTags.WOODEN_BUTTONS).add(FOTBlockItemIds.COCONUT_BUTTON);
        this.builder(BlockTags.WOODEN_FENCES).add(FOTBlockItemIds.COCONUT_FENCE);
        this.builder(BlockTags.FENCE_GATES).add(FOTBlockItemIds.COCONUT_FENCE_GATE);
        this.builder(BlockTags.WOODEN_PRESSURE_PLATES).add(FOTBlockItemIds.COCONUT_PRESSURE_PLATE);
        this.builder(BlockTags.WOODEN_SLABS).add(FOTBlockItemIds.COCONUT_SLAB);
        this.builder(BlockTags.WOODEN_STAIRS).add(FOTBlockItemIds.COCONUT_STAIRS);
        this.builder(BlockTags.STANDING_SIGNS).add(FOTBlockItemIds.COCONUT_SIGN);
        this.builder(BlockTags.WALL_SIGNS).add(FOTBlockItemIds.COCONUT_WALL_SIGN);
        this.builder(BlockTags.CEILING_HANGING_SIGNS).add(FOTBlockItemIds.COCONUT_HANGING_SIGN);
        this.builder(BlockTags.WALL_HANGING_SIGNS).add(FOTBlockItemIds.COCONUT_WALL_HANGING_SIGN);
        this.builder(BlockTags.WOODEN_TRAPDOORS).add(FOTBlockItemIds.COCONUT_TRAPDOOR);
        this.builder(BlockTags.WOODEN_DOORS).add(FOTBlockItemIds.COCONUT_DOOR);
        this.builder(BlockTags.ENCHANTMENT_POWER_TRANSMITTER).add(FOTBlockItemIds.BANANA_BLOSSOM, FOTBlockItemIds.TROPICAL_RED_FERN, FOTBlockItemIds.TROPICAL_MONSTERA);
        this.builder(BlockTags.BEE_GROWABLES).add(FOTBlockItemIds.UNDERRIPE_BANANA_CLUSTER_PLANT, FOTBlockItemIds.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlockItemIds.PINEAPPLE_CROP, FOTBlockItemIds.MANGO_FRUIT, FOTBlockItemIds.HANGING_MANGO_FRUIT, FOTBlockItemIds.MANGO_PIT,
                FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT, FOTBlockItemIds.POMEGRANATE_SAPLING);
        this.builder(BlockTags.FLOWERS).add(FOTBlockItemIds.BANANA_BLOSSOM, FOTBlockItemIds.BANANA_BLOSSOM_PLANT, FOTBlockItemIds.PINEAPPLE_CROP, FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT);
        this.builder(BlockTags.MAINTAINS_FARMLAND).add(FOTBlockItemIds.PINEAPPLE_CROP);
        this.builder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(FOTBlockItemIds.VERTICAL_BANANA_LEAVES, FOTBlockItemIds.VERTICAL_COCONUT_FRONDS,
                FOTBlockItemIds.TROPICAL_MONSTERA, FOTBlockItemIds.TROPICAL_RED_FERN);
        this.builder(BlockTags.REPLACEABLE_BY_TREES).add(FOTBlockItemIds.COCONUT_FRUIT, FOTBlockItemIds.MANGO_FRUIT, FOTBlockItemIds.HANGING_MANGO_FRUIT);
        this.builder(BlockTags.BEE_ATTRACTIVE).add(FOTBlockItemIds.PINK_PLUMERIA, FOTBlockItemIds.WHITE_PLUMERIA, FOTBlockItemIds.LIGHT_BLUE_PLUMERIA,
                FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT, FOTBlockItemIds.PINEAPPLE_CROP, FOTBlockItemIds.BANANA_BLOSSOM, FOTBlockItemIds.BANANA_BLOSSOM_PLANT);

        var replaceableList = provider.lookupOrThrow(Registries.BLOCK)
                .filterElements(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(FishOfThieves.MOD_ID) && block.defaultBlockState().canBeReplaced())
                .listElementIds().toList();

        if (!replaceableList.isEmpty())
        {
            replaceableList.forEach(this.builder(BlockTags.REPLACEABLE)::add);
        }

        this.builder(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS).add(BlockItemIds.MAGMA_BLOCK);
        this.builder(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON).forceAddTag(BlockTags.CORALS).forceAddTag(BlockTags.CORAL_BLOCKS).forceAddTag(BlockTags.WALL_CORALS);
        this.builder(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON).forceAddTag(BlockTags.CRYSTAL_SOUND_BLOCKS);
        this.builder(FOTTags.Blocks.EARTHWORMS_DROPS).forceAddTag(BlockTags.DIRT);
        this.builder(FOTTags.Blocks.GRUBS_DROPS).forceAddTag(BlockTags.SAND);
        this.builder(FOTTags.Blocks.LEECHES_DROPS).add(BlockItemIds.MUD, BlockItemIds.MUDDY_MANGROVE_ROOTS).forceAddTag(BlockTags.SAND);
        this.builder(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST).add(BlockItemIds.MOSS_BLOCK, BlockItemIds.COARSE_DIRT, BlockItemIds.MYCELIUM, BlockItemIds.MUD, BlockItemIds.MUDDY_MANGROVE_ROOTS);
        this.builder(FOTTags.Blocks.WOODEN_FISH_PLAQUE).add(FOTBlockItemIds.OAK_FISH_PLAQUE, FOTBlockItemIds.SPRUCE_FISH_PLAQUE,
                FOTBlockItemIds.BIRCH_FISH_PLAQUE, FOTBlockItemIds.JUNGLE_FISH_PLAQUE, FOTBlockItemIds.ACACIA_FISH_PLAQUE,
                FOTBlockItemIds.DARK_OAK_FISH_PLAQUE, FOTBlockItemIds.MANGROVE_FISH_PLAQUE, FOTBlockItemIds.CHERRY_FISH_PLAQUE, FOTBlockItemIds.PALE_OAK_FISH_PLAQUE,
                FOTBlockItemIds.BAMBOO_FISH_PLAQUE, FOTBlockItemIds.CRIMSON_FISH_PLAQUE, FOTBlockItemIds.WARPED_FISH_PLAQUE, FOTBlockItemIds.COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).add(FOTBlockItemIds.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_SPRUCE_FISH_PLAQUE,
                FOTBlockItemIds.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_ACACIA_FISH_PLAQUE,
                FOTBlockItemIds.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_PALE_OAK_FISH_PLAQUE,
                FOTBlockItemIds.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlockItemIds.IRON_FRAME_WARPED_FISH_PLAQUE,
                FOTBlockItemIds.IRON_FRAME_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.COPPER_FRAME_FISH_PLAQUE).add(FOTBlockItemIds.COPPER_FRAME_OAK_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_SPRUCE_FISH_PLAQUE,
                FOTBlockItemIds.COPPER_FRAME_BIRCH_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_JUNGLE_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_ACACIA_FISH_PLAQUE,
                FOTBlockItemIds.COPPER_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_MANGROVE_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_CHERRY_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_PALE_OAK_FISH_PLAQUE,
                FOTBlockItemIds.COPPER_FRAME_BAMBOO_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_CRIMSON_FISH_PLAQUE, FOTBlockItemIds.COPPER_FRAME_WARPED_FISH_PLAQUE,
                FOTBlockItemIds.COPPER_FRAME_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).add(FOTBlockItemIds.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE,
                FOTBlockItemIds.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_ACACIA_FISH_PLAQUE,
                FOTBlockItemIds.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE,
                FOTBlockItemIds.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlockItemIds.GOLDEN_FRAME_WARPED_FISH_PLAQUE,
                FOTBlockItemIds.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE).add(FOTBlockItemIds.GILDED_OAK_FISH_PLAQUE, FOTBlockItemIds.GILDED_SPRUCE_FISH_PLAQUE,
                FOTBlockItemIds.GILDED_BIRCH_FISH_PLAQUE, FOTBlockItemIds.GILDED_JUNGLE_FISH_PLAQUE, FOTBlockItemIds.GILDED_ACACIA_FISH_PLAQUE, FOTBlockItemIds.GILDED_DARK_OAK_FISH_PLAQUE,
                FOTBlockItemIds.GILDED_MANGROVE_FISH_PLAQUE, FOTBlockItemIds.GILDED_CHERRY_FISH_PLAQUE, FOTBlockItemIds.GILDED_PALE_OAK_FISH_PLAQUE, FOTBlockItemIds.GILDED_BAMBOO_FISH_PLAQUE, FOTBlockItemIds.GILDED_CRIMSON_FISH_PLAQUE,
                FOTBlockItemIds.GILDED_WARPED_FISH_PLAQUE, FOTBlockItemIds.GILDED_COCONUT_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.FISH_REPELLENTS).add(BlockItemIds.MAGMA_BLOCK).add(BlockIds.BUBBLE_COLUMN);
        this.builder(FOTTags.Blocks.FISH_PLAQUE).forceAddTag(FOTTags.Blocks.WOODEN_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.COPPER_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE).forceAddTag(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE);
        this.builder(FOTTags.Blocks.NON_FULL_LOGS)
                .add(FOTBlockItemIds.MEDIUM_COCONUT_LOG, FOTBlockItemIds.MEDIUM_COCONUT_WOOD,
                        FOTBlockItemIds.STRIPPED_MEDIUM_COCONUT_LOG,
                        FOTBlockItemIds.STRIPPED_MEDIUM_COCONUT_WOOD)
                .forceAddTag(FOTTags.Blocks.SMALL_COCONUT_LOGS).forceAddTag(FOTTags.Blocks.BANANA_STEMS);
        this.builder(FOTTags.Blocks.COCONUT_LOGS).add(FOTBlockItemIds.COCONUT_LOG, FOTBlockItemIds.MEDIUM_COCONUT_LOG,
                FOTBlockItemIds.SMALL_COCONUT_LOG, FOTBlockItemIds.SMALL_COCONUT_WOOD,
                FOTBlockItemIds.STRIPPED_SMALL_COCONUT_LOG, FOTBlockItemIds.STRIPPED_SMALL_COCONUT_WOOD,
                FOTBlockItemIds.COCONUT_WOOD, FOTBlockItemIds.MEDIUM_COCONUT_WOOD,
                FOTBlockItemIds.STRIPPED_COCONUT_LOG, FOTBlockItemIds.STRIPPED_COCONUT_WOOD, FOTBlockItemIds.STRIPPED_MEDIUM_COCONUT_LOG,
                FOTBlockItemIds.STRIPPED_MEDIUM_COCONUT_WOOD);
        this.builder(FOTTags.Blocks.SMALL_COCONUT_LOGS).add(FOTBlockItemIds.SMALL_COCONUT_LOG, FOTBlockItemIds.SMALL_COCONUT_WOOD,
                FOTBlockItemIds.COCONUT_FRUIT_GROWABLE_LOG, FOTBlockItemIds.SMALL_TOP_COCONUT_LOG,
                FOTBlockItemIds.STRIPPED_SMALL_COCONUT_LOG, FOTBlockItemIds.STRIPPED_SMALL_COCONUT_WOOD);
        this.builder(FOTTags.Blocks.COCONUT_GROWABLE_LOG_SPAWNABLE).add(FOTBlockItemIds.MEDIUM_COCONUT_LOG).forceAddTag(FOTTags.Blocks.SMALL_COCONUT_LOGS);
        this.builder(FOTTags.Blocks.BANANA_STEMS).add(FOTBlockItemIds.BANANA_STEM, FOTBlockItemIds.BANANA_CLUSTER_GROWABLE_STEM);
        this.builder(FOTTags.Blocks.BANANA_CLUSTER_PLANTS).add(FOTBlockItemIds.RIPE_BANANA_CLUSTER_PLANT, FOTBlockItemIds.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlockItemIds.UNDERRIPE_BANANA_CLUSTER_PLANT);
        this.builder(FOTTags.Blocks.BANANA_CLUSTERS).add(FOTBlockItemIds.RIPE_BANANA_CLUSTER, FOTBlockItemIds.BARELY_RIPE_BANANA_CLUSTER,
                FOTBlockItemIds.UNDERRIPE_BANANA_CLUSTER);
        this.builder(FOTTags.Blocks.BANANA_SHOOTS_PLACEABLE_ON).forceAddTag(BlockTags.DIRT);
        this.builder(FOTTags.Blocks.MANGO_FRUITS).add(FOTBlockItemIds.MANGO_FRUIT, FOTBlockItemIds.HANGING_MANGO_FRUIT);

        this.builder(FOTTags.Blocks.SERENE_SEASONS_YEAR_ROUND_CROPS)
                .add(FOTBlockItemIds.BANANA_SHOOTS, FOTBlockItemIds.BANANA_SHOOTS_PLANT,
                        FOTBlockItemIds.GUARDIAN_FRUIT)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTERS)
                .forceAddTag(FOTTags.Blocks.BANANA_CLUSTER_PLANTS);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_SPRING_CROPS)
                .add(FOTBlockItemIds.PINEAPPLE_CROP, FOTBlockItemIds.MANGO_SAPLING, FOTBlockItemIds.MANGO_PIT, FOTBlockItemIds.POMEGRANATE_SAPLING,
                        FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT)
                .forceAddTag(FOTTags.Blocks.MANGO_FRUITS);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_SUMMER_CROPS)
                .add(FOTBlockItemIds.COCONUT_SAPLING, FOTBlockItemIds.COCONUT_FRUIT,
                        FOTBlockItemIds.PINEAPPLE_CROP, FOTBlockItemIds.MANGO_SAPLING, FOTBlockItemIds.MANGO_PIT, FOTBlockItemIds.POMEGRANATE_SAPLING,
                        FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT)
                .forceAddTag(FOTTags.Blocks.MANGO_FRUITS);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_AUTUMN_CROPS)
                .add(FOTBlockItemIds.POMEGRANATE_SAPLING, FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT);
        this.builder(FOTTags.Blocks.SERENE_SEASONS_UNBREAKABLE_INFERTILE_CROPS)
                .add(FOTBlockItemIds.BANANA_SHOOTS, FOTBlockItemIds.BANANA_SHOOTS_PLANT, FOTBlockItemIds.POMEGRANATE_SAPLING,
                        FOTBlockItemIds.COCONUT_SAPLING, FOTBlockItemIds.PINEAPPLE_CROP, FOTBlockItemIds.MANGO_SAPLING, FOTBlockItemIds.MANGO_PIT,
                        FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT);
        this.builder(BlockTags.WOODEN_SHELVES).add(FOTBlockItemIds.COCONUT_SHELF);
    }
}