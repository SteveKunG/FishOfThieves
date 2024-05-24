package com.stevekung.fishofthieves.client.renderer;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.GlowFishLayer;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.variant.AbstractFishVariant;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.AbstractFish;

public abstract class ThievesFishRenderer<V extends AbstractFishVariant, T extends AbstractFish & ThievesFish & VariantHolder<Holder<V>>, M extends EntityModel<T> & HeadphoneModel.Scaleable<T>> extends MobRenderer<T, M>
{
    protected ThievesFishRenderer(EntityRendererProvider.Context context, M entityModel)
    {
        super(context, entityModel, 0.15f);
        this.addLayer(new GlowFishLayer<>(this));
        this.addLayer(new HeadphoneLayer<>(this, context.getModelSet(), entityModel));
    }

    @Override
    public void render(T livingEntity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight)
    {
        this.shadowRadius = livingEntity.isTrophy() ? 0.25f : 0.15f;
        super.render(livingEntity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    protected void setupRotations(T entity, PoseStack poseStack, float bob, float rotationYaw, float partialTicks, float scale)
    {
        super.setupRotations(entity, poseStack, bob, rotationYaw, partialTicks, scale);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var rotationRenderData = this.setupRotations(entity, inWater);
        var degree = rotationRenderData.baseDegree * Mth.sin(rotationRenderData.bodyRotBase * rotationRenderData.bodyRotSpeed * bob);
        poseStack.mulPose(Axis.YP.rotationDegrees(degree));

        if (!inWater)
        {
            rotationRenderData.translateConsumer.accept(poseStack);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T livingEntity)
    {
        return livingEntity.getVariant().value().texture();
    }

    @Override
    protected void scale(T livingEntity, PoseStack poseStack, float partialTickTime)
    {
        var scale = livingEntity.isTrophy() ? 1.0F : 0.5F;
        poseStack.scale(scale, scale, scale);
    }

    public abstract RotationRenderData setupRotations(T entity, boolean inWater);

    public record RotationRenderData(float bodyRotBase, float bodyRotSpeed, float baseDegree, Consumer<PoseStack> translateConsumer)
    {
        public static RotationRenderData create(float bodyRotBase, float bodyRotSpeed, float baseDegree, Consumer<PoseStack> translateConsumer)
        {
            return new RotationRenderData(bodyRotBase, bodyRotSpeed, baseDegree, translateConsumer);
        }
    }
}