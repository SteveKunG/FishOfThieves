package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;

public class GlowFishLayer<S extends ThievesFishRenderState, M extends EntityModel<S>> extends RenderLayer<S, M>
{
    public GlowFishLayer(RenderLayerParent<S, M> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S renderState, float yRot, float xRot)
    {
        var glowTexture = renderState.fullGlowTexture;

        if (!renderState.isInvisible && glowTexture != null)
        {
            var vertexConsumer = buffer.getBuffer(RenderType.eyes(glowTexture));
            var color = renderState.glowBrightness;
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(color, 1.0f, 1.0f, 1.0f));
        }
    }
}