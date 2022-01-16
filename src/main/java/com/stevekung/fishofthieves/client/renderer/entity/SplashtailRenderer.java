package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.SplashtailModel;
import com.stevekung.fishofthieves.entity.animal.Splashtail;

import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SplashtailRenderer extends ThievesFishRenderer<Splashtail, SplashtailModel<Splashtail>>
{
    private static final Map<Splashtail.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), hashMap ->
    {
        for (var variant : Splashtail.Variant.BY_ID)
        {
            hashMap.put(variant, new ResourceLocation(FishOfThieves.MOD_ID, String.format("textures/entity/splashtail/%s.png", variant.getName())));
        }
    });

    public SplashtailRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SplashtailModel<>(context.bakeLayer(SplashtailModel.LAYER)), 0.3F);
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
        var f = 4.3f * Mth.sin(0.6f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(f));

        if (!splashtail.isInWater())
        {
            poseStack.translate(0.1f, 0.1f, -0.1f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    protected void scale(Splashtail splashtail, PoseStack poseStack, float partialTickTime)
    {
        var scale = splashtail.isTrophy() ? 1.0F : 0.5F;
        poseStack.scale(scale, scale, scale);
    }
}