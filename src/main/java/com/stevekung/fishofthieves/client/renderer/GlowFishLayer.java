package com.stevekung.fishofthieves.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.client.models.SplashtailModel;
import com.stevekung.fishofthieves.entity.Splashtail;
import com.stevekung.fishofthieves.entity.Splashtail.Variant;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class GlowFishLayer<T extends Splashtail> extends EyesLayer<T, SplashtailModel<T>>
{
    private static final RenderType GLOW = RenderType.eyes(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/splashtail/seafoam_glow.png"));

    public GlowFishLayer(RenderLayerParent<T, SplashtailModel<T>> renderLayerParent)
    {
        super(renderLayerParent);
    }

    //TODO Better glowing render system
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (livingEntity.getVariant() == Variant.SEAFOAM)
        {
            super.render(poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public RenderType renderType()
    {
        return GLOW;
    }
}