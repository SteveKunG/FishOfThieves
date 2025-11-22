package com.stevekung.fishofthieves.fabric;

import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.network.FOTClientPackets;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;

public class FishOfThievesFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        FishOfThievesClient.init();

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(), FOTBlocks.FISH_BONE, FOTBlocks.MANGO_LEAVES);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FOTBlocks.OAK_FISH_PLAQUE, FOTBlocks.SPRUCE_FISH_PLAQUE, FOTBlocks.BIRCH_FISH_PLAQUE, FOTBlocks.JUNGLE_FISH_PLAQUE, FOTBlocks.ACACIA_FISH_PLAQUE, FOTBlocks.DARK_OAK_FISH_PLAQUE, FOTBlocks.MANGROVE_FISH_PLAQUE, FOTBlocks.CHERRY_FISH_PLAQUE, FOTBlocks.BAMBOO_FISH_PLAQUE, FOTBlocks.CRIMSON_FISH_PLAQUE, FOTBlocks.WARPED_FISH_PLAQUE, FOTBlocks.COCONUT_FISH_PLAQUE,
                FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.COPPER_FRAME_OAK_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.COPPER_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE,
                FOTBlocks.GILDED_OAK_FISH_PLAQUE, FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, FOTBlocks.GILDED_WARPED_FISH_PLAQUE, FOTBlocks.GILDED_COCONUT_FISH_PLAQUE,
                FOTBlocks.COCONUT_SAPLING, FOTBlocks.COCONUT_FRONDS, FOTBlocks.PINK_PLUMERIA, FOTBlocks.POTTED_PINK_PLUMERIA, FOTBlocks.BANANA_LEAVES, FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.VERTICAL_COCONUT_FRONDS,
                FOTBlocks.COCONUT_TRAPDOOR, FOTBlocks.COCONUT_DOOR, FOTBlocks.BANANA_SHOOTS, FOTBlocks.BANANA_SHOOTS_PLANT, FOTBlocks.BANANA_BLOSSOM, FOTBlocks.BANANA_BLOSSOM_PLANT, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT,
                FOTBlocks.PINEAPPLE_CROP, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlocks.RIPE_PINEAPPLE_BLOCK, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT, FOTBlocks.MANGO_PIT, FOTBlocks.MANGO_SAPLING,
                FOTBlocks.POTTED_BANANA_SHOOTS, FOTBlocks.POTTED_MANGO_PIT, FOTBlocks.POTTED_MANGO_SAPLING, FOTBlocks.POMEGRANATE_PLANT, FOTBlocks.TALL_POMEGRANATE_PLANT, FOTBlocks.POTTED_POMEGRANATE_PLANT, FOTBlocks.POMEGRANATE_SAPLING, FOTBlocks.POTTED_POMEGRANATE_SAPLING,
                FOTBlocks.TROPICAL_RED_FERN, FOTBlocks.POTTED_TROPICAL_RED_FERN, FOTBlocks.TROPICAL_MONSTERA, FOTBlocks.POTTED_TROPICAL_MONSTERA, FOTBlocks.LIGHT_BLUE_PLUMERIA, FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA,
                FOTBlocks.WHITE_PLUMERIA, FOTBlocks.POTTED_WHITE_PLUMERIA, FOTBlocks.GUARDIAN_FRUIT);

        FishOfThievesClient.getBlockColors().forEach(entry -> ColorProviderRegistry.BLOCK.register(entry.blockColor(), entry.blocks()));
        FishOfThievesClient.getItemColors().forEach(entry -> ColorProviderRegistry.ITEM.register(entry.itemColor(), entry.items()));
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

        ClientPlayNetworking.registerGlobalReceiver(FishOfThieves.RECEIVE_FISHING_HOOK_BAIT, FishOfThievesFabricClient::setFishingHookBait);
        ClientPlayNetworking.registerGlobalReceiver(FishOfThieves.STRUCTURE_CENTER_POS_DEBUG, FishOfThievesFabricClient::addDebugStructureCenterPos);
    }

    public static void setFishingHookBait(Minecraft minecraft, ClientPacketListener listener, FriendlyByteBuf buf, PacketSender responseSender)
    {
        var entityId = buf.readVarInt();
        var itemStack = buf.readItem();
        FOTClientPackets.setFishingHookBait(minecraft, entityId, itemStack);
    }

    public static void addDebugStructureCenterPos(Minecraft minecraft, ClientPacketListener listener, FriendlyByteBuf buf, PacketSender responseSender)
    {
        var structurePosList = buf.readCollection(ArrayList::new, buf1 -> Pair.of(buf1.readBlockPos(), buf1.readResourceLocation()));
        FOTClientPackets.addDebugStructureCenterPos(minecraft, structurePosList);
    }
}