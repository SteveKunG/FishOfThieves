package com.stevekung.fishofthieves.mixin.accessor;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SpeleothemBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(SpeleothemBlock.class)
public interface SpeleothemBlockAccessor
{
    @Invoker
    static Optional<BlockPos> invokeFindBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection axis, BiPredicate<BlockPos, BlockState> positionalStatePredicate, Predicate<BlockState> statePredicate, int maxIterations)
    {
        throw new AssertionError("Implemented via mixin");
    }
}