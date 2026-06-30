package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;

public record SimpleAgeBlockFeature(RandomizedIntStateProvider toPlace) implements Feature
{
    public static final MapCodec<SimpleAgeBlockFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    RandomizedIntStateProvider.CODEC.fieldOf("to_place").forGetter(config -> config.toPlace))
            .apply(instance, SimpleAgeBlockFeature::new));

    @Override
    public MapCodec<? extends Feature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        var blockState = this.toPlace.getState(level, random, origin);

        if (blockState.canSurvive(level, origin))
        {
            if (blockState.getBlock() instanceof DoublePlantBlock)
            {
                if (!level.isEmptyBlock(origin.above()))
                {
                    return false;
                }
                DoublePlantBlock.placeAt(level, blockState, origin, Block.UPDATE_CLIENTS);
            }
            else
            {
                level.setBlock(origin, blockState, Block.UPDATE_CLIENTS);
            }
        }
        else
        {
            return false;
        }
        return true;
    }
}