package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemDisplayContext;

@Mixin(FishingHookRenderer.class)
public abstract class MixinFishingHookRenderer extends EntityRenderer<FishingHook, FishingHookRenderState>
{
    @Unique
    private ItemModelResolver itemModelResolver;

    MixinFishingHookRenderer()
    {
        super(null);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fishofthieves$init(EntityRendererProvider.Context context, CallbackInfo info)
    {
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/vertex/PoseStack.popPose()V", ordinal = 0, shift = At.Shift.AFTER))
    private void fishofthieves$renderAttachedBait(FishingHookRenderState fishingHookRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState, CallbackInfo info)
    {
        var baitStack = fishingHookRenderState.fishofthieves$getBaitStack();

        if (!baitStack.isEmpty())
        {
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0f, -0.5f, 0f);
            poseStack.rotate(cameraRenderState.orientation);
            fishingHookRenderState.fishofthieves$getBaitStack().submit(poseStack, submitNodeCollector, fishingHookRenderState.lightCoords, OverlayTexture.NO_OVERLAY, fishingHookRenderState.outlineColor);
            poseStack.popPose();
        }
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void fishofthieves$extractRenderState(FishingHook fishingHook, FishingHookRenderState fishingHookRenderState, float partialTicks, CallbackInfo info)
    {
        this.itemModelResolver.updateForNonLiving(fishingHookRenderState.fishofthieves$getBaitStack(), fishingHook.fishofthieves$getBaitStack(), ItemDisplayContext.GROUND, fishingHook);
    }
}