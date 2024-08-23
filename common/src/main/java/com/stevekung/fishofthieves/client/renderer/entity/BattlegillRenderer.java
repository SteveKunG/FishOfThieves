package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.BattlegillModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BattlegillRenderer<S extends ThievesFishRenderState> extends ThievesFishRenderer<BattlegillVariant, S, Battlegill, BattlegillModel<S>>
{
    public BattlegillRenderer(EntityRendererProvider.Context context)
    {
        super(context, new BattlegillModel<>(context.bakeLayer(BattlegillModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(S entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy ? 0.25f : 0.15f, 0.1f, 0.0f));
    }
}