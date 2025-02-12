package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.HangingMangoFruitBlock;
import com.stevekung.fishofthieves.block.MangoFruitBlock;
import com.stevekung.fishofthieves.block.PineappleCropBlock;
import com.stevekung.fishofthieves.block.PomegranatePlantBlock;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import com.stevekung.fishofthieves.feature.SimpleAgeBlockFeature;
import com.stevekung.fishofthieves.feature.SingleBlockFeature;
import com.stevekung.fishofthieves.feature.TropicalIslandBlockBlobFeature;
import com.stevekung.fishofthieves.feature.blockpredicates.BlockBrightnessPredicate;
import com.stevekung.fishofthieves.feature.blockpredicates.SeeSkyPredicate;
import com.stevekung.fishofthieves.feature.configurations.SimpleAgeBlockConfiguration;
import com.stevekung.fishofthieves.feature.foliageplacers.BananaLeavesPlacer;
import com.stevekung.fishofthieves.feature.foliageplacers.CoconutFrondsPlacer;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntBooleanStateProvider;
import com.stevekung.fishofthieves.feature.stateproviders.RandomizedIntBooleanStateProvider;
import com.stevekung.fishofthieves.feature.treedecorators.BananaDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.BananaShootsDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.DirectionalAttachedToLeavesDecorator;
import com.stevekung.fishofthieves.feature.trunkplacers.BananaTrunkPlacer;
import com.stevekung.fishofthieves.feature.trunkplacers.CoconutTrunkPlacer;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public class FOTFeatures
{
    private static final FishBoneFeature FISH_BONE_FEATURE = new FishBoneFeature(NoneFeatureConfiguration.CODEC);
    private static final Feature<SimpleAgeBlockConfiguration> SIMPLE_AGE_BLOCK = new SimpleAgeBlockFeature(SimpleAgeBlockConfiguration.CODEC);
    private static final Feature<SimpleBlockConfiguration> SINGLE_BLOCK = new SingleBlockFeature(SimpleBlockConfiguration.CODEC);
    private static final Feature<BlockStateConfiguration> TROPICAL_ISLAND_BLOB = new TropicalIslandBlockBlobFeature(BlockStateConfiguration.CODEC);

    public static final ResourceKey<ConfiguredFeature<?, ?>> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_TREE = createKey("coconut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BANANA_TREE = createKey("banana_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE = createKey("mango_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE_BEES_02 = createKey("mango_tree_bees_02");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TROPICAL_FLOWER = createKey("tropical_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TROPICAL_ISLAND = createKey("trees_tropical_island");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_PINEAPPLE = createKey("wild_pineapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_WILD_PINEAPPLE = createKey("tall_wild_pineapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_PINEAPPLE = createKey("patch_wild_pineapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TROPICAL_MELON = createKey("patch_tropical_melon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_COCONUT = createKey("trees_coconut");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_POMEGRANATE = createKey("wild_pomegranate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_WILD_POMEGRANATE = createKey("tall_wild_pomegranate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_POMEGRANATE = createKey("patch_wild_pomegranate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TROPICAL_BUSH = createKey("patch_tropical_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TROPICAL_ISLAND_ROCK = createKey("tropical_island_rock");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SPARSE_JUNGLE_FRUIT_TREES = createKey("sparse_jungle_fruit_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE = createKey("sparse_jungle_patch_wild_pineapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE = createKey("sparse_jungle_patch_wild_pomegranate");

    public static void init()
    {
        register("fish_bone", FISH_BONE_FEATURE);
        register("simple_age_block", SIMPLE_AGE_BLOCK);
        register("single_block", SINGLE_BLOCK);
        register("tropical_island_blob", TROPICAL_ISLAND_BLOB);
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);

        FeatureUtils.register(context, FISH_BONE, FISH_BONE_FEATURE, NoneFeatureConfiguration.INSTANCE);
        FeatureUtils.register(context, COCONUT_TREE, Feature.TREE, createCoconutTree()
                .decorators(ImmutableList.of(new CoconutDecorator(0.6F, 0.45F, 2)))
                .dirt(BlockStateProvider.simple(Blocks.SAND))
                .ignoreVines()
                .build());
        FeatureUtils.register(context, BANANA_TREE, Feature.TREE, createBananaTree()
                .decorators(ImmutableList.of(
                        new BananaDecorator(0.4f, 0.2f, 0.4f, 6),
                        new BananaShootsDecorator(0.3f)))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .ignoreVines()
                .build());
        FeatureUtils.register(context, MANGO_TREE, Feature.TREE, createMangoTree(0.01F).build());
        FeatureUtils.register(context, MANGO_TREE_BEES_02, Feature.TREE, createMangoTree(0.2F).build());

        FeatureUtils.register(context, TREES_TROPICAL_ISLAND, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(placedFeature.getOrThrow(TreePlacements.FANCY_OAK_CHECKED), 0.05F),
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.MANGO_TREE_CHECKED), 0.1F),
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.BANANA_TREE_CHECKED), 0.1F)),
                placedFeature.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED)));
        FeatureUtils.register(context, TROPICAL_FLOWER, Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(FOTBlocks.PINK_PLUMERIA.defaultBlockState(), 8)
                .add(FOTBlocks.LIGHT_BLUE_PLUMERIA.defaultBlockState(), 6)
                .add(FOTBlocks.WHITE_PLUMERIA.defaultBlockState(), 3)
        ), 64));
        FeatureUtils.register(context, WILD_PINEAPPLE, Feature.FLOWER, wildPineapplePatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 0), 8)
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 1), 6)
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 2), 4)
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 3), 2)
        ), 16));
        FeatureUtils.register(context, TALL_WILD_PINEAPPLE, SIMPLE_AGE_BLOCK, new SimpleAgeBlockConfiguration(
                new RandomizedIntStateProvider(BlockStateProvider.simple(FOTBlocks.PINEAPPLE_CROP.defaultBlockState()), PineappleCropBlock.AGE, UniformInt.of(4, 5))));
        FeatureUtils.register(context, PATCH_WILD_PINEAPPLE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.TALL_WILD_PINEAPPLE), 0.85F)),
                placedFeature.getOrThrow(FOTPlacements.WILD_PINEAPPLE)));
        FeatureUtils.register(context, PATCH_TROPICAL_MELON, Feature.RANDOM_PATCH, new RandomPatchConfiguration(16, 7, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MELON)), BlockPredicate.allOf(
                        BlockPredicate.replaceable(),
                        BlockPredicate.noFluid(),
                        BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.GRASS_BLOCK)))));
        FeatureUtils.register(context, TREES_COCONUT, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(placedFeature.getOrThrow(FOTPlacements.COCONUT_TREE_CHECKED))));
        FeatureUtils.register(context, WILD_POMEGRANATE, Feature.FLOWER, wildPomegranatePatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 0), 8)
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 1), 6)
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 2), 4)
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 3), 2)
        ), 16));
        FeatureUtils.register(context, TALL_WILD_POMEGRANATE, SIMPLE_AGE_BLOCK, new SimpleAgeBlockConfiguration(
                new RandomizedIntStateProvider(BlockStateProvider.simple(FOTBlocks.TALL_POMEGRANATE_PLANT.defaultBlockState()), PomegranatePlantBlock.AGE, UniformInt.of(0, 3))));
        FeatureUtils.register(context, PATCH_WILD_POMEGRANATE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.TALL_WILD_POMEGRANATE), 0.5F)),
                placedFeature.getOrThrow(FOTPlacements.WILD_POMEGRANATE)));
        FeatureUtils.register(context, PATCH_TROPICAL_BUSH, Feature.FLOWER,
                new RandomPatchConfiguration(32, 4, 2, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                        .add(FOTBlocks.TROPICAL_MONSTERA.defaultBlockState(), 4)
                                        .add(FOTBlocks.TROPICAL_RED_FERN.defaultBlockState(), 2)
                                        .add(FOTBlocks.VERTICAL_BANANA_LEAVES.defaultBlockState(), 1))),
                        BlockPredicate.allOf(
                                BlockPredicate.replaceable(),
                                BlockPredicate.not(BlockBrightnessPredicate.value(13)),
                                BlockPredicate.not(SeeSkyPredicate.INSTANCE),
                                BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.GRASS_BLOCK)))));
        FeatureUtils.register(context, TROPICAL_ISLAND_ROCK, TROPICAL_ISLAND_BLOB, new BlockStateConfiguration(Blocks.STONE.defaultBlockState()));

        FeatureUtils.register(context, SPARSE_JUNGLE_FRUIT_TREES, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.MANGO_TREE_CHECKED),
                placedFeature.getOrThrow(FOTPlacements.BANANA_TREE_CHECKED))));
        FeatureUtils.register(context, SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.TALL_WILD_PINEAPPLE),
                placedFeature.getOrThrow(FOTPlacements.WILD_PINEAPPLE))));
        FeatureUtils.register(context, SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.TALL_WILD_POMEGRANATE),
                placedFeature.getOrThrow(FOTPlacements.WILD_POMEGRANATE))));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createCoconutTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FOTBlocks.COCONUT_LOG),
                new CoconutTrunkPlacer(7, 2, 2, BlockStateProvider.simple(FOTBlocks.SMALL_COCONUT_LOG), BlockStateProvider.simple(FOTBlocks.MEDIUM_COCONUT_LOG), BlockStateProvider.simple(FOTBlocks.TOP_SMALL_COCONUT_LOG)),
                BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS),
                new CoconutFrondsPlacer(2, 1, List.of(Pair.of(7, 1))),
                new ThreeLayersFeatureSize(5, 15, 1, 2, 4, OptionalInt.empty()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBananaTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FOTBlocks.BANANA_STEM),
                new BananaTrunkPlacer(3, 6, BlockStateProvider.simple(FOTBlocks.TOP_BANANA_STEM)),
                BlockStateProvider.simple(FOTBlocks.BANANA_LEAVES),
                new BananaLeavesPlacer(0.2f),
                new ThreeLayersFeatureSize(5, 8, 1, 2, 5, OptionalInt.empty()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMangoTree(float beehiveChance)
    {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new FancyTrunkPlacer(5, 11, 0),
                BlockStateProvider.simple(FOTBlocks.MANGO_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(3),
                        ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .decorators(List.of(
                        new AttachedToLeavesDecorator(0.1F, 2, 0,
                                new RandomizedIntBooleanStateProvider(BlockStateProvider.simple(FOTBlocks.HANGING_MANGO_FRUIT.defaultBlockState()),
                                        HangingMangoFruitBlock.AGE, UniformInt.of(0, 2),
                                        MangoFruitBlock.FALLING, ConstantFloat.of(0.6f)), 2, List.of(Direction.DOWN)),
                        new DirectionalAttachedToLeavesDecorator(0.5F, 1, 1,
                                new DirectionalRandomizedIntBooleanStateProvider(BlockStateProvider.simple(FOTBlocks.MANGO_FRUIT.defaultBlockState()),
                                        MangoFruitBlock.AGE, UniformInt.of(0, 2),
                                        MangoFruitBlock.FACING,
                                        MangoFruitBlock.FALLING, ConstantFloat.of(0.6f)), 1, Direction.Plane.HORIZONTAL.stream().toList(), true),
                        new BeehiveDecorator(beehiveChance)))
                .ignoreVines();
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider blockStateProvider, int tries)
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
    }

    private static RandomPatchConfiguration wildPineapplePatch(BlockStateProvider blockStateProvider, int tries)
    {
        return new RandomPatchConfiguration(tries, 4, 2, PlacementUtils.onlyWhenEmpty(SINGLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
    }

    private static RandomPatchConfiguration wildPomegranatePatch(BlockStateProvider blockStateProvider, int tries)
    {
        return new RandomPatchConfiguration(tries, 5, 3, PlacementUtils.onlyWhenEmpty(SINGLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> void register(String key, F value)
    {
        FOTPlatform.registerFeature(key, value);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FishOfThieves.id(name));
    }
}