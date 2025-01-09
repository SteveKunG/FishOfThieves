package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.HangingMangoFruitBlock;
import com.stevekung.fishofthieves.block.MangoFruitBlock;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import com.stevekung.fishofthieves.feature.foliageplacers.BananaLeavesPlacer;
import com.stevekung.fishofthieves.feature.foliageplacers.CoconutFrondsPlacer;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntStateProvider;
import com.stevekung.fishofthieves.feature.treedecorators.BananaDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.BananaShootsDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import com.stevekung.fishofthieves.feature.trunkplacers.BananaTrunkPlacer;
import com.stevekung.fishofthieves.feature.trunkplacers.CoconutTrunkPlacer;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public class FOTFeatures
{
    private static final FishBoneFeature FISH_BONE_FEATURE = new FishBoneFeature(NoneFeatureConfiguration.CODEC);

    public static final ResourceKey<ConfiguredFeature<?, ?>> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_TREE = createKey("coconut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BANANA_TREE = createKey("banana_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE = createKey("mango_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGO_TREE_BEES_005 = createKey("mango_tree_bees_005");

    public static void init()
    {
        register("fish_bone", FISH_BONE_FEATURE);
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
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
        FeatureUtils.register(context, MANGO_TREE, Feature.TREE, createMangoTree().build());
        FeatureUtils.register(context, MANGO_TREE_BEES_005, Feature.TREE, createMangoTree().decorators(List.of(new BeehiveDecorator(0.05F))).build());
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
                new BananaTrunkPlacer(3, 6, BlockStateProvider.simple(FOTBlocks.GROWABLE_BANANA_STEM), BlockStateProvider.simple(FOTBlocks.BANANA_STEM), BlockStateProvider.simple(FOTBlocks.TOP_BANANA_STEM)),
                BlockStateProvider.simple(FOTBlocks.BANANA_LEAVES),
                new BananaLeavesPlacer(0.2f),
                new ThreeLayersFeatureSize(5, 8, 1, 2, 5, OptionalInt.empty()));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMangoTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new FancyTrunkPlacer(5, 11, 0),
                BlockStateProvider.simple(FOTBlocks.MANGO_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(3),
                        ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .decorators(List.of(
                        new AttachedToLeavesDecorator(0.1F, 2, 0,
                                new RandomizedIntStateProvider(BlockStateProvider.simple(FOTBlocks.HANGING_MANGO_FRUIT.defaultBlockState()),
                                        HangingMangoFruitBlock.AGE, UniformInt.of(0, 2)), 2, List.of(Direction.DOWN)),
                        new AttachedToLeavesDecorator(0.5F, 1, 1,
                                new DirectionalRandomizedIntStateProvider(BlockStateProvider.simple(FOTBlocks.MANGO_FRUIT.defaultBlockState()),
                                        MangoFruitBlock.AGE, UniformInt.of(0, 2), MangoFruitBlock.FACING, Direction.Plane.HORIZONTAL.stream().toList()), 1, Direction.Plane.HORIZONTAL.stream().toList()),
                        new BeehiveDecorator(0.01F)))
                .ignoreVines();
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