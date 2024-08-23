package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.IslehopperModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;
import com.stevekung.fishofthieves.entity.animal.Islehopper;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class IslehopperRenderer<S extends ThievesFishRenderState> extends ThievesFishRenderer<IslehopperVariant, S, Islehopper, IslehopperModel<S>>
{
    public IslehopperRenderer(EntityRendererProvider.Context context)
    {
        super(context, new IslehopperModel<>(context.bakeLayer(IslehopperModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(S entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy ? 0.235f : 0.115f, 0.1f, 0.0f));
    }

    @Override
    protected void setupRotations(S renderState, PoseStack poseStack, float bodyRot, float scale)
    {
        super.setupRotations(renderState, poseStack, bodyRot, scale);
        poseStack.translate(0.0f, Mth.cos(renderState.ageInTicks * 0.1f) * 0.01f, 0.0f);
    }
}