package com.stevekung.fishofthieves.forge.proxy;

import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.blockentity.FishPlaqueRenderer;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.PartyFish;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import com.stevekung.fishofthieves.registry.FOTEntities;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxyForge extends CommonProxyForge
{
    @Override
    public void init()
    {
        super.init();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> AutoConfig.getConfigScreen(FishOfThievesConfig.class, screen).get()));
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLayerDefinitions);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLayers);
    }

    @Override
    public void commonSetup(FMLCommonSetupEvent event)
    {
        super.commonSetup(event);
    }

    @Override
    public void clientSetup(FMLClientSetupEvent event)
    {
        super.clientSetup(event);
        BlockEntityRenderers.register(FOTBlockEntityTypes.FISH_PLAQUE, FishPlaqueRenderer::new);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(FOTEntities.SPLASHTAIL, SplashtailRenderer::new);
        event.registerEntityRenderer(FOTEntities.PONDIE, PondieRenderer::new);
        event.registerEntityRenderer(FOTEntities.ISLEHOPPER, IslehopperRenderer::new);
        event.registerEntityRenderer(FOTEntities.ANCIENTSCALE, AncientscaleRenderer::new);
        event.registerEntityRenderer(FOTEntities.PLENTIFIN, PlentifinRenderer::new);
        event.registerEntityRenderer(FOTEntities.WILDSPLASH, WildsplashRenderer::new);
        event.registerEntityRenderer(FOTEntities.DEVILFISH, DevilfishRenderer::new);
        event.registerEntityRenderer(FOTEntities.BATTLEGILL, BattlegillRenderer::new);
        event.registerEntityRenderer(FOTEntities.WRECKER, WreckerRenderer::new);
        event.registerEntityRenderer(FOTEntities.STORMFISH, StormfishRenderer::new);
    }

    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(SplashtailModel.LAYER, SplashtailModel::createBodyLayer);
        event.registerLayerDefinition(PondieModel.LAYER, PondieModel::createBodyLayer);
        event.registerLayerDefinition(IslehopperModel.LAYER, IslehopperModel::createBodyLayer);
        event.registerLayerDefinition(AncientscaleModel.LAYER, AncientscaleModel::createBodyLayer);
        event.registerLayerDefinition(PlentifinModel.LAYER, PlentifinModel::createBodyLayer);
        event.registerLayerDefinition(WildsplashModel.LAYER, WildsplashModel::createBodyLayer);
        event.registerLayerDefinition(DevilfishModel.LAYER, DevilfishModel::createBodyLayer);
        event.registerLayerDefinition(BattlegillModel.LAYER, BattlegillModel::createBodyLayer);
        event.registerLayerDefinition(WreckerModel.LAYER, WreckerModel::createBodyLayer);
        event.registerLayerDefinition(StormfishModel.LAYER, StormfishModel::createBodyLayer);

        event.registerLayerDefinition(HeadphoneModel.LAYER, HeadphoneModel::createBodyLayer);
    }

    private void registerLayers(EntityRenderersEvent.AddLayers event)
    {
        addHeadphoneLayer(event, EntityType.COD, HeadphoneModel.Scaleable.COD);
        addHeadphoneLayer(event, EntityType.SALMON, HeadphoneModel.Scaleable.SALMON);
        addHeadphoneLayer(event, EntityType.PUFFERFISH, HeadphoneModel.Scaleable.PUFFERFISH);
        addHeadphoneLayer(event, EntityType.TROPICAL_FISH, HeadphoneModel.Scaleable.TROPICAL_FISH);
        addHeadphoneLayer(event, EntityType.TADPOLE, HeadphoneModel.Scaleable.TADPOLE);
    }

    private static <E extends LivingEntity & PartyFish, M extends EntityModel<E>> void addHeadphoneLayer(EntityRenderersEvent.AddLayers event, EntityType<E> entityType, HeadphoneModel.Scaleable<E> scaleable)
    {
        LivingEntityRenderer<E, M> renderer = event.getEntityRenderer(entityType);

        if (renderer != null)
        {
            renderer.addLayer(new HeadphoneLayer<>(renderer, event.getEntityModels(), scaleable));
        }
    }
}