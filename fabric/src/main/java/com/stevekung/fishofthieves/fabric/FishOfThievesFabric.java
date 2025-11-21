package com.stevekung.fishofthieves.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.network.ReceiveFishingHookBaitPacket;
import com.stevekung.fishofthieves.registry.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistryView;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;

public class FishOfThievesFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        DynamicRegistries.registerSynced(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariant.DIRECT_CODEC, SplashtailVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.PONDIE_VARIANT, PondieVariant.DIRECT_CODEC, PondieVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariant.DIRECT_CODEC, IslehopperVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariant.DIRECT_CODEC, AncientscaleVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariant.DIRECT_CODEC, PlentifinVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariant.DIRECT_CODEC, WildsplashVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariant.DIRECT_CODEC, DevilfishVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariant.DIRECT_CODEC, BattlegillVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.WRECKER_VARIANT, WreckerVariant.DIRECT_CODEC, WreckerVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.STORMFISH_VARIANT, StormfishVariant.DIRECT_CODEC, StormfishVariant.NETWORK_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.FISH_PLAQUE_INTERACTION, FishPlaqueInteraction.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);

        DynamicRegistrySetupCallback.EVENT.register(registryView ->
        {
            addListenerForDynamic(registryView, FOTRegistries.SPLASHTAIL_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.PONDIE_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.ISLEHOPPER_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.ANCIENTSCALE_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.PLENTIFIN_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.WILDSPLASH_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.DEVILFISH_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.BATTLEGILL_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.WRECKER_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.STORMFISH_VARIANT);
            addListenerForDynamic(registryView, FOTRegistries.FISH_PLAQUE_INTERACTION);
        });

        FabricLoader.getInstance().getModContainer(FishOfThieves.MOD_ID)
                .map(container -> ResourceManagerHelper.registerBuiltinResourcePack(FishOfThieves.id("simple_spawning_condition_pack"),
                        container, Component.translatable("dataPack.simple_spawning_condition_pack.name"), ResourcePackActivationType.NORMAL))
                .filter(success -> !success).ifPresent(success -> FishOfThieves.LOGGER.warn("Could not register Simple Spawning Condition pack."));

        FOTBlocks.init();
        FOTItems.init();
        FOTDataComponentPredicates.init();
        FOTBlockEntityTypes.init();
        FOTSoundEvents.init();
        FOTEntities.init();
        FOTSensorTypes.init();
        FOTMemoryModuleTypes.init();
        FOTFeatures.init();
        FOTTrunkPlacerTypes.init();
        FOTTreeDecoratorTypes.init();
        FOTFoliagePlacerTypes.init();
        FOTBlockStateProviderTypes.init();
        FOTBlockPredicateTypes.init();
        FishOfThieves.initCommon();
        FOTLootPoolEntries.init();
        FOTStructures.init();
        FOTDataSerializers.init();
        FOTLootItemConditions.init();
        FOTCriteriaTriggers.init();
        FOTEntitySubPredicate.init();
        FOTDataComponentTypes.init();
        FOTSpawnConditions.init();
        FOTMobEffects.init();
        FOTPlacementModifiers.init();
        FOTSurfaceRuleConditionSources.init();
        FOTEnvironmentAttributes.init();
        FOTDebugSubscriptions.init();

        FOTDecoratedPotPatterns.putItemsToPotTexture();

        FishOfThieves.initCompostables();

        FOTGrassColorModifier.TROPICAL_ISLAND = ClassTinkerers.getEnum(BiomeSpecialEffects.GrassColorModifier.class, "FISHOFTHIEVES_TROPICAL_ISLAND");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT_MAIN, FishOfThieves.getMainCreativeTabBuilder(FabricItemGroup.builder()).build());
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT_FISH, FishOfThieves.getFishCreativeTabBuilder(FabricItemGroup.builder()).build());

        StrippableBlockRegistry.register(FOTBlocks.COCONUT_LOG, FOTBlocks.STRIPPED_COCONUT_LOG);
        StrippableBlockRegistry.register(FOTBlocks.COCONUT_WOOD, FOTBlocks.STRIPPED_COCONUT_WOOD);

        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(FOTTags.Items.WOODEN_FISH_PLAQUE, 300));

        FishOfThieves.getFishermanTrades().forEach((level, factories) -> TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, level, factories::apply));

        LootTableEvents.MODIFY.register((id, tableBuilder, source, provider) ->
        {
            FOTLootManager.getInjectedLootPoolMap().forEach((resourceKey, function) ->
            {
                if (id.equals(resourceKey))
                {
                    tableBuilder.withPool(function.apply(LootPool.lootPool(), provider));
                }
            });
            tableBuilder.modifyPools(builder -> FOTLootManager.getInjectedLootTableMap().forEach((resourceKey, function) ->
            {
                if (id.equals(resourceKey))
                {
                    function.apply(builder, provider);
                }
            }));
        });

        FishOfThieves.getEntityAttributes().forEach(FabricDefaultAttributeRegistry::register);

        FishOfThieves.getSpawnPlacements().forEach(entry -> SpawnPlacements.register(entry.type(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entry.spawnPredicate()));

        BiomeModifications.addFeature(BiomeSelectors.tag(FOTTags.Biomes.HAS_FISH_BONE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.FISH_BONE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.TREES_COCONUT);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_TROPICAL_FLOWER);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_FRUIT_TREES);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_PINEAPPLE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_PATCH_WILD_POMEGRANATE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.SPARSE_JUNGLE_PATCH_TROPICAL_BUSH);

        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_SPLASHTAILS), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, FishOfThieves.CONFIG.spawnRate.fishWeight.splashtail, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_PONDIES), FOTEntities.PONDIE.getCategory(), FOTEntities.PONDIE, FishOfThieves.CONFIG.spawnRate.fishWeight.pondie, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_ISLEHOPPERS), FOTEntities.ISLEHOPPER.getCategory(), FOTEntities.ISLEHOPPER, FishOfThieves.CONFIG.spawnRate.fishWeight.islehopper, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_ANCIENTSCALES), FOTEntities.ANCIENTSCALE.getCategory(), FOTEntities.ANCIENTSCALE, FishOfThieves.CONFIG.spawnRate.fishWeight.ancientscale, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_PLENTIFINS), FOTEntities.PLENTIFIN.getCategory(), FOTEntities.PLENTIFIN, FishOfThieves.CONFIG.spawnRate.fishWeight.plentifin, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_WILDSPLASH), FOTEntities.WILDSPLASH.getCategory(), FOTEntities.WILDSPLASH, FishOfThieves.CONFIG.spawnRate.fishWeight.wildsplash, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_DEVILFISH), FOTEntities.DEVILFISH.getCategory(), FOTEntities.DEVILFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.devilfish, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_BATTLEGILLS), FOTEntities.BATTLEGILL.getCategory(), FOTEntities.BATTLEGILL, FishOfThieves.CONFIG.spawnRate.fishWeight.battlegill, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_WRECKERS), FOTEntities.WRECKER.getCategory(), FOTEntities.WRECKER, FishOfThieves.CONFIG.spawnRate.fishWeight.wrecker, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_STORMFISH), FOTEntities.STORMFISH.getCategory(), FOTEntities.STORMFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.stormfish, 4, 8);

        PayloadTypeRegistry.playS2C().register(ReceiveFishingHookBaitPacket.TYPE, ReceiveFishingHookBaitPacket.CODEC);

        ServerChunkEvents.CHUNK_LOAD.register((level, chunk) -> level.getBaitPreserve().spawnBaitOnLoad(level));
    }

    private static void addListenerForDynamic(DynamicRegistryView registryView, ResourceKey<? extends Registry<?>> key)
    {
        registryView.registerEntryAdded(key, (rawId, id, object) -> FishOfThieves.LOGGER.debug("Loaded entry of {}: {} = {}", key, id, object));
    }
}