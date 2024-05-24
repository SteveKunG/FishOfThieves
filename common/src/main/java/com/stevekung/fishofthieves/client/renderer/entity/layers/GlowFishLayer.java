package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.variant.muha.AbstractFishVariant;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.VariantHolder;

public class GlowFishLayer<V extends AbstractFishVariant, T extends LivingEntity & ThievesFish & VariantHolder<Holder<V>>, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    public GlowFishLayer(RenderLayerParent<T, M> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        var glowTexture = livingEntity.getVariant().value().glowTexture();

        if (!livingEntity.isInvisible() && glowTexture.isPresent())
        {
            var vertexConsumer = buffer.getBuffer(RenderType.eyes(glowTexture.get()));
            var color = livingEntity.getGlowBrightness(ageInTicks);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color, color, color, 1.0f);
        }
    }
}