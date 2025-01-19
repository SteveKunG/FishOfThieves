package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

public class FOTPlacements
{
    public static final ResourceKey<PlacedFeature> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<PlacedFeature> COCONUT_TREE_CHECKED = createKey("coconut_tree_checked");
    public static final ResourceKey<PlacedFeature> MANGO_TREE_CHECKED = createKey("mango_tree_checked");
    public static final ResourceKey<PlacedFeature> BANANA_TREE_CHECKED = createKey("banana_tree_checked");

    public static final ResourceKey<PlacedFeature> TROPICAL_FLOWER = createKey("tropical_flower");
    public static final ResourceKey<PlacedFeature> TREES_TROPICAL_ISLANDS = createKey("trees_tropical_islands");
    public static final ResourceKey<PlacedFeature> WILD_PINEAPPLE = createKey("wild_pineapple");
    public static final ResourceKey<PlacedFeature> TALL_WILD_PINEAPPLE = createKey("tall_wild_pineapple");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_PINEAPPLE = createKey("patch_wild_pineapple");
    public static final ResourceKey<PlacedFeature> PATCH_MELON_TROPICAL = createKey("patch_melon_tropical");
    public static final ResourceKey<PlacedFeature> TREES_COCONUT = createKey("trees_coconut");
    public static final ResourceKey<PlacedFeature> WILD_POMEGRANATE = createKey("wild_pomegranate");
    public static final ResourceKey<PlacedFeature> TALL_WILD_POMEGRANATE = createKey("tall_wild_pomegranate");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_POMEGRANATE = createKey("patch_wild_pomegranate");
    public static final ResourceKey<PlacedFeature> PATCH_TROPICAL_BUSH = createKey("patch_tropical_bush");

    public static void bootstrap(BootstapContext<PlacedFeature> context)
    {
        var holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, FISH_BONE, holderGetter.getOrThrow(FOTFeatures.FISH_BONE), RarityFilter.onAverageOnceEvery(384), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(UniformInt.of(4, 8)), BiomeFilter.biome());
        PlacementUtils.register(context, COCONUT_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.COCONUT_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.COCONUT_SAPLING));
        PlacementUtils.register(context, MANGO_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.MANGO_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.MANGO_SAPLING));
        PlacementUtils.register(context, BANANA_TREE_CHECKED, holderGetter.getOrThrow(FOTFeatures.BANANA_TREE), PlacementUtils.filteredByBlockSurvival(FOTBlocks.BANANA_SHOOTS));

        PlacementUtils.register(context, TROPICAL_FLOWER, holderGetter.getOrThrow(FOTFeatures.TROPICAL_FLOWER), RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, TREES_TROPICAL_ISLANDS, holderGetter.getOrThrow(FOTFeatures.TREES_TROPICAL_ISLANDS), VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        PlacementUtils.register(context, PATCH_WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.PATCH_WILD_PINEAPPLE), VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        PlacementUtils.register(context, WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.WILD_PINEAPPLE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, TALL_WILD_PINEAPPLE, holderGetter.getOrThrow(FOTFeatures.TALL_WILD_PINEAPPLE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, PATCH_MELON_TROPICAL, holderGetter.getOrThrow(FOTFeatures.PATCH_TROPICAL_MELON), RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, TREES_COCONUT, holderGetter.getOrThrow(FOTFeatures.TREES_COCONUT), VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        PlacementUtils.register(context, PATCH_WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.PATCH_WILD_POMEGRANATE), PlacementUtils.countExtra(8, 0.1F, 1), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.WILD_POMEGRANATE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, TALL_WILD_POMEGRANATE, holderGetter.getOrThrow(FOTFeatures.TALL_WILD_POMEGRANATE), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
        PlacementUtils.register(context, PATCH_TROPICAL_BUSH, holderGetter.getOrThrow(FOTFeatures.PATCH_TROPICAL_BUSH), RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    private static ResourceKey<PlacedFeature> createKey(String key)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, FishOfThieves.id(key));
    }
}