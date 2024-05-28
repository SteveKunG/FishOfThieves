package com.stevekung.fishofthieves.common.client.renderer.entity;

import com.stevekung.fishofthieves.common.client.model.WildsplashModel;
import com.stevekung.fishofthieves.common.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.common.entity.animal.Wildsplash;
import com.stevekung.fishofthieves.common.entity.variant.WildsplashVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WildsplashRenderer extends ThievesFishRenderer<WildsplashVariant, Wildsplash, WildsplashModel<Wildsplash>>
{
    public WildsplashRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WildsplashModel<>(context.bakeLayer(WildsplashModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Wildsplash entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.8f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.7f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy() ? 0.275f : 0.15f, 0.1f, 0.05f));
    }
}