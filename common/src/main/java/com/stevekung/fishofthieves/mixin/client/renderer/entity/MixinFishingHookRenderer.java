package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemDisplayContext;

@Mixin(FishingHookRenderer.class)
public abstract class MixinFishingHookRenderer extends EntityRenderer<FishingHook>
{
    @Unique
    private ItemRenderer itemRenderer;

    MixinFishingHookRenderer()
    {
        super(null);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fishofthieves$init(EntityRendererProvider.Context context, CallbackInfo info)
    {
        this.itemRenderer = context.getItemRenderer();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/vertex/PoseStack.popPose()V", ordinal = 0))
    private void fishofthieves$renderAttachedBait(FishingHook entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo info)
    {
        var baitStack = entity.fishofthieves$getBaitStack();

        if (!baitStack.isEmpty())
        {
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(entity.fishofthieves$getBaitStack(), ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
            poseStack.popPose();
        }
    }
}