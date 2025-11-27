package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Entity.class)
public class MixinEntity
{
    @WrapOperation(method = "getBlockSpeedFactor", at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockState.is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1))
    private boolean fishofthieves$shoalBlockSpeed(BlockState blockState, Block block, Operation<Boolean> operation)
    {
        return operation.call(blockState, block) && blockState.is(FOTBlocks.SHOAL_BLOCK);
    }
}