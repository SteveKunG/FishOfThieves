package com.stevekung.fishofthieves.mixin.entity;

import java.util.function.IntFunction;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
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

    @SuppressWarnings("deprecation")
    @Mixin(Boat.Type.class)
    public static class BoatType
    {
        @Shadow
        @Mutable
        @Final
        static Boat.Type[] $VALUES;

        @Shadow
        @Mutable
        @Final
        static StringRepresentable.EnumCodec<Boat.Type> CODEC;

        @Shadow
        @Mutable
        @Final
        static IntFunction<Boat.Type> BY_ID;

        @SuppressWarnings({ "unused", "SameParameterValue" })
        @Invoker(value = "<init>")
        private static Boat.Type create(String name, int ordinal, Block planks, String boatName)
        {
            throw new IllegalStateException("Unreachable");
        }

        @Inject(method = "<clinit>", at = @At("TAIL"))
        private static void fishofthieves$clinit(CallbackInfo info)
        {
            var entry = create("FOT_COCONUT", $VALUES.length, Blocks.OAK_PLANKS, "coconut"); // Forge is always weird, so using oak planks as temporary instead.
            $VALUES = ArrayUtils.add($VALUES, entry);

            // Re-initialize
            CODEC = StringRepresentable.fromEnum(() -> $VALUES);
            BY_ID = ByIdMap.continuous(Enum::ordinal, $VALUES, ByIdMap.OutOfBoundsStrategy.ZERO);

            FishOfThieves.LOGGER.info("Added new enum to {}: {}|{}", Boat.Type.class, FOTBoatTypes.COCONUT.name(), FOTBoatTypes.COCONUT);
        }
    }
}