package com.stevekung.fishofthieves.neoforge.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(CropBlock.class)
public interface CropBlockAccessor
{
    @Invoker
    static float callGetGrowthSpeed(BlockState blockState, BlockGetter level, BlockPos pos)
    {
        throw new AssertionError("Implemented via mixin");
    }
}