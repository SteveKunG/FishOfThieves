package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.WildsplashModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Wildsplash;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WildsplashRenderer extends ThievesFishRenderer<Wildsplash, WildsplashModel<Wildsplash>>
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Wildsplash.Variant.BY_ID, "wildsplash");

    public WildsplashRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WildsplashModel<>(context.bakeLayer(WildsplashModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Wildsplash wildsplash)
    {
        return TEXTURE_BY_TYPE.get(wildsplash.getVariant());
    }

    @Override
    protected void setupRotations(Wildsplash wildsplash, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(wildsplash, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotSpeed = 1.0f;

        if (!wildsplash.isInWater())
        {
            bodyRotSpeed = 1.8f;
        }

        var degree = 5.3f * Mth.sin(bodyRotSpeed * 0.7f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!wildsplash.isInWater())
        {
            poseStack.translate(0.165f, 0.1f, 0.05f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}