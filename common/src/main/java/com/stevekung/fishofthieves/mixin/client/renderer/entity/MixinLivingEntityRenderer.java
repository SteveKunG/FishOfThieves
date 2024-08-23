package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.entity.PartyFish;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer
{
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void fishofthieves$extractRenderState(LivingEntity livingEntity, LivingEntityRenderState renderState, float partialTicks, CallbackInfo info)
    {
        if (livingEntity instanceof PartyFish partyFish)
        {
            renderState.setDancing(partyFish.isDancing());
        }
        renderState.setSalmon(livingEntity.getType() == EntityType.SALMON);
    }
}