package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTBoatTypes;

import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

@Mixin(BoatRenderer.class)
public class MixinBoatRenderer
{
    @WrapOperation(method = "method_32163(ZLnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/world/entity/vehicle/Boat$Type;)Lcom/mojang/datafixers/util/Pair;",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/resources/ResourceLocation"))
    private ResourceLocation fishofthieves$getTextureLocation(String location, Operation<ResourceLocation> operation, @Local(argsOnly = true) Boat.Type type)
    {
        if (type == FOTBoatTypes.COCONUT)
        {
            return FishOfThieves.id(location);
        }
        return operation.call(location);
    }
}