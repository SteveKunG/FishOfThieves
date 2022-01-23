package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.PondieModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Pondie;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PondieRenderer extends ThievesFishRenderer<Pondie, PondieModel<Pondie>>
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Pondie.Variant.BY_ID, "pondie");

    public PondieRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PondieModel<>(context.bakeLayer(PondieModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Pondie pondie)
    {
        return TEXTURE_BY_TYPE.get(pondie.getVariant());
    }

    @Override
    protected void setupRotations(Pondie pondie, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(pondie, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = pondie.isPartying() ? -20.0f : 5.0f;
        var bodyRotSpeed = pondie.isPartying() ? pondie.isInWater() ? 2.0f : 1.0f : 0.65f;

        if (!pondie.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!pondie.isInWater())
        {
            poseStack.translate(0.165f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}