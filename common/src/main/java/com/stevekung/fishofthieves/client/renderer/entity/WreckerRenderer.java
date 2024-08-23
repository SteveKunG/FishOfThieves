package com.stevekung.fishofthieves.client.renderer.entity;

import com.stevekung.fishofthieves.client.model.WreckerModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.layers.WreckerBulbLayer;
import com.stevekung.fishofthieves.client.renderer.entity.state.WreckerRenderState;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WreckerRenderer extends ThievesFishRenderer<WreckerVariant, WreckerRenderState, Wrecker, WreckerModel>
{
    public WreckerRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WreckerModel(context.bakeLayer(WreckerModel.LAYER)));
        this.addLayer(new WreckerBulbLayer(this));
    }

    @Override
    public RotationRenderData setupRotations(WreckerRenderState renderState, boolean inWater)
    {
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var bodyRotSpeed = renderState.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var baseDegree = renderState.isDancing() ? -20.0f : 4.0f;
        return RotationRenderData.create(bodyRotBase, bodyRotSpeed, baseDegree, poseStack -> poseStack.translate(renderState.isTrophy ? 0.275f : 0.15f, 0.1f, 0.0f));
    }

    @Override
    public void extractRenderState(Wrecker entity, WreckerRenderState renderState, float partialTicks)
    {
        super.extractRenderState(entity, renderState, partialTicks);
        renderState.bulbTexture = WreckerRenderState.BULB_BY_VARIANT.apply(entity.getVariant());
    }
}