package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class SingleBlockFeature extends Feature<SimpleBlockConfiguration>
{
    public SingleBlockFeature(Codec<SimpleBlockConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context)
    {
        var simpleBlockConfiguration = context.config();
        var worldGenLevel = context.level();
        var blockPos = context.origin();
        var blockState = simpleBlockConfiguration.toPlace().getState(worldGenLevel, context.random(), blockPos);

        if (blockState.canSurvive(worldGenLevel, blockPos))
        {
            worldGenLevel.setBlock(blockPos, blockState, Block.UPDATE_CLIENTS);
            return true;
        }
        else
        {
            return false;
        }
    }
}