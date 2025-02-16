package com.stevekung.fishofthieves.mixin.effect;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTMobEffects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.phys.Vec3;

@Mixin(MobEffectUtil.class)
public class MixinMobEffectUtil
{
    @Inject(method = "method_42145(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;DLnet/minecraft/world/effect/MobEffect;Lnet/minecraft/world/effect/MobEffectInstance;ILnet/minecraft/server/level/ServerPlayer;)Z",
            cancellable = true,
            at = @At("HEAD"))
    private static void fishofthieves$preventMiningFatigueFromElderGuardian(Entity source, Vec3 vec3, double radius, MobEffect mobEffect, MobEffectInstance mobEffectInstance, int duration, ServerPlayer serverPlayer, CallbackInfoReturnable<Boolean> info)
    {
        if (source instanceof ElderGuardian && serverPlayer.hasEffect(FOTMobEffects.GUARDIAN_STIFLE))
        {
            info.setReturnValue(false);
        }
    }
}