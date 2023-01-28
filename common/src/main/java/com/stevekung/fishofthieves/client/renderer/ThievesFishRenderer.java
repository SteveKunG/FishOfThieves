package com.stevekung.fishofthieves.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.GlowFishLayer;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.AbstractFish;

public abstract class ThievesFishRenderer<V extends FishData, T extends AbstractFish & ThievesFish<V>, M extends EntityModel<T> & HeadphoneModel.Scaleable<T>> extends MobRenderer<T, M>
{
    protected ThievesFishRenderer(EntityRendererProvider.Context context, M entityModel)
    {
        super(context, entityModel, 0.15f);
        this.addLayer(new GlowFishLayer<>(this));
        this.addLayer(new HeadphoneLayer<>(this, context));
    }

    @Override
    public void render(T livingEntity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight)
    {
        this.shadowRadius = livingEntity.isTrophy() ? 0.25f : 0.15f;
        super.render(livingEntity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T livingEntity)
    {
        return livingEntity.getVariant().getTexture();
    }

    @Override
    protected void scale(T livingEntity, PoseStack poseStack, float partialTickTime)
    {
        var scale = livingEntity.isTrophy() ? 1.0F : 0.5F;
        poseStack.scale(scale, scale, scale);
    }
}