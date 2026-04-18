package com.stevekung.fishofthieves.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PointedDripstoneBlock.class)
public interface PointedDripstoneBlockAccessor
{
    @Invoker
    static boolean invokeCanDripThrough(BlockGetter level, BlockPos pos, BlockState state)
    {
        throw new AssertionError("Implemented via mixin");
    }
}