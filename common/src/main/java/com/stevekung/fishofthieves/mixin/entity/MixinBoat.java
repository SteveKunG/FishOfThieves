package com.stevekung.fishofthieves.mixin.entity;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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

    @Mixin(value = Boat.Type.class, priority = 10000)
    public static class BoatType
    {
        @Shadow
        @Mutable
        @Final
        static Boat.Type[] $VALUES;

        @SuppressWarnings({ "unused", "SameParameterValue" })
        @Invoker("<init>")
        static Boat.Type fishofthieves$create(String name, int ordinal, Block planks, String boatName)
        {
            throw new IllegalStateException("Unreachable");
        }

        static
        {
            var entry = fishofthieves$create("FISHOFTHIEVES_COCONUT", $VALUES.length, Blocks.OAK_PLANKS, "fishofthieves_coconut"); // Forge is always weird, so using oak planks as temporary instead.
            $VALUES = ArrayUtils.add($VALUES, entry);

            FishOfThieves.LOGGER.info("Added new enum to {}: {}|{}", Boat.Type.class, FOTBoatTypes.COCONUT.name(), FOTBoatTypes.COCONUT);
        }
    }
}