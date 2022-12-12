package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class HeadphoneLayer<T extends LivingEntity & PartyFish, M extends EntityModel<T> & HeadphoneModel.Scaleable<T>> extends RenderLayer<T, M>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/headphone.png");
    private final HeadphoneModel<T> model;

    public HeadphoneLayer(RenderLayerParent<T, M> renderLayerParent, EntityRendererProvider.Context context)
    {
        super(renderLayerParent);
        this.model = new HeadphoneModel<>(context.bakeLayer(HeadphoneModel.LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!livingEntity.isInvisible() && (livingEntity.getType() == EntityType.SALMON && livingEntity.hasCustomName() && "Sally".equals(livingEntity.getName().getContents()) || livingEntity.isDancing()))
        {
            poseStack.pushPose();
            this.getParentModel().scale(livingEntity, poseStack);
            this.model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }
    }
}