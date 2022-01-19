package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.client.model.AncientscaleModel;
import com.stevekung.fishofthieves.client.model.IslehopperModel;
import com.stevekung.fishofthieves.client.model.PondieModel;
import com.stevekung.fishofthieves.client.model.SplashtailModel;
import com.stevekung.fishofthieves.client.renderer.entity.AncientscaleRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.IslehopperRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.PondieRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.SplashtailRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FishOfThievesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.register(FOTEntities.SPLASHTAIL, SplashtailRenderer::new);
        EntityRendererRegistry.register(FOTEntities.PONDIE, PondieRenderer::new);
        EntityRendererRegistry.register(FOTEntities.ISLEHOPPER, IslehopperRenderer::new);
        EntityRendererRegistry.register(FOTEntities.ANCIENTSCALE, AncientscaleRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SplashtailModel.LAYER, SplashtailModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PondieModel.LAYER, PondieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(IslehopperModel.LAYER, IslehopperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AncientscaleModel.LAYER, AncientscaleModel::createBodyLayer);
    }
}