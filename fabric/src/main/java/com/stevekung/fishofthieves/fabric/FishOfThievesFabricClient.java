package com.stevekung.fishofthieves.fabric;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.client.FOTDebugScreenEntries;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.network.FOTClientPackets;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;
import com.stevekung.fishofthieves.network.SyncClientShoalFishPacket;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class FishOfThievesFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        FishOfThievesClient.init();
        FOTDebugScreenEntries.init();

        FishOfThievesClient.getBlockColors().forEach(entry -> BlockColorRegistry.register(List.of(entry.blockColor()), entry.blocks()));
        ParticleRenderEvents.ALLOW_TERRAIN_PARTICLE_TINT.register((blockState, level, blockPos) -> !blockState.is(FOTTags.Blocks.MANGO_FRUITS));

        FishOfThievesClient.registerBlockEntityRenderers();

        FishOfThievesClient.getEntityRenderers().forEach(entry -> EntityRenderers.register(entry.entityType(), entry.factory()));

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> FishOfThievesClient.getHeadphone().forEach(entry ->
        {
            if (entityType == entry.entityType())
            {
                registrationHelper.register(new HeadphoneLayer<>(entityRenderer, context.getModelSet(), entry.scaleable()));
            }
        }));

        FishOfThievesClient.getModelLayers().forEach(entry -> ModelLayerRegistry.registerModelLayer(entry.layerLocation(), () -> entry.supplier().get()));

        ClientPlayNetworking.registerGlobalReceiver(ReceiveFishingHookBaitPacket.TYPE, FishOfThievesFabricClient::setFishingHookBait);
        ClientPlayNetworking.registerGlobalReceiver(SyncClientShoalFishPacket.TYPE, FishOfThievesFabricClient::syncClientShoalFish);
    }

    public static void setFishingHookBait(ReceiveFishingHookBaitPacket packet, ClientPlayNetworking.Context context)
    {
        var minecraft = Minecraft.getInstance();
        FOTClientPackets.setFishingHookBait(minecraft, packet.entityId(), packet.itemStack());
    }

    public static void syncClientShoalFish(SyncClientShoalFishPacket packet, ClientPlayNetworking.Context context)
    {
        var minecraft = Minecraft.getInstance();
        FOTClientPackets.syncClientShoalFish(minecraft, packet.entityId(), packet.shoalFishData(), packet.forcedUpdate());
    }
}