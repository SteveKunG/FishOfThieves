package com.stevekung.fishofthieves.forge;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.registry.*;
import com.stevekung.fishofthieves.forge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.forge.level.FOTBiomeModifiers;
import com.stevekung.fishofthieves.forge.level.FOTStructureModifiers;
import com.stevekung.fishofthieves.forge.proxy.ClientProxyForge;
import com.stevekung.fishofthieves.forge.proxy.CommonProxyForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(FishOfThieves.MOD_ID)
public class FishOfThievesForge
{
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Codec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

    private static final String THIEVES_FISH_SPAWNS_IN_STRUCTURE = "thieves_fish_spawns_in_structure";
    public static final ResourceLocation ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL = FishOfThieves.id(THIEVES_FISH_SPAWNS_IN_STRUCTURE);
    private static final String ADD_FISH_BONE = "add_fish_bone";
    public static final ResourceLocation ADD_FISH_BONE_RL = FishOfThieves.id(ADD_FISH_BONE);

    public FishOfThievesForge()
    {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
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

        FishOfThieves.initGlobal();

        modEventBus.addListener(FOTBiomeModifiers::generateBiomeModifiers);
        STRUCTURE_MODIFIERS.register(THIEVES_FISH_SPAWNS_IN_STRUCTURE, FOTStructureModifiers.Modifier::makeCodec);
        modEventBus.addListener(FOTStructureModifiers::generateStructureModifiers);

        if (FMLEnvironment.dist.isClient())
        {
            new ClientProxyForge().init();
        }
        new CommonProxyForge().init();
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        FishOfThieves.initCommon();
        FOTLootItemFunctions.init();
        FOTLootPoolEntries.init();
        FOTStructures.init();
        FOTDataSerializers.init();
        FOTLootItemConditions.init();

        if (ModList.get().isLoaded("aquaculture"))
        {
            Aquaculture2.init();
        }
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event)
    {
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> FOTBlocks.init());
        event.register(ForgeRegistries.Keys.ITEMS, helper -> FOTItems.init());
        event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, helper -> FOTBlockEntityTypes.init());
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> FOTEntities.init());
        event.register(ForgeRegistries.Keys.FEATURES, helper -> FOTFeatures.init());
        event.register(ForgeRegistries.Keys.SENSOR_TYPES, helper -> FOTSensorTypes.init());
        event.register(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, helper -> FOTMemoryModuleTypes.init());
        event.register(Registries.CREATIVE_MODE_TAB, helper -> Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT, FishOfThieves.getCreativeTabBuilder(CreativeModeTab.builder()).build()));
    }
}