package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.StormfishModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.state.ThievesFishRenderState;
import com.stevekung.fishofthieves.entity.animal.Stormfish;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StormfishRenderer<S extends ThievesFishRenderState> extends ThievesFishRenderer<StormfishVariant, S, Stormfish, StormfishModel<S>>
{
    public StormfishRenderer(EntityRendererProvider.Context context)
    {
        super(context, new StormfishModel<>(context.bakeLayer(StormfishModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(S entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy ? 0.35f : 0.2f, 0.1f, 0.0f));
    }
}