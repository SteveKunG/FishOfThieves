package com.stevekung.fishofthieves.forge.mixin.entity;

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

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@Mixin(Boat.Type.class)
public class MixinBoatType
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

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            target = "net/minecraft/world/entity/vehicle/Boat$Type.$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;",
            shift = At.Shift.AFTER,
            opcode = Opcodes.PUTSTATIC,
            ordinal = 0
    ))
    private static void fishofthieves$clinit(CallbackInfo info)
    {
        var entry = fishofthieves$create("FISHOFTHIEVES_COCONUT", $VALUES.length, Blocks.OAK_PLANKS, "fishofthieves_coconut");
        $VALUES = ArrayUtils.add($VALUES, entry);

        FishOfThieves.LOGGER.info("Added new enum to {}: {}|{}", Boat.Type.class, FOTBoatTypes.COCONUT.name(), FOTBoatTypes.COCONUT);
    }
}