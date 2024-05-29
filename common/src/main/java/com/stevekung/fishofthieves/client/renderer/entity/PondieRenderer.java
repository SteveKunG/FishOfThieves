package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.PondieModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Pondie;
import com.stevekung.fishofthieves.entity.variant.PondieVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PondieRenderer extends ThievesFishRenderer<PondieVariant, Pondie, PondieModel<Pondie>>
{
    public PondieRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PondieModel<>(context.bakeLayer(PondieModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Pondie entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy() ? 0.275f : 0.135f, 0.1f, 0.0f));
    }
}