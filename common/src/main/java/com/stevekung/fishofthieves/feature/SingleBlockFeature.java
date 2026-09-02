package com.stevekung.fishofthieves.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SingleBlockFeature(Holder<BlockStateProvider> toPlace) implements Feature
{
    public static final MapCodec<SingleBlockFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("to_place").forGetter(config -> config.toPlace))
            .apply(instance, SingleBlockFeature::new));

    @Override
    public MapCodec<? extends Feature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        var blockState = this.toPlace.value().getState(level, random, origin);

        if (blockState.canSurvive(level, origin))
        {
            level.setBlock(origin, blockState, Block.UPDATE_CLIENTS);
            return true;
        }
        else
        {
            return false;
        }
    }
}