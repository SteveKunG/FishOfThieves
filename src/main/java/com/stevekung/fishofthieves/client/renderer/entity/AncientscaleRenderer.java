package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.model.AncientscaleModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Ancientscale;

import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AncientscaleRenderer extends ThievesFishRenderer<Ancientscale, AncientscaleModel<Ancientscale>>
{
    private static final Map<Ancientscale.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), hashMap ->
    {
        for (var variant : Ancientscale.Variant.BY_ID)
        {
            hashMap.put(variant, new ResourceLocation(FishOfThieves.MOD_ID, String.format("textures/entity/ancientscale/%s.png", variant.getName())));
        }
    });

    public AncientscaleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AncientscaleModel<>(context.bakeLayer(AncientscaleModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Ancientscale ancientscale)
    {
        return TEXTURE_BY_TYPE.get(ancientscale.getVariant());
    }

    @Override
    protected void setupRotations(Ancientscale ancientscale, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(ancientscale, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotSpeed = 1.0f;

        if (!ancientscale.isInWater())
        {
            bodyRotSpeed = 1.7f;
        }

        var degree = 5.3f * Mth.sin(bodyRotSpeed * 0.65f * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!ancientscale.isInWater())
        {
            poseStack.translate(0.165f, 0.1f, 0.1f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}