package com.stevekung.fishofthieves.common.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.common.entity.PartyFish;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class HeadphoneLayer<T extends LivingEntity & PartyFish, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    private static final ResourceLocation TEXTURE = FishOfThieves.id("textures/entity/headphone.png");
    private final HeadphoneModel<T> model;
    private final HeadphoneModel.Scaleable<T> scaleable;

    @SuppressWarnings("unchecked")
    public HeadphoneLayer(LivingEntityRenderer<?, ?> renderLayerParent, EntityModelSet entityModelSet, HeadphoneModel.Scaleable<T> scaleable)
    {
        super((RenderLayerParent<T, M>) renderLayerParent);
        this.model = new HeadphoneModel<>(entityModelSet.bakeLayer(HeadphoneModel.LAYER));
        this.scaleable = scaleable;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!livingEntity.isInvisible() && (livingEntity.getType() == EntityType.SALMON && livingEntity.hasCustomName() && ChatFormatting.stripFormatting(livingEntity.getName().getString()).equals("Sally") || livingEntity.isDancing()))
        {
            poseStack.pushPose();
            this.scaleable.scale(livingEntity, poseStack);
            this.model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }
    }
}