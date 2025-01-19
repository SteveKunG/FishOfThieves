package com.stevekung.fishofthieves.feature.blockpredicates;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.registry.FOTBlockPredicateTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class SeeSkyPredicate implements BlockPredicate
{
    public static SeeSkyPredicate INSTANCE = new SeeSkyPredicate();
    public static final Codec<SeeSkyPredicate> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public boolean test(WorldGenLevel worldGenLevel, BlockPos blockPos)
    {
        return worldGenLevel.canSeeSky(blockPos);
    }

    @Override
    public BlockPredicateType<?> type()
    {
        return FOTBlockPredicateTypes.SEE_SKY;
    }
}
