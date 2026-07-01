package com.stevekung.fishofthieves.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.WreckerModel;
import com.stevekung.fishofthieves.client.renderer.entity.state.WreckerRenderState;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public class WreckerBulbLayer extends RenderLayer<WreckerRenderState, WreckerModel>
{
    public WreckerBulbLayer(RenderLayerParent<WreckerRenderState, WreckerModel> renderLayerParent)
    {
        super(renderLayerParent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, WreckerRenderState renderState, float yRot, float xRot)
    {
        if (!renderState.isInvisible)
        {
            var color = Mth.clamp(1.0F + Mth.cos(renderState.ageInTicks * 0.05f), 0.25F, 1.0F);
            submitNodeCollector.order(2).submitModel(this.getParentModel(), renderState, poseStack, RenderTypes.eyes(renderState.bulbTexture), packedLight, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(color, 1.0f, 1.0f, 1.0f), null, renderState.outlineColor);
        }
    }
}