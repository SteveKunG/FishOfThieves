package com.stevekung.fishofthieves.mixin.level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

@Mixin(FlowingFluid.class)
public class MixinFlowingFluid
{
    @Inject(method = "canHoldFluid", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$canHoldFluid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> info)
    {
        if (state.is(FOTBlocks.SHOAL))
        {
            info.setReturnValue(false);
        }
    }
}