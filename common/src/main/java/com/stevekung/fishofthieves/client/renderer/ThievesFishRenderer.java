package com.stevekung.fishofthieves.client.renderer;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.GlowFishLayer;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.AbstractFish;

public abstract class ThievesFishRenderer<V extends AbstractFishVariant, S extends ThievesFishRenderState, T extends AbstractFish & ThievesFish<V> & VariantHolder<Holder<V>>, M extends EntityModel<S> & HeadphoneModel.Scaleable<S>> extends MobRenderer<T, S, M>
{
    protected ThievesFishRenderer(EntityRendererProvider.Context context, M entityModel)
    {
        super(context, entityModel, 0.15f);
        this.addLayer(new GlowFishLayer<>(this));
        this.addLayer(new HeadphoneLayer<>(this, context.getModelSet(), entityModel));
    }

    @Override
    public void render(S renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        this.shadowRadius = renderState.isTrophy ? 0.25f : 0.15f;
        super.render(renderState, poseStack, buffer, packedLight);
    }

    @SuppressWarnings("unchecked")
    @Override
    public S createRenderState()
    {
        return (S) new ThievesFishRenderState();
    }

    @Override
    public void extractRenderState(T entity, S renderState, float partialTicks)
    {
        var variant = entity.getVariant().value();
        renderState.isTrophy = entity.isTrophy();
        renderState.isNoFlip = entity.isNoFlip();
        renderState.fullTexture = variant.fullTexture();
        variant.fullGlowTexture().ifPresent(resourceLocation -> renderState.fullGlowTexture = resourceLocation);
        renderState.glowBrightness = entity.getGlowBrightness(renderState.ageInTicks);
    }

    @Override
    protected void setupRotations(S renderState, PoseStack poseStack, float bodyRot, float scale)
    {
        super.setupRotations(renderState, poseStack, bodyRot, scale);
        var inWater = renderState.isInWater || renderState.isNoFlip;
        var rotationRenderData = this.setupRotations(renderState, inWater);
        var degree = rotationRenderData.baseDegree * Mth.sin(rotationRenderData.bodyRotBase * rotationRenderData.bodyRotSpeed * renderState.ageInTicks);//TODO Test
        poseStack.mulPose(Axis.YP.rotationDegrees(degree));

        if (!inWater)
        {
            rotationRenderData.translateConsumer.accept(poseStack);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(S renderState)
    {
        return renderState.fullTexture;
    }

    @Override
    protected void scale(S livingEntity, PoseStack poseStack)
    {
        var scale = livingEntity.isTrophy ? 1.0F : 0.5F;
        poseStack.scale(scale, scale, scale);
    }

    public abstract RotationRenderData setupRotations(S renderState, boolean inWater);

    public record RotationRenderData(float bodyRotBase, float bodyRotSpeed, float baseDegree, Consumer<PoseStack> translateConsumer)
    {
        public static RotationRenderData create(float bodyRotBase, float bodyRotSpeed, float baseDegree, Consumer<PoseStack> translateConsumer)
        {
            return new RotationRenderData(bodyRotBase, bodyRotSpeed, baseDegree, translateConsumer);
        }
    }
}