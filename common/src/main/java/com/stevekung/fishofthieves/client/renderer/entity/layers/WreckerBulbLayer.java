package com.stevekung.fishofthieves.client.renderer.entity.layers;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.variant.WreckerVariants;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WreckerBulbLayer<T extends Wrecker, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    private static final Function<Holder<WreckerVariant>, ResourceLocation> BULB_BY_VARIANT = variant -> FishOfThieves.id("textures/entity/wrecker/%s_bulb.png".formatted(variant.unwrapKey().orElse(WreckerVariants.ROSE).location().getPath()));

    public WreckerBulbLayer(RenderLayerParent<T, M> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!livingEntity.isInvisible())
        {
            var vertexConsumer = buffer.getBuffer(RenderType.eyes(BULB_BY_VARIANT.apply(livingEntity.getVariant())));
            var color = Mth.clamp(1.0F + Mth.cos(ageInTicks * 0.05f), 0.25F, 1.0F);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color, color, color, 1.0f);
        }
    }
}