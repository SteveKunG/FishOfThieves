package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.entity.FakeWaterEntity;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Entity.class)
public class MixinEntity implements FakeWaterEntity
{
    @Unique
    private boolean isInFakeWater;

    @Override
    public void fishofthieves$setIsInFakeWater()
    {
        this.isInFakeWater = true;
    }

    @Inject(method = "isInWater", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$isInFakeWater(CallbackInfoReturnable<Boolean> info)
    {
        if (this.isInFakeWater)
        {
            info.setReturnValue(true);
        }
    }

    @WrapOperation(method = "getBlockSpeedFactor", at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockState.is(Ljava/lang/Object;)Z", ordinal = 1))
    private boolean fishofthieves$shoalBlockSpeed(BlockState blockState, Object block, Operation<Boolean> operation)
    {
        return operation.call(blockState, block) && blockState.is(FOTBlocks.SHOAL);
    }
}