package com.stevekung.fishofthieves.fabric;

import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.client.FOTDecoratedPotPatternsClient;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.network.FOTClientPackets;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;
import com.stevekung.fishofthieves.network.StructureCenterPosDebugPacket;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.FoliageColor;

public class FishOfThievesFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        FishOfThievesClient.init();
        FOTDecoratedPotPatternsClient.init();

        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT_MIPPED, FOTBlocks.FISH_BONE, FOTBlocks.MANGO_LEAVES);
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.GILDED_COCONUT_FISH_PLAQUE,
                FOTBlocks.COCONUT_SAPLING, FOTBlocks.COCONUT_FRONDS, FOTBlocks.PINK_PLUMERIA, FOTBlocks.POTTED_PINK_PLUMERIA, FOTBlocks.BANANA_LEAVES, FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.VERTICAL_COCONUT_FRONDS,
                FOTBlocks.COCONUT_TRAPDOOR, FOTBlocks.COCONUT_DOOR, FOTBlocks.BANANA_SHOOTS, FOTBlocks.BANANA_SHOOTS_PLANT, FOTBlocks.BANANA_BLOSSOM, FOTBlocks.BANANA_BLOSSOM_PLANT, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlocks.PINEAPPLE_CROP, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlocks.RIPE_PINEAPPLE_BLOCK, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT, FOTBlocks.MANGO_PIT, FOTBlocks.MANGO_SAPLING,
                FOTBlocks.POTTED_BANANA_SHOOTS, FOTBlocks.POTTED_MANGO_PIT, FOTBlocks.POTTED_MANGO_SAPLING, FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT, FOTBlocks.POTTED_POMEGRANATE_PLANT, FOTBlocks.POMEGRANATE_SAPLING, FOTBlocks.POTTED_POMEGRANATE_SAPLING,
                FOTBlocks.TROPICAL_RED_FERN, FOTBlocks.POTTED_TROPICAL_RED_FERN, FOTBlocks.TROPICAL_MONSTERA, FOTBlocks.POTTED_TROPICAL_MONSTERA, FOTBlocks.LIGHT_BLUE_PLUMERIA, FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA,
                FOTBlocks.WHITE_PLUMERIA, FOTBlocks.POTTED_WHITE_PLUMERIA, FOTBlocks.GUARDIAN_FRUIT);

        ColorProviderRegistry.BLOCK.register((blockState, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.FOLIAGE_DEFAULT, FOTBlocks.MANGO_LEAVES);
        ColorProviderRegistry.BLOCK.register((blockState, level, pos, tintIndex) -> level != null && pos != null && tintIndex == 1 ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.FOLIAGE_DEFAULT, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT);
        ParticleRenderEvents.ALLOW_BLOCK_DUST_TINT.register((blockState, level, blockPos) -> !blockState.is(FOTTags.Blocks.MANGO_FRUITS));

        FishOfThievesClient.registerBlockEntityRenderers();

        FishOfThievesClient.getEntityRenderers().forEach(entry -> EntityRendererRegistry.register(entry.entityType(), entry.factory()));

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> FishOfThievesClient.getHeadphone().forEach(entry ->
        {
            if (entityType == entry.entityType())
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), entry.scaleable()));
            }
        }));

        FishOfThievesClient.getModelLayers().forEach(entry -> EntityModelLayerRegistry.registerModelLayer(entry.layerLocation(), () -> entry.supplier().get()));

        ClientPlayNetworking.registerGlobalReceiver(ReceiveFishingHookBaitPacket.TYPE, FishOfThievesFabricClient::setFishingHookBait);
        ClientPlayNetworking.registerGlobalReceiver(StructureCenterPosDebugPacket.TYPE, FishOfThievesFabricClient::addDebugStructureCenterPos);
    }

    public static void setFishingHookBait(ReceiveFishingHookBaitPacket packet, ClientPlayNetworking.Context context)
    {
        var minecraft = Minecraft.getInstance();
        FOTClientPackets.setFishingHookBait(minecraft, packet.entityId(), packet.itemStack());
    }

    public static void addDebugStructureCenterPos(StructureCenterPosDebugPacket packet, ClientPlayNetworking.Context context)
    {
        var minecraft = Minecraft.getInstance();
        FOTClientPackets.addDebugStructureCenterPos(minecraft, packet.structurePosList());
    }
}