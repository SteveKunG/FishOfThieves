package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.PlentifinModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;
import com.stevekung.fishofthieves.entity.animal.Plentifin;
import com.stevekung.fishofthieves.entity.variant.PlentifinVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PlentifinRenderer<S extends ThievesFishRenderState> extends ThievesFishRenderer<PlentifinVariant, S, Plentifin, PlentifinModel<S>>
{
    public PlentifinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PlentifinModel<>(context.bakeLayer(PlentifinModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(S entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy ? 0.265f : 0.135f, 0.1f, 0.0f));
    }
}