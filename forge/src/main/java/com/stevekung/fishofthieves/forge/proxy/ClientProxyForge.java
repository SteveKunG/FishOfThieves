package com.stevekung.fishofthieves.forge.proxy;

import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxyForge
{
    public void init()
    {
        var context = FMLJavaModLoadingContext.get();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> AutoConfig.getConfigScreen(FishOfThievesConfig.class, screen).get()));
        MinecraftForge.EVENT_BUS.register(this);
        context.getModEventBus().addListener(this::clientSetup);
        context.getModEventBus().addListener(this::registerRenderers);
        context.getModEventBus().addListener(this::registerLayerDefinitions);
        context.getModEventBus().addListener(this::registerLayers);
        context.getModEventBus().addListener(this::registerBlockColors);
        context.getModEventBus().addListener(this::registerItemColors);
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
        event.register((blockState, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), FOTBlocks.MANGO_LEAVES);
        event.register((blockState, level, pos, tintIndex) -> level != null && pos != null && tintIndex == 1 ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), FOTBlocks.MANGO_FRUIT, FOTBlocks.HANGING_MANGO_FRUIT);
    }

    private void registerItemColors(RegisterColorHandlersEvent.Item event)
    {
        event.register((itemStack, tintIndex) -> FoliageColor.getDefaultColor(), FOTBlocks.MANGO_LEAVES);
    }

    private static <M extends EntityModel<LivingEntity>> void addHeadphoneLayer(EntityRenderersEvent.AddLayers event, EntityType<? extends LivingEntity> entityType, HeadphoneModel.Scaleable<LivingEntity> scaleable)
    {
        LivingEntityRenderer<LivingEntity, M> renderer = event.getRenderer(entityType);

        if (renderer != null)
        {
            renderer.addLayer(new HeadphoneLayer<>(renderer, event.getEntityModels(), scaleable));
        }
    }
}