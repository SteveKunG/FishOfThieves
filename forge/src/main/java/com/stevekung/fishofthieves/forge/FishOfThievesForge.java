package com.stevekung.fishofthieves.forge;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.FishOfThievesClient;
import com.stevekung.fishofthieves.forge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.forge.level.FOTBiomeModifiers;
import com.stevekung.fishofthieves.forge.level.FOTStructureModifiers;
import com.stevekung.fishofthieves.forge.proxy.ClientProxyForge;
import com.stevekung.fishofthieves.forge.proxy.CommonProxyForge;
import com.stevekung.fishofthieves.registry.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
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
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Codec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

    private static final String THIEVES_FISH_SPAWNS_IN_STRUCTURE = "thieves_fish_spawns_in_structure";
    public static final ResourceLocation ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL = FishOfThieves.id(THIEVES_FISH_SPAWNS_IN_STRUCTURE);

    private static final String PROTOCOL_VERSION = "1";
    private static int ID = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(FishOfThieves.id("main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

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
        TREE_DECORATOR_TYPES.register(modEventBus);
        FOLIAGE_PLACER_TYPES.register(modEventBus);
        BLOCK_STATE_PROVIDER_TYPES.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);

        FishOfThieves.init();
        FOTGrassColorModifier.TROPICAL_ISLAND = FOTPlatform.getTropicalIslandGrassColor();

        modEventBus.addListener(FOTBiomeModifiers::generateBiomeModifiers);
        STRUCTURE_MODIFIERS.register(THIEVES_FISH_SPAWNS_IN_STRUCTURE, FOTStructureModifiers.Modifier::makeCodec);
        modEventBus.addListener(FOTStructureModifiers::generateStructureModifiers);

        if (FMLEnvironment.dist.isClient())
        {
            new ClientProxyForge().init();
            FishOfThievesClient.init();
        }
        new CommonProxyForge().init();
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        if (ModList.get().isLoaded("aquaculture"))
        {
            Aquaculture2.init();
        }
        INSTANCE.messageBuilder(ReceiveFishingHookBaitPacket.class, nextID()).encoder(ReceiveFishingHookBaitPacket::toBytes).decoder(ReceiveFishingHookBaitPacket::new).consumerMainThread(ReceiveFishingHookBaitPacket::handle).add();
        INSTANCE.messageBuilder(RequestServerShoalPacket.class, nextID()).encoder(RequestServerShoalPacket::toBytes).decoder(RequestServerShoalPacket::new).consumerMainThread(RequestServerShoalPacket::handle).add();
        INSTANCE.messageBuilder(SyncClientShoalPacket.class, nextID()).encoder(SyncClientShoalPacket::toBytes).decoder(SyncClientShoalPacket::new).consumerMainThread(SyncClientShoalPacket::handle).add();
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event)
    {
        event.register(ForgeRegistries.Keys.BLOCKS, helper ->
        {
            FOTBlocks.init();

            helper.register("pink_plumeria", FOTBlocks.PINK_PLUMERIA = FOTBlocks.getPinkPlumeria());
            helper.register("light_blue_plumeria", FOTBlocks.LIGHT_BLUE_PLUMERIA = FOTBlocks.getLightBluePlumeria());
            helper.register("white_plumeria", FOTBlocks.WHITE_PLUMERIA = FOTBlocks.getWhitePlumeria());
            helper.register("banana_shoots", FOTBlocks.BANANA_SHOOTS = FOTBlocks.getBananaShoots());
            helper.register("mango_pit", FOTBlocks.MANGO_PIT = FOTBlocks.getMangoPit());
            helper.register("mango_sapling", FOTBlocks.MANGO_SAPLING = FOTBlocks.getMangoSapling());
            helper.register("pomegranate_plant", FOTBlocks.POMEGRANATE_PLANT = FOTBlocks.getPomegranatePlant());
            helper.register("pomegranate_sapling", FOTBlocks.POMEGRANATE_SAPLING = FOTBlocks.getPomegranateSapling());
            helper.register("tropical_red_fern", FOTBlocks.TROPICAL_RED_FERN = FOTBlocks.getTropicalRedFern());
            helper.register("tropical_monstera", FOTBlocks.TROPICAL_MONSTERA = FOTBlocks.getTropicalMonstera());

            helper.register("potted_pink_plumeria", FOTBlocks.POTTED_PINK_PLUMERIA = FOTBlocks.flowerPot(FOTBlocks.PINK_PLUMERIA));
            helper.register("potted_light_blue_plumeria", FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA = FOTBlocks.flowerPot(FOTBlocks.LIGHT_BLUE_PLUMERIA));
            helper.register("potted_white_plumeria", FOTBlocks.POTTED_WHITE_PLUMERIA = FOTBlocks.flowerPot(FOTBlocks.WHITE_PLUMERIA));
            helper.register("potted_banana_shoots", FOTBlocks.POTTED_BANANA_SHOOTS = FOTBlocks.flowerPot(FOTBlocks.BANANA_SHOOTS));
            helper.register("potted_mango_pit", FOTBlocks.POTTED_MANGO_PIT = FOTBlocks.flowerPot(FOTBlocks.MANGO_PIT));
            helper.register("potted_mango_sapling", FOTBlocks.POTTED_MANGO_SAPLING = FOTBlocks.flowerPot(FOTBlocks.MANGO_SAPLING));
            helper.register("potted_pomegranate_plant", FOTBlocks.POTTED_POMEGRANATE_PLANT = FOTBlocks.flowerPot(FOTBlocks.POMEGRANATE_PLANT));
            helper.register("potted_pomegranate_sapling", FOTBlocks.POTTED_POMEGRANATE_SAPLING = FOTBlocks.flowerPot(FOTBlocks.POMEGRANATE_SAPLING));
            helper.register("potted_tropical_red_fern", FOTBlocks.POTTED_TROPICAL_RED_FERN = FOTBlocks.flowerPot(FOTBlocks.TROPICAL_RED_FERN));
            helper.register("potted_tropical_monstera", FOTBlocks.POTTED_TROPICAL_MONSTERA = FOTBlocks.flowerPot(FOTBlocks.TROPICAL_MONSTERA));
        });
        event.register(ForgeRegistries.Keys.ITEMS, helper ->
        {
            FOTItems.init();

            helper.register("pink_plumeria", FOTItems.PINK_PLUMERIA = FOTItems.blockItem(FOTBlocks.PINK_PLUMERIA));
            helper.register("light_blue_plumeria", FOTItems.LIGHT_BLUE_PLUMERIA = FOTItems.blockItem(FOTBlocks.LIGHT_BLUE_PLUMERIA));
            helper.register("white_plumeria", FOTItems.WHITE_PLUMERIA = FOTItems.blockItem(FOTBlocks.WHITE_PLUMERIA));
            helper.register("banana_shoots", FOTItems.BANANA_SHOOTS = FOTItems.blockItem(FOTBlocks.BANANA_SHOOTS));
            helper.register("mango_pit", FOTItems.MANGO_PIT = new ItemNameBlockItem(FOTBlocks.MANGO_PIT, new Item.Properties()));
            helper.register("mango_sapling", FOTItems.MANGO_SAPLING = FOTItems.blockItem(FOTBlocks.MANGO_SAPLING));
            helper.register("pomegranate_plant", FOTItems.POMEGRANATE_PLANT = FOTItems.blockItem(FOTBlocks.POMEGRANATE_PLANT));
            helper.register("tall_pomegranate_plant", FOTItems.TALL_POMEGRANATE_PLANT = new DoubleHighBlockItem(FOTBlocks.TALL_POMEGRANATE_PLANT, new Item.Properties()));
            helper.register("pomegranate_seeds", FOTItems.POMEGRANATE_SEEDS = new ItemNameBlockItem(FOTBlocks.POMEGRANATE_SAPLING, new Item.Properties()));
            helper.register("tropical_red_fern", FOTItems.TROPICAL_RED_FERN = FOTItems.blockItem(FOTBlocks.TROPICAL_RED_FERN));
            helper.register("tropical_monstera", FOTItems.TROPICAL_MONSTERA = FOTItems.blockItem(FOTBlocks.TROPICAL_MONSTERA));
        });
        event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, helper -> FOTBlockEntityTypes.init());
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> FOTEntities.init());
        event.register(ForgeRegistries.Keys.BIOMES, helper ->
        {
            FOTLootItemConditions.init();
            FOTDecoratedPotPatterns.putItemsToPotTexture();
        });
        event.register(ForgeRegistries.Keys.FEATURES, helper -> FOTFeatures.init());
        event.register(ForgeRegistries.Keys.SENSOR_TYPES, helper -> FOTSensorTypes.init());
        event.register(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, helper -> FOTMemoryModuleTypes.init());
        event.register(ForgeRegistries.Keys.TREE_DECORATOR_TYPES, helper -> FOTTreeDecoratorTypes.init());
        event.register(ForgeRegistries.Keys.FOLIAGE_PLACER_TYPES, helper -> FOTFoliagePlacerTypes.init());
        event.register(ForgeRegistries.Keys.BLOCK_STATE_PROVIDER_TYPES, helper -> FOTBlockStateProviderTypes.init());
        event.register(ForgeRegistries.Keys.MOB_EFFECTS, helper -> FOTMobEffects.init());
        event.register(Registries.TRUNK_PLACER_TYPE, helper -> FOTTrunkPlacerTypes.init());
        event.register(Registries.CREATIVE_MODE_TAB, helper -> FOTCreativeTabs.init());
        event.register(Registries.PLACEMENT_MODIFIER_TYPE, helper -> FOTPlacementModifiers.init());
        event.register(Registries.MATERIAL_CONDITION, helper -> FOTSurfaceRuleConditionSources.init());
        event.register(Registries.POINT_OF_INTEREST_TYPE, helper -> FOTPoiTypes.init());
    }

    public static void sendToClient(Object packet, ServerPlayer player)
    {
        var connection = player.connection.connection;

        if (INSTANCE.isRemotePresent(connection))
        {
            INSTANCE.sendTo(packet, connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToServer(Object packet)
    {
        INSTANCE.sendToServer(packet);
    }

    private static int nextID()
    {
        return ID++;
    }
}