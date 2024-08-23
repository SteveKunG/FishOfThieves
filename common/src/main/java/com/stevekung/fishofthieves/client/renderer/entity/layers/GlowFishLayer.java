package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.VariantHolder;

public class GlowFishLayer<V extends AbstractFishVariant, S extends ThievesFishRenderState, T extends LivingEntity & ThievesFish<V> & VariantHolder<Holder<V>>, M extends EntityModel<S>> extends RenderLayer<S, M>
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
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1.0f, color, color, color));
        }
    }
}