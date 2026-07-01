package com.stevekung.fishofthieves.feature.blockpredicates;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTBlockPredicateTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class SeeSkyPredicate implements BlockPredicate
{
    public static final SeeSkyPredicate INSTANCE = new SeeSkyPredicate();
    public static final MapCodec<SeeSkyPredicate> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public boolean test(LevelAccessor level, BlockPos blockPos)
    {
        return level.canSeeSky(blockPos);
    }

    @Override
    public BlockPredicateType<?> type()
    {
        return FOTBlockPredicateTypes.SEE_SKY;
    }
}
