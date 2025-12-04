package com.stevekung.fishofthieves.mixin.entity;

import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FishOfThieves;
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

    @Mixin(Boat.Type.class)
    public static class BoatType
    {
        @Shadow
        @Mutable
        @Final
        static Boat.Type[] $VALUES;

        @SuppressWarnings({ "unused", "SameParameterValue" })
        @Invoker(value = "<init>")
        private static Boat.Type fishofthieves$create(String name, int ordinal, Block planks, String boatName)
        {
            throw new IllegalStateException("Unreachable");
        }

        @Inject(method = "<clinit>", at = @At(
                value = "FIELD",
                target = "net/minecraft/world/entity/vehicle/Boat$Type.$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;",
                shift = At.Shift.AFTER,
                opcode = Opcodes.PUTSTATIC,
                ordinal = 0
        ))
        private static void fishofthieves$clinit(CallbackInfo info)
        {
            var entry = fishofthieves$create("FISHOFTHIEVES_COCONUT", $VALUES.length, FOTBlocks.COCONUT_PLANKS, "fishofthieves_coconut");
            $VALUES = ArrayUtils.add($VALUES, entry);

            FishOfThieves.LOGGER.info("Added new enum to {}: {}|{}", Boat.Type.class, FOTBoatTypes.COCONUT.name(), FOTBoatTypes.COCONUT);
        }
    }
}