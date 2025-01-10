package com.stevekung.fishofthieves.forge;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.forge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.forge.level.FOTBiomeModifiers;
import com.stevekung.fishofthieves.forge.level.FOTStructureModifiers;
import com.stevekung.fishofthieves.forge.proxy.ClientProxyForge;
import com.stevekung.fishofthieves.forge.proxy.CommonProxyForge;
import com.stevekung.fishofthieves.item.PomegranateSeedsItem;
import com.stevekung.fishofthieves.registry.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.CreativeModeTab;
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
    public static final DeferredRegister<Codec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

    private static final String THIEVES_FISH_SPAWNS_IN_STRUCTURE = "thieves_fish_spawns_in_structure";
    public static final ResourceLocation ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL = FishOfThieves.id(THIEVES_FISH_SPAWNS_IN_STRUCTURE);
    private static final String ADD_FISH_BONE = "add_fish_bone";
    public static final ResourceLocation ADD_FISH_BONE_RL = FishOfThieves.id(ADD_FISH_BONE);

    public FishOfThievesForge(FMLJavaModLoadingContext context)
    {
        var modEventBus = context.getModEventBus();
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

        FishOfThieves.init();

        modEventBus.addListener(FOTBiomeModifiers::generateBiomeModifiers);
        STRUCTURE_MODIFIERS.register(THIEVES_FISH_SPAWNS_IN_STRUCTURE, FOTStructureModifiers.Modifier::makeCodec);
        modEventBus.addListener(FOTStructureModifiers::generateStructureModifiers);

        if (FMLEnvironment.dist.isClient())
        {
            new ClientProxyForge().init(context);
        }
        new CommonProxyForge().init(context);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        if (ModList.get().isLoaded("aquaculture"))
        {
            Aquaculture2.init();
        }
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event)
    {
        event.register(ForgeRegistries.Keys.BLOCKS, helper ->
        {
            FOTBlocks.init();

            helper.register("pink_plumeria", FOTBlocks.PINK_PLUMERIA = FOTBlocks.getPinkPlumeria());
            helper.register("banana_shoots", FOTBlocks.BANANA_SHOOTS = FOTBlocks.getBananaShoots());
            helper.register("mango_seed", FOTBlocks.MANGO_SEED = FOTBlocks.getMangoSeed());
            helper.register("mango_sapling", FOTBlocks.MANGO_SAPLING = FOTBlocks.getMangoSapling());
            helper.register("pomegranate_plant", FOTBlocks.POMEGRANATE_PLANT = FOTBlocks.getPomegranatePlant());

            helper.register("potted_pink_plumeria", FOTBlocks.POTTED_PINK_PLUMERIA = FOTBlocks.flowerPot(FOTBlocks.PINK_PLUMERIA));
            helper.register("potted_banana_shoots", FOTBlocks.POTTED_BANANA_SHOOTS = FOTBlocks.flowerPot(FOTBlocks.BANANA_SHOOTS));
            helper.register("potted_mango_seed", FOTBlocks.POTTED_MANGO_SEED = FOTBlocks.flowerPot(FOTBlocks.MANGO_SEED));
            helper.register("potted_mango_sapling", FOTBlocks.POTTED_MANGO_SAPLING = FOTBlocks.flowerPot(FOTBlocks.MANGO_SAPLING));
            helper.register("potted_pomegranate_plant", FOTBlocks.POTTED_POMEGRANATE_PLANT = FOTBlocks.flowerPot(FOTBlocks.POMEGRANATE_PLANT));
        });
        event.register(ForgeRegistries.Keys.ITEMS, helper ->
        {
            FOTItems.init();

            helper.register("pink_plumeria", FOTItems.PINK_PLUMERIA = FOTItems.blockItem(FOTBlocks.PINK_PLUMERIA));
            helper.register("banana_shoots", FOTItems.BANANA_SHOOTS = FOTItems.blockItem(FOTBlocks.BANANA_SHOOTS));
            helper.register("mango_seed", FOTItems.MANGO_SEED = new ItemNameBlockItem(FOTBlocks.MANGO_SEED, new Item.Properties()));
            helper.register("mango_sapling", FOTItems.MANGO_SAPLING = FOTItems.blockItem(FOTBlocks.MANGO_SAPLING));
            helper.register("pomegranate_plant", FOTItems.POMEGRANATE_PLANT = FOTItems.blockItem(FOTBlocks.POMEGRANATE_PLANT));
            helper.register("tall_pomegranate_plant", FOTItems.TALL_POMEGRANATE_PLANT = new DoubleHighBlockItem(FOTBlocks.TALL_POMEGRANATE_PLANT, new Item.Properties()));
            helper.register("pomegranate_seeds", FOTItems.POMEGRANATE_SEEDS = new PomegranateSeedsItem(FOTBlocks.POMEGRANATE_PLANT, new Item.Properties()));
        });
        event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, helper -> FOTBlockEntityTypes.init());
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> FOTEntities.init());
        event.register(ForgeRegistries.Keys.BIOMES, helper -> FOTLootItemConditions.init());
        event.register(ForgeRegistries.Keys.FEATURES, helper -> FOTFeatures.init());
        event.register(ForgeRegistries.Keys.SENSOR_TYPES, helper -> FOTSensorTypes.init());
        event.register(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, helper -> FOTMemoryModuleTypes.init());
        event.register(ForgeRegistries.Keys.TREE_DECORATOR_TYPES, helper -> FOTTreeDecoratorTypes.init());
        event.register(ForgeRegistries.Keys.FOLIAGE_PLACER_TYPES, helper -> FOTFoliagePlacerTypes.init());
        event.register(ForgeRegistries.Keys.BLOCK_STATE_PROVIDER_TYPES, helper -> FOTBlockStateProviderTypes.init());
        event.register(Registries.TRUNK_PLACER_TYPE, helper -> FOTTrunkPlacerTypes.init());
        event.register(Registries.CREATIVE_MODE_TAB, helper ->
        {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT_MAIN, FishOfThieves.getMainCreativeTabBuilder(CreativeModeTab.builder()).build());
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT_FISH, FishOfThieves.getFishCreativeTabBuilder(CreativeModeTab.builder()).build());
        });
    }
}