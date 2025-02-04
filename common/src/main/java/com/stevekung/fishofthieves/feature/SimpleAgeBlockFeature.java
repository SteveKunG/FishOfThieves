package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.feature.configurations.SimpleAgeBlockConfiguration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SimpleAgeBlockFeature extends Feature<SimpleAgeBlockConfiguration>
{
    public SimpleAgeBlockFeature(Codec<SimpleAgeBlockConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleAgeBlockConfiguration> context)
    {
        var simpleBlockConfiguration = context.config();
        var worldGenLevel = context.level();
        var blockPos = context.origin();
        var blockState = simpleBlockConfiguration.toPlace().getState(context.random(), blockPos);

        if (blockState.canSurvive(worldGenLevel, blockPos))
        {
            if (blockState.getBlock() instanceof DoublePlantBlock)
            {
                if (!worldGenLevel.isEmptyBlock(blockPos.above()))
                {
                    return false;
                }
                DoublePlantBlock.placeAt(worldGenLevel, blockState, blockPos, Block.UPDATE_CLIENTS);
            }
            else
            {
                worldGenLevel.setBlock(blockPos, blockState, Block.UPDATE_CLIENTS);
            }
        }
        else
        {
            return false;
        }
        return true;
    }
}