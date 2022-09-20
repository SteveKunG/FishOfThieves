package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.DevilfishModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Devilfish;
import com.stevekung.fishofthieves.registry.variants.DevilfishVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class DevilfishRenderer extends ThievesFishRenderer<DevilfishVariant, Devilfish, DevilfishModel<Devilfish>>
{
    public DevilfishRenderer(EntityRendererProvider.Context context)
    {
        super(context, new DevilfishModel<>(context.bakeLayer(DevilfishModel.LAYER)));
    }

    @Override
    protected void setupRotations(Devilfish entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var bodyRotBase = 1.0f;
        var baseDegree = entity.isPartying() ? -20.0f : 4.0f;
        var bodyRotSpeed = entity.isPartying() ? inWater ? 2.0f : 1.0f : 0.6f;

        if (!inWater)
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!inWater)
        {
            poseStack.translate(0.175f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}