package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public class GlowFishLayer<T extends LivingEntity & ThievesFish, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    public GlowFishLayer(RenderLayerParent<T, M> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!livingEntity.isInvisible() && livingEntity.canGlow())
        {
            var vertexConsumer = buffer.getBuffer(RenderType.eyes(livingEntity.getGlowTextureByType().get(livingEntity.getVariant())));
            var color = livingEntity.getGlowBrightness(ageInTicks);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color, color, color, 1.0f);
        }
    }
}