package com.stevekung.fishofthieves.fabric.core;

import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.blockentity.FishPlaqueRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FishOfThievesFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        BlockRenderLayerMap.INSTANCE.putBlock(FOTBlocks.FISH_BONE, RenderType.cutoutMipped());

        BlockEntityRenderers.register(FOTBlockEntityTypes.FISH_PLAQUE, FishPlaqueRenderer::new);

        EntityRendererRegistry.register(FOTEntities.SPLASHTAIL, SplashtailRenderer::new);
        EntityRendererRegistry.register(FOTEntities.PONDIE, PondieRenderer::new);
        EntityRendererRegistry.register(FOTEntities.ISLEHOPPER, IslehopperRenderer::new);
        EntityRendererRegistry.register(FOTEntities.ANCIENTSCALE, AncientscaleRenderer::new);
        EntityRendererRegistry.register(FOTEntities.PLENTIFIN, PlentifinRenderer::new);
        EntityRendererRegistry.register(FOTEntities.WILDSPLASH, WildsplashRenderer::new);
        EntityRendererRegistry.register(FOTEntities.DEVILFISH, DevilfishRenderer::new);
        EntityRendererRegistry.register(FOTEntities.BATTLEGILL, BattlegillRenderer::new);
        EntityRendererRegistry.register(FOTEntities.WRECKER, WreckerRenderer::new);
        EntityRendererRegistry.register(FOTEntities.STORMFISH, StormfishRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SplashtailModel.LAYER, SplashtailModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PondieModel.LAYER, PondieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(IslehopperModel.LAYER, IslehopperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AncientscaleModel.LAYER, AncientscaleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PlentifinModel.LAYER, PlentifinModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WildsplashModel.LAYER, WildsplashModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(DevilfishModel.LAYER, DevilfishModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BattlegillModel.LAYER, BattlegillModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WreckerModel.LAYER, WreckerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(StormfishModel.LAYER, StormfishModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(HeadphoneModel.LAYER, HeadphoneModel::createBodyLayer);
    }
}