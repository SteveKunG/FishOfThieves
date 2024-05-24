package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.SplashtailModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Splashtail;
import com.stevekung.fishofthieves.registry.variant.muha.SplashtailVariant;
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
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy() ? 0.275f : 0.135f, 0.1f, -0.1f));
    }
}