package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.PlentifinModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Plentifin;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PlentifinRenderer extends ThievesFishRenderer<PlentifinVariant, Plentifin, PlentifinModel<Plentifin>>
{
    public PlentifinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PlentifinModel<>(context.bakeLayer(PlentifinModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Plentifin entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.fishofthieves$isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;
        var baseDegree = entity.fishofthieves$isDancing() ? -20.0f : 5.0f;
        var xPos = entity.isTrophy() ? 0.265f : 0.135f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(xPos, 0.1f, 0.0f));
    }
}