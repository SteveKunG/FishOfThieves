package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.AncientscaleModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Ancientscale;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class AncientscaleRenderer extends ThievesFishRenderer<AncientscaleVariant, Ancientscale, AncientscaleModel<Ancientscale>>
{
    public AncientscaleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AncientscaleModel<>(context.bakeLayer(AncientscaleModel.LAYER)));
    }

    @Override
    protected void setupRotations(Ancientscale entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var bodyRotBase = 1.0f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;

        if (!inWater)
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!inWater)
        {
            poseStack.translate(0.165f, 0.1f, 0.1f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}