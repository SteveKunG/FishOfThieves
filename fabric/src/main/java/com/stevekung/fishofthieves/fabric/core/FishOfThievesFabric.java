package com.stevekung.fishofthieves.fabric.core;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;

public class FishOfThievesFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        FishOfThieves.init();
        FOTBlocks.init();
        FOTItems.init();
        FOTBlockEntityTypes.init();
        FOTEntities.init();
        FishOfThieves.initCommon();
        FOTLootItemConditions.init();

        CompostingChanceRegistry.INSTANCE.add(FOTItems.EARTHWORMS, 0.4F);
        CompostingChanceRegistry.INSTANCE.add(FOTItems.GRUBS, 0.4F);
        CompostingChanceRegistry.INSTANCE.add(FOTItems.LEECHES, 0.4F);

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 1, FishOfThieves::getTierOneTrades);
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2, FishOfThieves::getTierTwoTrades);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            // Gameplay
            if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
            {
                tableBuilder.modifyPools(FOTLootManager::getFishermanGiftLoot);
            }
            else if (id.equals(BuiltInLootTables.FISHING_FISH))
            {
                tableBuilder.modifyPools(FOTLootManager::getFishingLoot);
            }
            // Entity Loot
            else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable()))
            {
                tableBuilder.modifyPools(FOTLootManager::getPolarBearLoot);
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
        });

        FabricDefaultAttributeRegistry.register(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PONDIE, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.ISLEHOPPER, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.ANCIENTSCALE, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.PLENTIFIN, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.WILDSPLASH, AbstractFish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.DEVILFISH, Devilfish.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.BATTLEGILL, Battlegill.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.WRECKER, Wrecker.createAttributes());
        FabricDefaultAttributeRegistry.register(FOTEntities.STORMFISH, AbstractFish.createAttributes());

        SpawnPlacements.register(FOTEntities.SPLASHTAIL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(FOTEntities.PONDIE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(FOTEntities.ISLEHOPPER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Islehopper::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.ANCIENTSCALE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ancientscale::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.PLENTIFIN, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Plentifin::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.WILDSPLASH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wildsplash::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.DEVILFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Devilfish::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.BATTLEGILL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Battlegill::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.WRECKER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wrecker::checkSpawnRules);
        SpawnPlacements.register(FOTEntities.STORMFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stormfish::checkSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_SPLASHTAILS), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, 15, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_PONDIES), FOTEntities.PONDIE.getCategory(), FOTEntities.PONDIE, 15, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_ISLEHOPPERS), FOTEntities.ISLEHOPPER.getCategory(), FOTEntities.ISLEHOPPER, 8, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_ANCIENTSCALES), FOTEntities.ANCIENTSCALE.getCategory(), FOTEntities.ANCIENTSCALE, 8, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_PLENTIFINS), FOTEntities.PLENTIFIN.getCategory(), FOTEntities.PLENTIFIN, 12, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_WILDSPLASH), FOTEntities.WILDSPLASH.getCategory(), FOTEntities.WILDSPLASH, 10, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_DEVILFISH), FOTEntities.DEVILFISH.getCategory(), FOTEntities.DEVILFISH, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_BATTLEGILLS), FOTEntities.BATTLEGILL.getCategory(), FOTEntities.BATTLEGILL, 5, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_WRECKERS), FOTEntities.WRECKER.getCategory(), FOTEntities.WRECKER, 50, 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FOTTags.Biomes.SPAWNS_STORMFISH), FOTEntities.STORMFISH.getCategory(), FOTEntities.STORMFISH, 12, 4, 8);
    }
}