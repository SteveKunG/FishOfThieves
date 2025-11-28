package com.stevekung.fishofthieves.mixin.level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

@Mixin(FlowingFluid.class)
public class MixinFlowingFluid
{
    @Inject(method = "canHoldAnyFluid", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$canHoldAnyFluid(BlockState state, CallbackInfoReturnable<Boolean> info)
    {
        if (state.is(FOTBlocks.SHOAL))
        {
            info.setReturnValue(false);
        }
    }
}