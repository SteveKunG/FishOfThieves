package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.registry.FOTEntities;

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
        EntityRendererRegistry.register(FOTEntities.PLENTIFIN, PlentifinRenderer::new);
        EntityRendererRegistry.register(FOTEntities.WILDSPLASH, WildsplashRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SplashtailModel.LAYER, SplashtailModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PondieModel.LAYER, PondieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(IslehopperModel.LAYER, IslehopperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AncientscaleModel.LAYER, AncientscaleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PlentifinModel.LAYER, PlentifinModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WildsplashModel.LAYER, WildsplashModel::createBodyLayer);
    }
}