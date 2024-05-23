package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.IslehopperModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Islehopper;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class IslehopperRenderer extends ThievesFishRenderer<IslehopperVariant, Islehopper, IslehopperModel<Islehopper>>
{
    public IslehopperRenderer(EntityRendererProvider.Context context)
    {
        super(context, new IslehopperModel<>(context.bakeLayer(IslehopperModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Islehopper entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy() ? 0.235f : 0.115f, 0.1f, 0.0f));
    }

    @Override
    protected void setupRotations(Islehopper entity, PoseStack poseStack, float bob, float rotationYaw, float partialTicks, float scale)
    {
        super.setupRotations(entity, poseStack, bob, rotationYaw, partialTicks, scale);
        poseStack.translate(0.0f, Mth.cos(bob * 0.1f) * 0.01f, 0.0f);
    }
}