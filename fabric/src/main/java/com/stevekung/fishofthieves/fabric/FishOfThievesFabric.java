package com.stevekung.fishofthieves.fabric;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistryView;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;

public class FishOfThievesFabric implements ModInitializer
{
    static
    {
        FOTBuiltInRegistries.SPAWN_CONDITION_TYPE = BuiltInRegistries.registerSimple(FOTRegistries.SPAWN_CONDITION_TYPE, registry -> FOTSpawnConditions.ANY_OF);
    }

    @Override
    public void onInitialize()
    {
        DynamicRegistries.registerSynced(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.PONDIE_VARIANT, PondieVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.WRECKER_VARIANT, WreckerVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
        DynamicRegistries.registerSynced(FOTRegistries.STORMFISH_VARIANT, StormfishVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
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

        //@formatter:off
        FabricLoader.getInstance().getModContainer(FishOfThieves.MOD_ID)
                .map(container -> ResourceManagerHelper.registerBuiltinResourcePack(FishOfThieves.id("simple_spawning_condition_pack"),
                        container, Component.translatable("dataPack.simple_spawning_condition_pack.name"), ResourcePackActivationType.NORMAL))
                .filter(success -> !success).ifPresent(success -> FishOfThieves.LOGGER.warn("Could not register Simple Spawning Condition pack."));
        //@formatter:on

        FishOfThieves.initGlobal();
        FOTBlocks.init();
        FOTItems.init();
        FOTItemSubPredicates.init();
        FOTBlockEntityTypes.init();
        FOTEntities.init();
        FOTSensorTypes.init();
        FOTMemoryModuleTypes.init();
        FOTFeatures.init();
        FishOfThieves.initCommon();
        FOTLootPoolEntries.init();
        FOTStructures.init();
        FOTDataSerializers.init();
        FOTLootItemConditions.init();
        FOTCriteriaTriggers.init();
        FOTEntitySubPredicate.init();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FishOfThieves.FOT, FishOfThieves.getCreativeTabBuilder(FabricItemGroup.builder()).build());

        CompostingChanceRegistry.INSTANCE.add(FOTItems.EARTHWORMS, 0.4F);
        CompostingChanceRegistry.INSTANCE.add(FOTItems.GRUBS, 0.4F);
        CompostingChanceRegistry.INSTANCE.add(FOTItems.LEECHES, 0.4F);

//        FuelRegistry.INSTANCE.add(FOTTags.Items.WOODEN_FISH_PLAQUE, 300);TODO

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 1, list -> FishOfThieves.getFishermanTradesByLevel(1, list));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2, list -> FishOfThieves.getFishermanTradesByLevel(2, list));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 3, list -> FishOfThieves.getFishermanTradesByLevel(3, list));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 4, list -> FishOfThieves.getFishermanTradesByLevel(4, list));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 5, list -> FishOfThieves.getFishermanTradesByLevel(5, list));

        LootTableEvents.MODIFY.register((id, tableBuilder, source, provider) ->
        {
            // Gameplay
            if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
            {
                tableBuilder.modifyPools(FOTLootManager::getFishermanGiftLoot);
            }
            else if (id.equals(BuiltInLootTables.FISHING_FISH))
            {
                tableBuilder.modifyPools(builder -> FOTLootManager.getFishingLoot(builder, provider));
            }
            // Entity Loot
            else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable()))
            {
                tableBuilder.modifyPools(builder -> FOTLootManager.getPolarBearLoot(builder, provider));
            }
            else if (id.equals(EntityType.DOLPHIN.getDefaultLootTable()))
            {
                tableBuilder.modifyPools(builder -> FOTLootManager.getDolphinLoot(builder, provider));
            }
            else if (id.equals(EntityType.GUARDIAN.getDefaultLootTable()))
            {
                tableBuilder.withPool(FOTLootManager.getGuardianLoot(LootPool.lootPool(), provider, false));
            }
            else if (id.equals(EntityType.ELDER_GUARDIAN.getDefaultLootTable()))
            {
                tableBuilder.withPool(FOTLootManager.getGuardianLoot(LootPool.lootPool(), provider, true));
            }
            // Chests
            else if (id.equals(BuiltInLootTables.VILLAGE_FISHER))
            {
                tableBuilder.withPool(FOTLootManager.getVillageFisherLoot(LootPool.lootPool()));
            }
            else if (id.equals(BuiltInLootTables.BURIED_TREASURE))
            {
                tableBuilder.withPool(FOTLootManager.getBuriedTreasureLoot(LootPool.lootPool()));
            }
            // Archaeology
            else if (id.equals(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY) || id.equals(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY))
            {
                tableBuilder.modifyPools(FOTLootManager::getOceanRuinsArchaeologyLoot);
            }
        });

        FabricDefaultAttributeRegistry.register(FOTEntities.SPLASHTAIL, AbstractSchoolingThievesFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PONDIE, AbstractSchoolingThievesFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.ISLEHOPPER, AbstractSchoolingThievesFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.ANCIENTSCALE, AbstractSchoolingThievesFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PLENTIFIN, AbstractSchoolingThievesFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.WILDSPLASH, AbstractSchoolingThievesFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.DEVILFISH, Devilfish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.BATTLEGILL, Battlegill.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.WRECKER, Wrecker.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.STORMFISH, AbstractSchoolingThievesFish.createAttributes());

        SpawnPlacements.register(FOTEntities.SPLASHTAIL, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(FOTEntities.PONDIE, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(FOTEntities.ISLEHOPPER, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Islehopper::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.ANCIENTSCALE, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ancientscale::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.PLENTIFIN, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Plentifin::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.WILDSPLASH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wildsplash::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.DEVILFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Devilfish::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.BATTLEGILL, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Battlegill::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.WRECKER, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wrecker::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.STORMFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stormfish::checkSpawnRules);

        BiomeModifications.addFeature(BiomeSelectors.tag(FOTTags.Biomes.HAS_FISH_BONE), GenerationStep.Decoration.VEGETAL_DECORATION, FOTPlacements.FISH_BONE);

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
    }

    private static void addListenerForDynamic(DynamicRegistryView registryView, ResourceKey<? extends Registry<?>> key)
    {
        registryView.registerEntryAdded(key, (rawId, id, object) -> FishOfThieves.LOGGER.debug("Loaded entry of {}: {} = {}", key, id, object));
    }
}