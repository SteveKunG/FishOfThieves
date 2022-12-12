package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.client.model.WildsplashModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Wildsplash;
import com.stevekung.fishofthieves.registry.variants.WildsplashVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class WildsplashRenderer extends ThievesFishRenderer<WildsplashVariant, Wildsplash, WildsplashModel<Wildsplash>>
{
    public WildsplashRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WildsplashModel<>(context.bakeLayer(WildsplashModel.LAYER)));
    }

    @Override
    protected void setupRotations(Wildsplash entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var bodyRotBase = 1.0f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.7f;

        if (!inWater)
        {
            bodyRotBase = 1.8f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(degree));

        if (!inWater)
        {
            poseStack.translate(0.165f, 0.1f, 0.05f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f));
        }
    }
}