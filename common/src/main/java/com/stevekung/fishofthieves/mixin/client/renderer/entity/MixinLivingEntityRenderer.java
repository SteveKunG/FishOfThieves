package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer
{
    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.hasPose(Lnet/minecraft/world/entity/Pose;)Z"),
            index = 9, ordinal = 4)
    private float injectx(float xRot, @Local(argsOnly = true) LivingEntity livingEntity)
    {
        var shakeStrength = 0.8f;
        var shakeRatio = 5.25d;
        xRot += (float)(Math.cos((double)livingEntity.tickCount * shakeRatio) * Math.PI * shakeStrength);
        return xRot;
    }

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.hasPose(Lnet/minecraft/world/entity/Pose;)Z"),
            index = 10, ordinal = 5)
    private float injecty(float yRot, @Local(argsOnly = true) LivingEntity livingEntity)
    {
//        yRot += (float)(Math.cos((double)livingEntity.tickCount * 3.25) * Math.PI * 0.4F);
        return yRot;
    }
}