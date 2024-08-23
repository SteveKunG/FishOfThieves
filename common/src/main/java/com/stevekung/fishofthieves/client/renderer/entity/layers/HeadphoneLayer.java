package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.entity.PartyFish;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;

public class HeadphoneLayer<T extends LivingEntity & PartyFish, S extends LivingEntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M>
{
    private static final ResourceLocation TEXTURE = FishOfThieves.id("textures/entity/headphone.png");
    private final HeadphoneModel<S> model;
    private final HeadphoneModel.Scaleable<S> scaleable;

    @SuppressWarnings("unchecked")
    public HeadphoneLayer(LivingEntityRenderer<?, ?, ?> renderLayerParent, EntityModelSet entityModelSet, HeadphoneModel.Scaleable<S> scaleable)
    {
        super((LivingEntityRenderer<T, S, M>) renderLayerParent);
        this.model = new HeadphoneModel<>(entityModelSet.bakeLayer(HeadphoneModel.LAYER));
        this.scaleable = scaleable;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S renderState, float yRot, float xRot)
    {
        if (!renderState.isInvisible && (renderState.isSalmon() && renderState.customName != null && ChatFormatting.stripFormatting(renderState.customName.getString()).equals("Sally") || renderState.isDancing()))
        {
            poseStack.pushPose();
            this.scaleable.scale(renderState, poseStack);
            this.model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, ARGB.color(255, 255, 255));
            poseStack.popPose();
        }
    }
}