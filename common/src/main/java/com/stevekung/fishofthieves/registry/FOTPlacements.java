package com.stevekung.fishofthieves.registry;

import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.blockpredicates.BlockBrightnessPredicate;
import com.stevekung.fishofthieves.feature.blockpredicates.SeeSkyPredicate;
import com.stevekung.fishofthieves.feature.placement.ContinentsFilter;
import com.stevekung.fishofthieves.feature.placement.VegetationFilter;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

public class FOTPlacements
{
    public static final ResourceKey<PlacedFeature> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<PlacedFeature> COCONUT_TREE_CHECKED = createKey("coconut_tree_checked");
    public static final ResourceKey<PlacedFeature> OLD_COCONUT_TREE_CHECKED = createKey("old_coconut_tree_checked");
    public static final ResourceKey<PlacedFeature> MANGO_TREE_CHECKED = createKey("mango_tree_checked");
    public static final ResourceKey<PlacedFeature> MANGO_TREE_LEAF_LITTER_CHECKED = createKey("mango_tree_leaf_litter_checked");
    public static final ResourceKey<PlacedFeature> MANGO_TREE_BEES_02_LEAF_LITTER_CHECKED = createKey("mango_tree_bees_02_leaf_litter_checked");
    public static final ResourceKey<PlacedFeature> BANANA_TREE_CHECKED = createKey("banana_tree_checked");

    public static final ResourceKey<PlacedFeature> TROPICAL_FLOWER = createKey("tropical_flower");
    public static final ResourceKey<PlacedFeature> TREES_TROPICAL_ISLAND = createKey("trees_tropical_island");
    public static final ResourceKey<PlacedFeature> WILD_PINEAPPLE = createKey("wild_pineapple");
    public static final ResourceKey<PlacedFeature> TALL_WILD_PINEAPPLE = createKey("tall_wild_pineapple");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_PINEAPPLE = createKey("patch_wild_pineapple");
    public static final ResourceKey<PlacedFeature> PATCH_MELON_TROPICAL = createKey("patch_melon_tropical");
    public static final ResourceKey<PlacedFeature> TREES_COCONUT = createKey("trees_coconut");
    public static final ResourceKey<PlacedFeature> TREES_COCONUT_TROPICAL_ISLAND = createKey("trees_coconut_tropical_island");
    public static final ResourceKey<PlacedFeature> WILD_POMEGRANATE = createKey("wild_pomegranate");
    public static final ResourceKey<PlacedFeature> TALL_WILD_POMEGRANATE = createKey("tall_wild_pomegranate");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_POMEGRANATE = createKey("patch_wild_pomegranate");
    public static final ResourceKey<PlacedFeature> PATCH_TROPICAL_BUSH = createKey("patch_tropical_bush");
    public static final ResourceKey<PlacedFeature> TROPICAL_ISLAND_ROCK = createKey("tropical_island_rock");

