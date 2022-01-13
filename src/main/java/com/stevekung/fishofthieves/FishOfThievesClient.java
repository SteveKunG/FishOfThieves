package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.client.models.SplashtailModel;
import com.stevekung.fishofthieves.client.renderer.SplashtailRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FishOfThievesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.register(FOTEntities.SPLASHTAIL, SplashtailRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SplashtailModel.LAYER, SplashtailModel::createBodyLayer);
    }
}