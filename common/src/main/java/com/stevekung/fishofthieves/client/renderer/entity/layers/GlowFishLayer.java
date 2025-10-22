package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;

public class GlowFishLayer<S extends ThievesFishRenderState, M extends EntityModel<S>> extends RenderLayer<S, M>
{
    public GlowFishLayer(RenderLayerParent<S, M> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, float yRot, float xRot)
    {
        var glowTexture = renderState.fullGlowTexture;

        if (!renderState.isInvisible && glowTexture != null)
        {
            var color = renderState.glowBrightness;
            submitNodeCollector.submitModel(this.getParentModel(), renderState, poseStack, RenderTypes.eyes(glowTexture), 15728640, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1.0f, color, color, color), null);
        }
    }
}