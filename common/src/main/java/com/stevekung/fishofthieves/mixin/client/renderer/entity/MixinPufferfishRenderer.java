package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.state.PufferfishRenderState;
import net.minecraft.util.Mth;

@Mixin(PufferfishRenderer.class)
public class MixinPufferfishRenderer
{
    @Inject(method = "setupRotations", at = @At("HEAD"))
    private void fishofthieves$setupRotations(PufferfishRenderState renderState, PoseStack poseStack, float bodyRot, float scale, CallbackInfo info)
    {
        if (renderState.isDancing())
        {
            var degree = -20.0f * Mth.sin(2.0f * renderState.ageInTicks);
            poseStack.mulPose(Axis.YP.rotationDegrees(degree));
        }
    }
}