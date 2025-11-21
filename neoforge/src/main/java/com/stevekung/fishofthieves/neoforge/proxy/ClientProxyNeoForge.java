package com.stevekung.fishofthieves.neoforge.proxy;

import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import me.shedaniel.autoconfig.AutoConfig;

public class ClientProxyNeoForge
{
    public void init()
    {
        var eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, screen) -> AutoConfig.getConfigScreen(FishOfThievesConfig.class, screen).get());
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::registerRenderers);
        eventBus.addListener(this::registerLayerDefinitions);
        eventBus.addListener(this::registerLayers);
        eventBus.addListener(this::registerBlockColors);
        eventBus.addListener(this::registerClientExtensions);
        NeoForge.EVENT_BUS.addListener(this::onFogChange);
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
        FishOfThievesClient.registerBlockEntityRenderers();
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        FishOfThievesClient.getEntityRenderers().forEach(entry -> event.registerEntityRenderer(entry.entityType(), entry.factory()));
    }

    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        FishOfThievesClient.getModelLayers().forEach(entry -> event.registerLayerDefinition(entry.layerLocation(), entry.supplier()));
    }

    private void registerLayers(EntityRenderersEvent.AddLayers event)
    {
        FishOfThievesClient.getHeadphone().forEach(entry -> addHeadphoneLayer(event, entry.entityType(), entry.scaleable()));
    }

    private void registerBlockColors(RegisterColorHandlersEvent.Block event)
    {
        FishOfThievesClient.getBlockColors().forEach(entry -> event.register(entry.blockColor(), entry.blocks()));
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerBlock(new IClientBlockExtensions()
        {
            @Override
            public boolean areBreakingParticlesTinted(BlockState state, ClientLevel level, BlockPos pos)
            {
                return false;
            }
        }, FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT);
    }

    private void onFogChange(ViewportEvent.RenderFog event)
    {
        if (event.getType() == FogType.WATER && event.getCamera().getEntity() instanceof LocalPlayer localPlayer)
        {
            var holder = localPlayer.level().getBiome(localPlayer.blockPosition());

            if (holder.is(FOTBiomes.TROPICAL_ISLAND))
            {
                event.setFarPlaneDistance(192.0F);
                event.setCanceled(true);
            }
        }
    }

    private static <E extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<S>> void addHeadphoneLayer(EntityRenderersEvent.AddLayers event, EntityType<E> entityType, HeadphoneModel.Scaleable<S> scaleable)
    {
        LivingEntityRenderer<E, S, M> renderer = event.getRenderer(entityType);

        if (renderer != null)
        {
            renderer.addLayer(new HeadphoneLayer<>(renderer, event.getEntityModels(), scaleable));
        }
    }
}