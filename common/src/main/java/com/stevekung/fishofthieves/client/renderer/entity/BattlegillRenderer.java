package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.BattlegillModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BattlegillRenderer extends ThievesFishRenderer<Battlegill, BattlegillModel<Battlegill>>
{
    private static final Map<FishVariant, ResourceLocation> TEXTURE_BY_TYPE = ThievesFishRenderer.createTextureByType(Battlegill.Variant.BY_ID, "battlegill");

    public BattlegillRenderer(EntityRendererProvider.Context context)
    {
        super(context, new BattlegillModel<>(context.bakeLayer(BattlegillModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Battlegill battlegill)
    {
        return TEXTURE_BY_TYPE.get(battlegill.getVariant());
    }

    @Override
    protected void setupRotations(Battlegill battlegill, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(battlegill, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = battlegill.isPartying() ? -20.0f : 4.0f;
        var bodyRotSpeed = battlegill.isPartying() ? battlegill.isInWater() ? 2.0f : 1.0f : 0.6f;

        if (!battlegill.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!battlegill.isInWater())
        {
            poseStack.translate(0.175f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}