    public static final ResourceKey<PlacedFeature> SPARSE_JUNGLE_TROPICAL_FLOWER = createKey("sparse_jungle_tropical_flower");
    public static final ResourceKey<PlacedFeature> SPARSE_JUNGLE_FRUIT_TREES = createKey("sparse_jungle_fruit_trees");
    public static final ResourceKey<PlacedFeature> SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE = createKey("sparse_jungle_patch_wild_pineapple");
    public static final ResourceKey<PlacedFeature> SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE = createKey("sparse_jungle_patch_wild_pomegranate");
    public static final ResourceKey<PlacedFeature> SPARSE_JUNGLE_PATCH_TROPICAL_BUSH = createKey("sparse_jungle_patch_tropical_bush");

    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        var holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, FISH_BONE, holderGetter.getOrThrow(FOTFeatures.FISH_BONE), RarityFilter.onAverageOnceEvery(384), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(UniformInt.of(4, 8)), BiomeFilter.biome());
        PlacementUtils.register(context, COCONUT_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.COCONUT_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.COCONUT_SAPLING));
        PlacementUtils.register(context, OLD_COCONUT_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.OLD_COCONUT_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.COCONUT_SAPLING));
        PlacementUtils.register(context, MANGO_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.MANGO_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.MANGO_SAPLING));
        PlacementUtils.register(context, MANGO_TREE_LEAF_LITTER_CHECKED, holderGetter.getOrThrow(FOTFeatures.MANGO_TREE_LEAF_LITTER), PlacementUtils.filteredByBlockSurvival(FOTBlocks.MANGO_SAPLING));
        PlacementUtils.register(context, MANGO_TREE_BEES_02_LEAF_LITTER_CHECKED, holderGetter.getOrThrow(FOTFeatures.MANGO_TREE_BEES_02_LEAF_LITTER), PlacementUtils.filteredByBlockSurvival(FOTBlocks.MANGO_SAPLING));
        PlacementUtils.register(context, BANANA_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.BANANA_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.BANANA_SHOOTS));

        PlacementUtils.register(context, TROPICAL_FLOWER, holderGetter.getOrThrow(FOTFeatures.TROPICAL_FLOWER), RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(64), RandomOffsetPlacement.ofTriangle(7, 3), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, TREES_TROPICAL_ISLAND, holderGetter.getOrThrow(FOTFeatures.TREES_TROPICAL_ISLAND), VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        PlacementUtils.register(context, PATCH_WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.PATCH_WILD_PINEAPPLE), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(32), RandomOffsetPlacement.ofTriangle(4, 2), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                BlockPredicate.replaceable(),
                BlockPredicate.noFluid(),
                BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))));
        PlacementUtils.register(context, WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.WILD_PINEAPPLE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, TALL_WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.TALL_WILD_PINEAPPLE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, PATCH_MELON_TROPICAL, holderGetter.getOrThrow(FOTFeatures.PATCH_TROPICAL_MELON), RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(16), RandomOffsetPlacement.ofTriangle(7, 3), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                BlockPredicate.replaceable(),
                BlockPredicate.noFluid(),
                BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))));
        PlacementUtils.register(context, TREES_COCONUT, holderGetter.getOrThrow(FOTFeatures.TREES_COCONUT), VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));
        PlacementUtils.register(context, TREES_COCONUT_TROPICAL_ISLAND, holderGetter.getOrThrow(FOTFeatures.TREES_COCONUT), ImmutableList.<PlacementModifier>builder()
                .addAll(VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1)))
                .add(VegetationFilter.vegetation(UniformFloat.of(-0.2f, 0.4f)))
                .add(ContinentsFilter.continents(UniformFloat.of(-1.1f, -0.9f)))
                .build());
        PlacementUtils.register(context, PATCH_WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.PATCH_WILD_POMEGRANATE), RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(16), RandomOffsetPlacement.ofTriangle(5, 3), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                BlockPredicate.replaceable(),
                BlockPredicate.noFluid(),
                BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))));
        PlacementUtils.register(context, WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.WILD_POMEGRANATE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, TALL_WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.TALL_WILD_POMEGRANATE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, PATCH_TROPICAL_BUSH, holderGetter.getOrThrow(FOTFeatures.PATCH_TROPICAL_BUSH), RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(), CountPlacement.of(32), RandomOffsetPlacement.ofTriangle(4, 2), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                BlockPredicate.replaceable(),
                BlockPredicate.not(BlockBrightnessPredicate.value(13)),
                BlockPredicate.not(SeeSkyPredicate.INSTANCE),
                BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))));
        PlacementUtils.register(context, TROPICAL_ISLAND_ROCK, holderGetter.getOrThrow(FOTFeatures.TROPICAL_ISLAND_ROCK), RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK)));

        PlacementUtils.register(context, SPARSE_JUNGLE_TROPICAL_FLOWER, holderGetter.getOrThrow(FOTFeatures.TROPICAL_FLOWER), RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(64), RandomOffsetPlacement.ofTriangle(7, 3));
        PlacementUtils.register(context, SPARSE_JUNGLE_FRUIT_TREES, holderGetter.getOrThrow(FOTFeatures.SPARSE_JUNGLE_FRUIT_TREES), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(40)));
        PlacementUtils.register(context, SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(16)));
        PlacementUtils.register(context, SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(16)));
        PlacementUtils.register(context, SPARSE_JUNGLE_PATCH_TROPICAL_BUSH, holderGetter.getOrThrow(FOTFeatures.PATCH_TROPICAL_BUSH), RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    private static ResourceKey<PlacedFeature> createKey(String key)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, FishOfThieves.id(key));
    }
}