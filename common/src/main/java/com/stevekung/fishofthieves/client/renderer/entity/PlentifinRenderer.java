package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.client.model.PlentifinModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Plentifin;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class PlentifinRenderer extends ThievesFishRenderer<PlentifinVariant, Plentifin, PlentifinModel<Plentifin>>
{
    public PlentifinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PlentifinModel<>(context.bakeLayer(PlentifinModel.LAYER)));
    }

    @Override
    protected void setupRotations(Plentifin entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;
        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(degree));

        if (!inWater)
        {
            poseStack.translate(entity.isTrophy() ? 0.265f : 0.135f, 0.1f, 0.0f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f));
        }
    }
}