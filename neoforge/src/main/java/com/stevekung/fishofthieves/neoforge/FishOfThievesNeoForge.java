package com.stevekung.fishofthieves.neoforge;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.neoforge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.neoforge.level.FOTBiomeModifiers;
import com.stevekung.fishofthieves.neoforge.level.FOTStructureModifiers;
import com.stevekung.fishofthieves.neoforge.proxy.ClientProxyNeoForge;
import com.stevekung.fishofthieves.neoforge.proxy.CommonProxyNeoForge;
import com.stevekung.fishofthieves.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.registries.*;

@Mod(FishOfThieves.MOD_ID)
public class FishOfThievesNeoForge
{
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(BuiltInRegistries.BLOCK, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, FishOfThieves.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishOfThieves.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, FishOfThieves.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, FishOfThieves.MOD_ID);
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(BuiltInRegistries.SENSOR_TYPE, FishOfThieves.MOD_ID);
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, FishOfThieves.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, FishOfThieves.MOD_ID);

    private static final String THIEVES_FISH_SPAWNS_IN_STRUCTURE = "thieves_fish_spawns_in_structure";
    public static final ResourceLocation ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL = FishOfThieves.res(THIEVES_FISH_SPAWNS_IN_STRUCTURE);
    private static final String ADD_FISH_BONE = "add_fish_bone";
    public static final ResourceLocation ADD_FISH_BONE_RL = FishOfThieves.res(ADD_FISH_BONE);

    static
    {
        FOTBuiltInRegistries.SPAWN_CONDITION_TYPE = new RegistryBuilder<>(FOTRegistries.SPAWN_CONDITION_TYPE).sync(true).maxId(64).create();
    }

    public FishOfThievesNeoForge()
    {
        var modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modEventBus.register(this);
        modEventBus.addListener(this::commonSetup);
        BLOCK.register(modEventBus);
        ITEM.register(modEventBus);
        BLOCK_ENTITY_TYPE.register(modEventBus);
        ENTITY.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        FEATURES.register(modEventBus);
        SENSOR_TYPES.register(modEventBus);
        MEMORY_MODULE_TYPES.register(modEventBus);
        STRUCTURE_MODIFIERS.register(modEventBus);
        ENTITY_DATA_SERIALIZERS.register(modEventBus);

        FishOfThieves.initGlobal();

        modEventBus.addListener(FOTBiomeModifiers::generateBiomeModifiers);
        STRUCTURE_MODIFIERS.register(THIEVES_FISH_SPAWNS_IN_STRUCTURE, FOTStructureModifiers.Modifier::makeCodec);
        modEventBus.addListener(FOTStructureModifiers::generateStructureModifiers);

        if (FMLEnvironment.dist.isClient())
        {
            new ClientProxyNeoForge().init();
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
    public void registerRegistries(NewRegistryEvent event)
    {
        event.register(FOTBuiltInRegistries.SPAWN_CONDITION_TYPE);
    }

    @SubscribeEvent
    public void registerRegistries(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariant.DIRECT_CODEC, SplashtailVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.PONDIE_VARIANT, PondieVariant.DIRECT_CODEC, PondieVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariant.DIRECT_CODEC, IslehopperVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariant.DIRECT_CODEC, AncientscaleVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariant.DIRECT_CODEC, PlentifinVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariant.DIRECT_CODEC, WildsplashVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariant.DIRECT_CODEC, DevilfishVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariant.DIRECT_CODEC, BattlegillVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.WRECKER_VARIANT, WreckerVariant.DIRECT_CODEC, WreckerVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.STORMFISH_VARIANT, StormfishVariant.DIRECT_CODEC, StormfishVariant.DIRECT_CODEC);
        event.dataPackRegistry(FOTRegistries.FISH_PLAQUE_INTERACTION, FishPlaqueInteraction.DIRECT_CODEC, FishPlaqueInteraction.DIRECT_CODEC);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event)
    {
        event.register(Registries.BLOCK, helper -> FOTBlocks.init());
        event.register(Registries.ITEM, helper -> FOTItems.init());
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> FOTBlockEntityTypes.init());
        event.register(Registries.ENTITY_TYPE, helper -> FOTEntities.init());
        event.register(Registries.FEATURE, helper -> FOTFeatures.init());
        event.register(Registries.SENSOR_TYPE, helper -> FOTSensorTypes.init());
        event.register(Registries.MEMORY_MODULE_TYPE, helper -> FOTMemoryModuleTypes.init());
        event.register(Registries.STRUCTURE_TYPE, helper -> FOTStructures.init());
        event.register(Registries.LOOT_CONDITION_TYPE, helper -> FOTLootItemConditions.init());
        event.register(Registries.LOOT_POOL_ENTRY_TYPE, helper -> FOTLootPoolEntries.init());
        event.register(Registries.CREATIVE_MODE_TAB, helper -> Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT, FishOfThieves.getCreativeTabBuilder(CreativeModeTab.builder()).build()));
        event.register(Registries.TRIGGER_TYPE, helper -> FOTCriteriaTriggers.init());
        event.register(Registries.ENTITY_SUB_PREDICATE_TYPE, helper -> FOTEntitySubPredicate.init());
        event.register(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, helper -> FOTNeoForgeDataSerializers.init());
    }
}