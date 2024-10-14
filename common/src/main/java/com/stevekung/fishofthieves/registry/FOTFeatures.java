package com.stevekung.fishofthieves.registry;

import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import com.stevekung.fishofthieves.feature.foliageplacers.CoconutFrondsPlacer;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import com.stevekung.fishofthieves.feature.trunkplacers.CoconutTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FOTFeatures
{
    private static final FishBoneFeature FISH_BONE_FEATURE = new FishBoneFeature(NoneFeatureConfiguration.CODEC);

    public static final ResourceKey<ConfiguredFeature<?, ?>> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COCONUT_TREE = createKey("coconut_tree");

    public static void init()
    {
        register("fish_bone", FISH_BONE_FEATURE);
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext)
    {
        FeatureUtils.register(bootstapContext, FISH_BONE, FISH_BONE_FEATURE, NoneFeatureConfiguration.INSTANCE);
        FeatureUtils.register(bootstapContext, COCONUT_TREE, Feature.TREE, createCoconutTree()
                .decorators(ImmutableList.of(new CoconutDecorator(0.6F, 0.45F, 2)))
                .dirt(BlockStateProvider.simple(Blocks.SAND))
                .ignoreVines()
                .build());
    }

    private static TreeConfiguration.TreeConfigurationBuilder createCoconutTree()
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FOTBlocks.COCONUT_LOG),
                new CoconutTrunkPlacer(7, 2, 2),
                BlockStateProvider.simple(FOTBlocks.COCONUT_FRONDS),
                new CoconutFrondsPlacer(2, 1, new CoconutFrondsPlacer.ReduceLeavesLength(7, 1)),
                new ThreeLayersFeatureSize(5, 15, 1, 2, 4, OptionalInt.empty()));
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