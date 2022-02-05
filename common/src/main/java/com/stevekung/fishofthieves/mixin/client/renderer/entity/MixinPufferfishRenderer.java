package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Pufferfish;

@Mixin(PufferfishRenderer.class)
public class MixinPufferfishRenderer
{
    @Inject(method = "setupRotations", at = @At("HEAD"))
    private void fishofthieves$setupRotations(Pufferfish pufferfish, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo info)
    {
        if (((PartyFish) pufferfish).isPartying())
        {
            var degree = -20.0f * Mth.sin(2.0f * ageInTicks);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));
        }
    }
}