package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.DevilfishModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Devilfish;
import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DevilfishRenderer extends ThievesFishRenderer<DevilfishVariant, Devilfish, DevilfishModel<Devilfish>>
{
    public DevilfishRenderer(EntityRendererProvider.Context context)
    {
        super(context, new DevilfishModel<>(context.bakeLayer(DevilfishModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Devilfish entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy() ? 0.35f : 0.175f, 0.1f, 0.0f));
    }
}