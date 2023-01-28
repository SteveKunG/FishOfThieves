package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
    protected void setupRotations(Islehopper entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(degree));
        poseStack.translate(0.0f, Mth.cos(ageInTicks * 0.1f) * 0.01f, 0.0f);

        if (!inWater)
        {
            poseStack.translate(entity.isTrophy() ? 0.235f : 0.115f, 0.1f, 0.0f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f));
        }
    }
}