package com.stevekung.fishofthieves.neoforge.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.world.entity.vehicle.Boat;

@Mixin(Boat.class)
public class MixinBoat
{
    @ModifyArg(
            method = "readAdditionalSaveData",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/entity/vehicle/Boat$Type.byName(Ljava/lang/String;)Lnet/minecraft/world/entity/vehicle/Boat$Type;"))
    private String fishofthieves$fixBoatTypeName(String name)
    {
        // Convert old coconut boat type name
        if (name.equals("fishofthieves_coconut"))
        {
            return "fishofthieves:coconut";
        }
        return name;
    }
}