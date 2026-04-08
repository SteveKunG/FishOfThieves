package com.stevekung.fishofthieves.neoforge;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.neoforge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.neoforge.proxy.ClientProxyNeoForge;
import com.stevekung.fishofthieves.neoforge.proxy.CommonProxyNeoForge;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;
import com.stevekung.fishofthieves.network.RequestServerShoalFishPacket;
import com.stevekung.fishofthieves.network.SyncClientShoalFishPacket;
import com.stevekung.fishofthieves.registry.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(FishOfThieves.MOD_ID)
public class FishOfThievesNeoForge
{
    private static final String PROTOCOL_VERSION = "1";
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, FishOfThieves.MOD_ID);

    public FishOfThievesNeoForge()
    {
        var modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modEventBus.register(this);
        modEventBus.addListener(this::commonSetup);
        ENTITY_DATA_SERIALIZERS.register(modEventBus);

        FOTGrassColorModifier.TROPICAL_ISLAND = Enum.valueOf(BiomeSpecialEffects.GrassColorModifier.class, "FISHOFTHIEVES_TROPICAL_ISLAND");

        if (FMLEnvironment.getDist().isClient())
        {
            new ClientProxyNeoForge().init();
            FishOfThievesClient.init();
        }
        new CommonProxyNeoForge().init();
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        FishOfThieves.initCommon();

        if (ModList.get().isLoaded("aquaculture"))
        {
            Aquaculture2.init();
        }
    }

    @SubscribeEvent
    public void registerRegistries(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariant.DIRECT_CODEC, SplashtailVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.PONDIE_VARIANT, PondieVariant.DIRECT_CODEC, PondieVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariant.DIRECT_CODEC, IslehopperVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariant.DIRECT_CODEC, AncientscaleVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariant.DIRECT_CODEC, PlentifinVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariant.DIRECT_CODEC, WildsplashVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariant.DIRECT_CODEC, DevilfishVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariant.DIRECT_CODEC, BattlegillVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.WRECKER_VARIANT, WreckerVariant.DIRECT_CODEC, WreckerVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.STORMFISH_VARIANT, StormfishVariant.DIRECT_CODEC, StormfishVariant.NETWORK_CODEC);
        event.dataPackRegistry(FOTRegistries.FISH_PLAQUE_INTERACTION, FishPlaqueInteraction.DIRECT_CODEC, FishPlaqueInteraction.DIRECT_CODEC);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event)
    {
        event.register(Registries.BLOCK, helper -> FOTBlocks.init());
        event.register(Registries.ITEM, helper -> FOTItems.init());
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> FOTBlockEntityTypes.init());
        event.register(Registries.ENTITY_TYPE, helper -> FOTEntities.init());
        event.register(Registries.SOUND_EVENT, helper -> FOTSoundEvents.init());
        event.register(Registries.FEATURE, helper -> FOTFeatures.init());
        event.register(Registries.SENSOR_TYPE, helper -> FOTSensorTypes.init());
        event.register(Registries.MEMORY_MODULE_TYPE, helper -> FOTMemoryModuleTypes.init());
        event.register(Registries.STRUCTURE_TYPE, helper -> FOTStructures.init());
        event.register(Registries.LOOT_CONDITION_TYPE, helper -> FOTLootItemConditions.init());
        event.register(Registries.LOOT_POOL_ENTRY_TYPE, helper -> FOTLootPoolEntries.init());
        event.register(Registries.CREATIVE_MODE_TAB, helper -> FOTCreativeTabs.init());
        event.register(Registries.TRIGGER_TYPE, helper -> FOTCriteriaTriggers.init());
        event.register(Registries.ENTITY_SUB_PREDICATE_TYPE, helper -> FOTEntitySubPredicates.init());
        event.register(Registries.DATA_COMPONENT_PREDICATE_TYPE, helper -> FOTDataComponentPredicates.init());
        event.register(Registries.BLOCK_PREDICATE_TYPE, helper -> FOTBlockPredicateTypes.init());
        event.register(Registries.TREE_DECORATOR_TYPE, helper -> FOTTreeDecoratorTypes.init());
        event.register(Registries.FOLIAGE_PLACER_TYPE, helper -> FOTFoliagePlacerTypes.init());
        event.register(Registries.BLOCK_STATE_PROVIDER_TYPE, helper -> FOTBlockStateProviderTypes.init());
        event.register(Registries.TRUNK_PLACER_TYPE, helper -> FOTTrunkPlacerTypes.init());
        event.register(Registries.DECORATED_POT_PATTERN, helper -> FOTDecoratedPotPatterns.putItemsToPotTexture());
        event.register(Registries.MOB_EFFECT, helper -> FOTMobEffects.init());
        event.register(Registries.SPAWN_CONDITION_TYPE, helper -> FOTSpawnConditions.init());
        event.register(Registries.DATA_COMPONENT_TYPE, helper -> FOTDataComponentTypes.init());
        event.register(Registries.PLACEMENT_MODIFIER_TYPE, helper -> FOTPlacementModifiers.init());
        event.register(Registries.MATERIAL_CONDITION, helper -> FOTSurfaceRuleConditionSources.init());
        event.register(Registries.DEBUG_SUBSCRIPTION, helper -> FOTDebugSubscriptions.init());
        event.register(Registries.POINT_OF_INTEREST_TYPE, helper -> FOTPoiTypes.init());
        event.register(Registries.MAP_DECORATION_TYPE, helper -> FOTMapDecorationTypes.init());
        event.register(Registries.LOOT_FUNCTION_TYPE, helper -> FOTLootItemFunctions.init());
        event.register(Registries.ENVIRONMENT_ATTRIBUTE, helper -> FOTEnvironmentAttributes.init());
        event.register(Registries.GAME_RULE, helper -> FOTGameRules.init());
        event.register(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, helper -> FOTDataSerializers.init());
    }

    @SubscribeEvent
    public void register(RegisterPayloadHandlersEvent event)
    {
        var registrar = event.registrar(FishOfThieves.MOD_ID).versioned(PROTOCOL_VERSION).optional();
        registrar.playToClient(ReceiveFishingHookBaitPacket.TYPE, ReceiveFishingHookBaitPacket.CODEC, ReceiveFishingHookBaitPacketNeoForge::handle);
        registrar.playToServer(RequestServerShoalFishPacket.TYPE, RequestServerShoalFishPacket.CODEC, RequestServerShoalFishPacketNeoForge::handle);
        registrar.playToClient(SyncClientShoalFishPacket.TYPE, SyncClientShoalFishPacket.CODEC, SyncClientShoalFishPacketNeoForge::handle);
    }

    @SuppressWarnings("unused")
    public static Object getTropicalIslandGrassColorParameters(int idx, Class<?> type)
    {
        return type.cast(switch (idx)
        {
            case 0 -> "fishofthieves:tropical_island";
            case 1 ->
                    (BiomeSpecialEffects.GrassColorModifier.ColorModifier) (x, z, baseColor) -> FOTGrassColorModifier.getGrassColor(x, z);
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }
}