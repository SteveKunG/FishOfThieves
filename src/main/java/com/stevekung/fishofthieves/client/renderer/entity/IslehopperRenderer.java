package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.IslehopperModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Islehopper;

import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class IslehopperRenderer extends ThievesFishRenderer<Islehopper, IslehopperModel<Islehopper>>
{
    private static final Map<Islehopper.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), hashMap ->
    {
        for (var variant : Islehopper.Variant.BY_ID)
        {
            hashMap.put(variant, new ResourceLocation(FishOfThieves.MOD_ID, String.format("textures/entity/islehopper/%s.png", variant.getName())));
        }
    });

    public IslehopperRenderer(EntityRendererProvider.Context context)
    {
        super(context, new IslehopperModel<>(context.bakeLayer(IslehopperModel.LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(Islehopper islehopper)
    {
        return TEXTURE_BY_TYPE.get(islehopper.getVariant());
    }

    @Override
    protected void setupRotations(Islehopper islehopper, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(islehopper, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotSpeed = 1.0f;

        if (!islehopper.isInWater())
        {
            bodyRotSpeed = 1.7f;
        }

        var degree = 4.3f * Mth.sin(bodyRotSpeed * 0.6f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));
        poseStack.translate(0.0f, Mth.cos(ageInTicks * 0.1f) * 0.01f, 0.0f);

        if (!islehopper.isInWater())
        {
            poseStack.translate(0.15f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}