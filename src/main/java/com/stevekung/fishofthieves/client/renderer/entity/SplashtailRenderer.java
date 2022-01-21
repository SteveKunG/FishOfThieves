package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.SplashtailModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Splashtail;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SplashtailRenderer extends ThievesFishRenderer<Splashtail, SplashtailModel<Splashtail>>
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Splashtail.Variant.BY_ID, "splashtail");

    public SplashtailRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SplashtailModel<>(context.bakeLayer(SplashtailModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Splashtail splashtail)
    {
        return TEXTURE_BY_TYPE.get(splashtail.getVariant());
    }

    @Override
    protected void setupRotations(Splashtail splashtail, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(splashtail, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotSpeed = 1.0f;

        if (!splashtail.isInWater())
        {
            bodyRotSpeed = 1.7f;
        }

        var degree = 4.3f * Mth.sin(bodyRotSpeed * 0.6f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!splashtail.isInWater())
        {
            poseStack.translate(0.15f, 0.1f, -0.1f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}