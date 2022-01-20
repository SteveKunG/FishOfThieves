package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.PlentifinModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Plentifin;

import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PlentifinRenderer extends ThievesFishRenderer<Plentifin, PlentifinModel<Plentifin>>
{
    private static final Map<Plentifin.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), hashMap ->
    {
        for (var variant : Plentifin.Variant.BY_ID)
        {
            hashMap.put(variant, new ResourceLocation(FishOfThieves.MOD_ID, String.format("textures/entity/plentifin/%s.png", variant.getName())));
        }
    });

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
        var bodyRotSpeed = 1.0f;

        if (!plentifin.isInWater())
        {
            bodyRotSpeed = 1.7f;
        }

        var degree = 5.3f * Mth.sin(bodyRotSpeed * 0.65f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!plentifin.isInWater())
        {
            poseStack.translate(0.15f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}