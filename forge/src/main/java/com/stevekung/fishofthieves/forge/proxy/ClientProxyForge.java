package com.stevekung.fishofthieves.forge.proxy;

import com.stevekung.fishofthieves.client.model.*;
import com.stevekung.fishofthieves.client.renderer.entity.*;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.forge.mixin.MobBucketItemAccessor;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

public class ClientProxyForge extends CommonProxyForge
{
    @Override
    public void init()
    {
        super.init();
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, screen) -> AutoConfig.getConfigScreen(FishOfThievesConfig.class, screen).get()));
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLayerDefinitions);
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
    }

    @SubscribeEvent
    public void addItemTooltip(ItemTooltipEvent event)
    {
        var itemStack = event.getItemStack();
        var compoundTag = itemStack.getTag();
        var insertAt = event.getToolTip().size();

        if (event.getFlags().isAdvanced())
        {
            insertAt -= 2;
        }

        if (itemStack.getItem() instanceof MobBucketItemAccessor mobBucketItem)
        {
            if (mobBucketItem.invokeGetFishType().is(FOTTags.THIEVES_FISH_ENTITY_TYPE) && itemStack.hasTag() && compoundTag.contains(ThievesFish.VARIANT_TAG, Tag.TAG_INT))
            {
                var type = new TranslatableComponent("entity.fishofthieves.%s.%s".formatted(ForgeRegistries.ENTITIES.getKey(mobBucketItem.invokeGetFishType()).getPath(), compoundTag.getString(ThievesFish.NAME_TAG))).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);

                if (compoundTag.getBoolean(ThievesFish.TROPHY_TAG))
                {
                    type.append(", ").append(new TranslatableComponent("entity.fishofthieves.trophy"));
                }
                event.getToolTip().add(insertAt, type);
            }
        }
    }

    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
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

    public void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
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
}