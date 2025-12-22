package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.SplashtailModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Splashtail;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SplashtailRenderer extends ThievesFishRenderer<SplashtailVariant, Splashtail, SplashtailModel<Splashtail>>
{
    public SplashtailRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SplashtailModel<>(context.bakeLayer(SplashtailModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Splashtail entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.fishofthieves$isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = entity.fishofthieves$isDancing() ? -20.0f : 4.0f;
        var xPos = entity.isTreasured() ? 0.35f : entity.isTrophy() ? 0.275f : 0.135f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(xPos, 0.1f, -0.1f));
    }
}