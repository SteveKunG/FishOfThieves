package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.PondieModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Pondie;

import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PondieRenderer extends ThievesFishRenderer<Pondie, PondieModel<Pondie>>
{
    private static final Map<Pondie.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), hashMap ->
    {
        for (var variant : Pondie.Variant.BY_ID)
        {
            hashMap.put(variant, new ResourceLocation(FishOfThieves.MOD_ID, String.format("textures/entity/pondie/%s.png", variant.getName())));
        }
    });

    public PondieRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PondieModel<>(context.bakeLayer(PondieModel.LAYER)), 0.3F);
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
        var bodyRotSpeed = 1.0f;

        if (!pondie.isInWater())
        {
            bodyRotSpeed = 1.7f;
        }

        var degree = 5.3f * Mth.sin(bodyRotSpeed * 0.65f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!pondie.isInWater())
        {
            poseStack.translate(0.165f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    protected void scale(Pondie pondie, PoseStack poseStack, float partialTickTime)
    {
        var scale = pondie.isTrophy() ? 1.0F : 0.5F;
        poseStack.scale(scale, scale, scale);
    }
}