package com.stevekung.fishofthieves.feature;

import com.stevekung.fishofthieves.registry.FOTFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CoconutTreeGrower extends AbstractTreeGrower
{
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers)
    {
        return FOTFeatures.COCONUT_TREE;
    }
}