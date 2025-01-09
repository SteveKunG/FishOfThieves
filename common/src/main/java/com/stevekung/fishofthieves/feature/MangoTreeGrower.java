package com.stevekung.fishofthieves.feature;

import com.stevekung.fishofthieves.registry.FOTFeatures;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MangoTreeGrower extends AbstractTreeGrower
{
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers)
    {
        return hasFlowers ? FOTFeatures.MANGO_TREE_BEES_02 : FOTFeatures.MANGO_TREE;
    }
}