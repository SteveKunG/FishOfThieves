package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.AncientscaleModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Ancientscale;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AncientscaleRenderer extends ThievesFishRenderer<AncientscaleVariant, Ancientscale, AncientscaleModel<Ancientscale>>
{
    public AncientscaleRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AncientscaleModel<>(context.bakeLayer(AncientscaleModel.LAYER)));
    }

    @Override
    public RotationRenderData setupRotations(Ancientscale entity, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.65f;
        var baseDegree = entity.isDancing() ? -20.0f : 5.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(entity.isTrophy() ? 0.285f : 0.165f, 0.1f, 0.1f));
    }
}