package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.WildsplashModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.animal.Wildsplash;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WildsplashRenderer extends ThievesFishRenderer<Wildsplash, WildsplashModel<Wildsplash>>
{
    private static final Map<FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Wildsplash.Variant.BY_ID, "wildsplash");

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
        var bodyRotBase = 1.0f;
        var baseDegree = wildsplash.isPartying() ? -20.0f : 5.0f;
        var bodyRotSpeed = wildsplash.isPartying() ? wildsplash.isInWater() ? 2.0f : 1.0f : 0.7f;

        if (!wildsplash.isInWater())
        {
            bodyRotBase = 1.8f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!wildsplash.isInWater())
        {
            poseStack.translate(0.165f, 0.1f, 0.05f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}