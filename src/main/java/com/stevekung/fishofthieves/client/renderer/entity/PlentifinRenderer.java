package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.PlentifinModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Plentifin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PlentifinRenderer extends ThievesFishRenderer<Plentifin, PlentifinModel<Plentifin>>
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Plentifin.Variant.BY_ID, "plentifin");

    public PlentifinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PlentifinModel<>(context.bakeLayer(PlentifinModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Plentifin plentifin)
    {
        return TEXTURE_BY_TYPE.get(plentifin.getVariant());
    }

    @Override
    protected void setupRotations(Plentifin plentifin, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(plentifin, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = plentifin.isPartying() ? -20.0f : 5.0f;
        var bodyRotSpeed = plentifin.isPartying() ? 2.0f : 0.65f;

        if (!plentifin.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!plentifin.isInWater())
        {
            poseStack.translate(0.15f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}