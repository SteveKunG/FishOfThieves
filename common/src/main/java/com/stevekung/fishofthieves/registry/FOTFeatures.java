package com.stevekung.fishofthieves.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.*;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import com.stevekung.fishofthieves.feature.SimpleAgeBlockFeature;
import com.stevekung.fishofthieves.feature.SingleBlockFeature;
import com.stevekung.fishofthieves.feature.TropicalIslandBlockBlobFeature;
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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
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
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public class FOTFeatures
{
    private static final FishBoneFeature FISH_BONE_FEATURE = register("fish_bone", new FishBoneFeature(NoneFeatureConfiguration.CODEC));
    private static final SimpleAgeBlockFeature SIMPLE_AGE_BLOCK = register("simple_age_block", new SimpleAgeBlockFeature(SimpleAgeBlockConfiguration.CODEC));
    private static final SingleBlockFeature SINGLE_BLOCK = register("single_block", new SingleBlockFeature(SimpleBlockConfiguration.CODEC));
    private static final TropicalIslandBlockBlobFeature TROPICAL_ISLAND_BLOB = register("tropical_island_blob", new TropicalIslandBlockBlobFeature(BlockBlobConfiguration.CODEC));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_TREE = createKey("coconut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_COCONUT_TREE = createKey("old_coconut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BANANA_TREE = createKey("banana_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE = createKey("mango_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE_BEES_02 = createKey("mango_tree_bees_02");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE_LEAF_LITTER = createKey("mango_tree_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE_BEES_02_LEAF_LITTER = createKey("mango_tree_bees_02_leaf_litter");

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
        FishOfThieves.LOGGER.info("Registering Feature");
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context)
    {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);

        FeatureUtils.register(context, FISH_BONE, FISH_BONE_FEATURE, NoneFeatureConfiguration.INSTANCE);
        FeatureUtils.register(context, COCONUT_TREE, Feature.TREE, createCoconutTree()
                .decorators(List.of(new CoconutDecorator(0.6F, 0.45F, 2)))
                .belowTrunkProvider(BlockStateProvider.simple(Blocks.SAND))
                .ignoreVines()
                .build());
        FeatureUtils.register(context, OLD_COCONUT_TREE, Feature.TREE, createOldCoconutTree()
                .decorators(List.of(new CoconutDecorator(0.2F, 0.7F, 3)))
                .belowTrunkProvider(BlockStateProvider.simple(Blocks.SAND))
                .ignoreVines()
                .build());
        FeatureUtils.register(context, BANANA_TREE, Feature.TREE, createBananaTree()
                .decorators(List.of(
                        new BananaDecorator(0.4f, 0.2f, 0.4f, 6),
                        new BananaShootsDecorator(0.3f)))
                .belowTrunkProvider(BlockStateProvider.simple(Blocks.DIRT))
                .ignoreVines()
                .build());
        List<TreeDecorator> leafLitters = List.of(
                new PlaceOnGroundDecorator(96, 4, 2, new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 3))),
                new PlaceOnGroundDecorator(150, 2, 2, new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 4))));
        FeatureUtils.register(context, MANGO_TREE, Feature.TREE, createMangoTree(0.01F).build());
        FeatureUtils.register(context, MANGO_TREE_BEES_02, Feature.TREE, createMangoTree(0.2F).build());
        FeatureUtils.register(context, MANGO_TREE_LEAF_LITTER, Feature.TREE, createMangoTree(0.01F, leafLitters).build());
        FeatureUtils.register(context, MANGO_TREE_BEES_02_LEAF_LITTER, Feature.TREE, createMangoTree(0.2F, leafLitters).build());

        FeatureUtils.register(context, TREES_TROPICAL_ISLAND, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(placedFeature.getOrThrow(TreePlacements.FANCY_OAK_LEAF_LITTER), 0.05F),
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.MANGO_TREE_LEAF_LITTER_CHECKED), 0.05F),
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.MANGO_TREE_BEES_02_LEAF_LITTER_CHECKED), 0.05F),
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.BANANA_TREE_CHECKED), 0.1F),
                new WeightedPlacedFeature(placedFeature.getOrThrow(TreePlacements.FALLEN_JUNGLE_TREE), 0.0125F)
        ), placedFeature.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED)));
        FeatureUtils.register(context, TROPICAL_FLOWER, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                .add(FOTBlocks.PINK_PLUMERIA.defaultBlockState(), 12)
                .add(FOTBlocks.LIGHT_BLUE_PLUMERIA.defaultBlockState(), 10)
                .add(FOTBlocks.WHITE_PLUMERIA.defaultBlockState(), 8)
                .add(FOTBlocks.TROPICAL_MONSTERA.defaultBlockState(), 3)
                .add(FOTBlocks.TROPICAL_RED_FERN.defaultBlockState(), 3)
                .add(FOTBlocks.VERTICAL_BANANA_LEAVES.defaultBlockState(), 2)
        )));
        FeatureUtils.register(context, WILD_PINEAPPLE, SINGLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 0), 8)
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 1), 6)
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 2), 4)
                .add(FOTBlocks.PINEAPPLE_CROP.defaultBlockState().setValue(PineappleCropBlock.AGE, 3), 2)
        )));
        FeatureUtils.register(context, TALL_WILD_PINEAPPLE, SIMPLE_AGE_BLOCK, new SimpleAgeBlockConfiguration(
                new RandomizedIntStateProvider(BlockStateProvider.simple(FOTBlocks.PINEAPPLE_CROP.defaultBlockState()), PineappleCropBlock.AGE, UniformInt.of(4, 5))));
        FeatureUtils.register(context, PATCH_WILD_PINEAPPLE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.TALL_WILD_PINEAPPLE), 0.85F)),
                placedFeature.getOrThrow(FOTPlacements.WILD_PINEAPPLE)));
        FeatureUtils.register(context, PATCH_TROPICAL_MELON, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MELON)));
        FeatureUtils.register(context, TREES_COCONUT, Feature.SIMPLE_RANDOM_SELECTOR, new CompositeFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.COCONUT_TREE_CHECKED),
                placedFeature.getOrThrow(FOTPlacements.OLD_COCONUT_TREE_CHECKED)
        )));
        FeatureUtils.register(context, WILD_POMEGRANATE, SINGLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 0), 8)
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 1), 6)
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 2), 4)
                .add(FOTBlocks.POMEGRANATE_PLANT.defaultBlockState().setValue(PomegranatePlantBlock.AGE, 3), 2)
        )));
        FeatureUtils.register(context, TALL_WILD_POMEGRANATE, SIMPLE_AGE_BLOCK, new SimpleAgeBlockConfiguration(
                new RandomizedIntStateProvider(BlockStateProvider.simple(FOTBlocks.TALL_POMEGRANATE_PLANT.defaultBlockState()), PomegranatePlantBlock.AGE, UniformInt.of(0, 3))));
        FeatureUtils.register(context, PATCH_WILD_POMEGRANATE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(placedFeature.getOrThrow(FOTPlacements.TALL_WILD_POMEGRANATE), 0.5F)),
                placedFeature.getOrThrow(FOTPlacements.WILD_POMEGRANATE)));
        FeatureUtils.register(context, PATCH_TROPICAL_BUSH, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                .add(FOTBlocks.TROPICAL_MONSTERA.defaultBlockState(), 4)
                                .add(FOTBlocks.TROPICAL_RED_FERN.defaultBlockState(), 2)
                                .add(FOTBlocks.VERTICAL_BANANA_LEAVES.defaultBlockState(), 1))));
        FeatureUtils.register(context, TROPICAL_ISLAND_ROCK, TROPICAL_ISLAND_BLOB, new BlockBlobConfiguration(Blocks.STONE.defaultBlockState(), BlockPredicate.matchesTag(BlockTags.FOREST_ROCK_CAN_PLACE_ON)));

        FeatureUtils.register(context, SPARSE_JUNGLE_FRUIT_TREES, Feature.SIMPLE_RANDOM_SELECTOR, new CompositeFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.MANGO_TREE_LEAF_LITTER_CHECKED),
                placedFeature.getOrThrow(FOTPlacements.BANANA_TREE_CHECKED))));
        FeatureUtils.register(context, SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE, Feature.SIMPLE_RANDOM_SELECTOR, new CompositeFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.TALL_WILD_PINEAPPLE),
                placedFeature.getOrThrow(FOTPlacements.WILD_PINEAPPLE))));
        FeatureUtils.register(context, SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE, Feature.SIMPLE_RANDOM_SELECTOR, new CompositeFeatureConfiguration(HolderSet.direct(
                placedFeature.getOrThrow(FOTPlacements.TALL_WILD_POMEGRANATE),
                placedFeature.getOrThrow(FOTPlacements.WILD_POMEGRANATE))));
    }


    private static TreeConfiguration.TreeConfigurationBuilder createCoconutTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FOTBlocks.COCONUT_LOG),
                new CoconutTrunkPlacer(8, 2, UniformInt.of(1, 2), UniformInt.of(1, 2), false, BlockStateProvider.simple(FOTBlocks.SMALL_COCONUT_LOG), BlockStateProvider.simple(FOTBlocks.MEDIUM_COCONUT_LOG), BlockStateProvider.simple(FOTBlocks.SMALL_TOP_COCONUT_LOG)),
                BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS),
                new CoconutFrondsPlacer(2, 1, BlockStateProvider.simple(FOTBlocks.VERTICAL_COCONUT_FRONDS), BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE)), BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL)), Pair.of(8, 1)),
                new ThreeLayersFeatureSize(2, 2, 0, 2, 2, OptionalInt.empty()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createOldCoconutTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FOTBlocks.COCONUT_LOG),
                new CoconutTrunkPlacer(12, 3, UniformInt.of(8, 9), UniformInt.of(1, 2), true, BlockStateProvider.simple(FOTBlocks.SMALL_COCONUT_LOG), BlockStateProvider.simple(FOTBlocks.MEDIUM_COCONUT_LOG), BlockStateProvider.simple(FOTBlocks.SMALL_TOP_COCONUT_LOG)),
                BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS),
                new CoconutFrondsPlacer(3, 1, BlockStateProvider.simple(FOTBlocks.VERTICAL_COCONUT_FRONDS), BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE)), BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL))),
                new ThreeLayersFeatureSize(2, 2, 0, 2, 2, OptionalInt.empty()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBananaTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FOTBlocks.BANANA_STEM),
                new BananaTrunkPlacer(3, 6, BlockStateProvider.simple(FOTBlocks.BANANA_CLUSTER_GROWABLE_STEM)),
                BlockStateProvider.simple(FOTBlocks.BANANA_LEAVES.defaultBlockState().setValue(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER)),
                new BananaLeavesPlacer(0.2f, BlockStateProvider.simple(FOTBlocks.VERTICAL_BANANA_LEAVES), BlockStateProvider.simple(FOTBlocks.BANANA_LEAVES.defaultBlockState().setValue(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL).setValue(BananaLeavesBlock.TYPE, BananaLeavesBlock.Type.UPPER))),
                new ThreeLayersFeatureSize(2, 2, 0, 2, 2, OptionalInt.empty()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMangoTree(float beehiveChance, List<TreeDecorator> additionalDecorators)
    {
        var decorators = new ArrayList<>(List.of(
                new AttachedToLeavesDecorator(0.1F, 2, 0,
                        new RandomizedIntBooleanStateProvider(BlockStateProvider.simple(FOTBlocks.HANGING_MANGO_FRUIT.defaultBlockState()),
                                HangingMangoFruitBlock.AGE, UniformInt.of(0, 2),
                                MangoFruitBlock.FALLING, ConstantFloat.of(0.6f)), 2, List.of(Direction.DOWN)),
                new DirectionalAttachedToLeavesDecorator(0.5F, 1, 1,
                        new DirectionalRandomizedIntBooleanStateProvider(BlockStateProvider.simple(FOTBlocks.MANGO_FRUIT.defaultBlockState()),
                                MangoFruitBlock.AGE, UniformInt.of(0, 2),
                                MangoFruitBlock.FACING,
                                MangoFruitBlock.FALLING, ConstantFloat.of(0.6f)), 1, Direction.Plane.HORIZONTAL.stream().toList(), true),
                new BeehiveDecorator(beehiveChance)
        ));
        decorators.addAll(additionalDecorators);
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new FancyTrunkPlacer(5, 11, 0),
                BlockStateProvider.simple(FOTBlocks.MANGO_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(3),
                        ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .decorators(decorators)
                .ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMangoTree(float beehiveChance)
    {
        return createMangoTree(beehiveChance, List.of());
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F feature)
    {
        return Registry.register(BuiltInRegistries.FEATURE, FishOfThieves.id(key), feature);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FishOfThieves.id(name));
    }
}