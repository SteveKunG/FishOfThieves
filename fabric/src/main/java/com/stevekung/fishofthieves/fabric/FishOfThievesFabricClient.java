package com.stevekung.fishofthieves.fabric;

import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.blockentity.FishPlaqueRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.entity.EntityType;

public class FishOfThievesFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        BlockRenderLayerMap.INSTANCE.putBlock(FOTBlocks.FISH_BONE, RenderType.cutoutMipped());
        //@formatter:off
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE,
                FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE);
        //@formatter:on

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

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            if (entityType == EntityType.COD)
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), HeadphoneModel.Scaleable.COD));
            }
            else if (entityType == EntityType.SALMON)
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), HeadphoneModel.Scaleable.SALMON));
            }
            else if (entityType == EntityType.PUFFERFISH)
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), HeadphoneModel.Scaleable.PUFFERFISH));
            }
            else if (entityType == EntityType.TROPICAL_FISH)
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), HeadphoneModel.Scaleable.TROPICAL_FISH));
            }
            else if (entityType == EntityType.TADPOLE)
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), HeadphoneModel.Scaleable.TADPOLE));
            }
        });

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