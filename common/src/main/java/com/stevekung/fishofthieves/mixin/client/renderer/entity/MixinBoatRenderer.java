package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;

import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

@Mixin(BoatRenderer.class)
public class MixinBoatRenderer
{
    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/vehicle/Boat$Type;Z)Lnet/minecraft/resources/ResourceLocation;", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$getTextureLocation(Boat.Type type, boolean chestBoat, CallbackInfoReturnable<ResourceLocation> info)
    {
        if (type == FOTBoatTypes.COCONUT)
        {
            info.setReturnValue(FishOfThieves.id("textures/entity/boat/" + type.getName() + ".png"));
        }
    }
}