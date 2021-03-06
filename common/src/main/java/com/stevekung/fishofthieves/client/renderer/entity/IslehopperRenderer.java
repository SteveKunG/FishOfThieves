package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.IslehopperModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.animal.Islehopper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class IslehopperRenderer extends ThievesFishRenderer<Islehopper, IslehopperModel<Islehopper>>
{
    private static final Map<FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Islehopper.Variant.BY_ID, "islehopper");

    public IslehopperRenderer(EntityRendererProvider.Context context)
    {
        super(context, new IslehopperModel<>(context.bakeLayer(IslehopperModel.LAYER)));
    }

    @Override
    protected Map<FishVariant, ResourceLocation> getTextureMap()
    {
        return TEXTURE_BY_TYPE;
    }

    @Override
    protected void setupRotations(Islehopper islehopper, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(islehopper, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = islehopper.isPartying() ? -20.0f : 4.0f;
        var bodyRotSpeed = islehopper.isPartying() ? islehopper.isInWater() ? 2.0f : 1.0f : 0.6f;

        if (!islehopper.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));
        poseStack.translate(0.0f, Mth.cos(ageInTicks * 0.1f) * 0.01f, 0.0f);

        if (!islehopper.isInWater())
        {
            poseStack.translate(0.15f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}