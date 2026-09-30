package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@Mixin(Boat.class)
public class MixinBoat
{
    @Inject(method = "getDropItem", cancellable = true, at = @At("TAIL"))
    private void fishofthieves$getBoatDrop(CallbackInfoReturnable<Item> info)
    {
        if (Boat.class.cast(this).getVariant() == FOTBoatTypes.COCONUT)
        {
            info.setReturnValue(FOTItems.COCONUT_BOAT);
        }
    }

    @WrapOperation(method = "checkFallDamage", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/vehicle/Boat$Type.getPlanks()Lnet/minecraft/world/level/block/Block;"))
    private Block fishofthieves$getBoatPlanksDrop(Boat.Type boatType, Operation<Block> operation)
    {
        if (boatType == FOTBoatTypes.COCONUT)
        {
            return FOTBlocks.COCONUT_PLANKS;
        }
        return operation.call(boatType);
    }
}