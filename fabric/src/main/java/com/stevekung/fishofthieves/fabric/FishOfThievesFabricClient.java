package com.stevekung.fishofthieves.fabric;

import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.client.FOTDecoratedPotPatternsClient;
import com.stevekung.fishofthieves.client.FOTModelLayers;
import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.blockentity.FishPlaqueRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.FoliageColor;

public class FishOfThievesFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        FishOfThievesClient.init();
        FOTDecoratedPotPatternsClient.init();

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(), FOTBlocks.FISH_BONE, FOTBlocks.MANGO_LEAVES);
        //@formatter:off
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.GILDED_COCONUT_FISH_PLAQUE,
                FOTBlocks.COCONUT_SAPLING, FOTBlocks.COCONUT_FRONDS, FOTBlocks.PINK_PLUMERIA, FOTBlocks.POTTED_PINK_PLUMERIA, FOTBlocks.BANANA_LEAVES, FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.VERTICAL_COCONUT_FRONDS,
                FOTBlocks.COCONUT_TRAPDOOR, FOTBlocks.COCONUT_DOOR, FOTBlocks.BANANA_SHOOTS, FOTBlocks.BANANA_SHOOTS_PLANT, FOTBlocks.BANANA_BLOSSOM, FOTBlocks.BANANA_BLOSSOM_PLANT, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlocks.PINEAPPLE_CROP, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlocks.RIPE_PINEAPPLE_BLOCK, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT, FOTBlocks.MANGO_PIT, FOTBlocks.MANGO_SAPLING,
                FOTBlocks.POTTED_BANANA_SHOOTS, FOTBlocks.POTTED_MANGO_PIT, FOTBlocks.POTTED_MANGO_SAPLING, FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT, FOTBlocks.POTTED_POMEGRANATE_PLANT, FOTBlocks.POMEGRANATE_SAPLING, FOTBlocks.POTTED_POMEGRANATE_SAPLING,
                FOTBlocks.TROPICAL_RED_FERN, FOTBlocks.POTTED_TROPICAL_RED_FERN, FOTBlocks.TROPICAL_MONSTERA, FOTBlocks.POTTED_TROPICAL_MONSTERA, FOTBlocks.LIGHT_BLUE_PLUMERIA, FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA,
                FOTBlocks.WHITE_PLUMERIA, FOTBlocks.POTTED_WHITE_PLUMERIA, FOTBlocks.GUARDIAN_FRUIT);
        //@formatter:on

        ColorProviderRegistry.BLOCK.register((blockState, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.FOLIAGE_DEFAULT, FOTBlocks.MANGO_LEAVES);
        ColorProviderRegistry.BLOCK.register((blockState, level, pos, tintIndex) -> level != null && pos != null && tintIndex == 1 ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.FOLIAGE_DEFAULT, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT);
        ParticleRenderEvents.ALLOW_BLOCK_DUST_TINT.register((blockState, level, blockPos) -> !blockState.is(FOTTags.Blocks.MANGO_FRUITS));

        BlockEntityRenderers.register(FOTBlockEntityTypes.FISH_PLAQUE, FishPlaqueRenderer::new);
        BlockEntityRenderers.register(FOTBlockEntityTypes.SIGN, SignRenderer::new);
        BlockEntityRenderers.register(FOTBlockEntityTypes.HANGING_SIGN, HangingSignRenderer::new);

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

        EntityRendererRegistry.register(FOTEntities.COCONUT_BOAT, context -> new BoatRenderer(context, FOTModelLayers.COCONUT_BOAT));
        EntityRendererRegistry.register(FOTEntities.COCONUT_CHEST_BOAT, context -> new BoatRenderer(context, FOTModelLayers.COCONUT_CHEST_BOAT));

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

        EntityModelLayerRegistry.registerModelLayer(FOTModelLayers.COCONUT_BOAT, BoatModel::createBoatModel);
        EntityModelLayerRegistry.registerModelLayer(FOTModelLayers.COCONUT_CHEST_BOAT, BoatModel::createChestBoatModel);

        EntityModelLayerRegistry.registerModelLayer(HeadphoneModel.LAYER, HeadphoneModel::createBodyLayer);
    }
}