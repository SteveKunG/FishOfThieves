package com.stevekung.fishofthieves.forge.proxy;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Lists;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.entity.animal.Devilfish;
import com.stevekung.fishofthieves.entity.animal.Wrecker;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxyForge
{
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerAttributes);
    }

    public void commonSetup(FMLCommonSetupEvent event)
    {
        ComposterBlock.COMPOSTABLES.put(FOTItems.EARTHWORMS, 0.4F);
        ComposterBlock.COMPOSTABLES.put(FOTItems.GRUBS, 0.4F);
        ComposterBlock.COMPOSTABLES.put(FOTItems.LEECHES, 0.4F);
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
    }

    @SubscribeEvent
    public void registerLootTables(LootTableLoadEvent event)
    {
        var id = event.getName();
        var table = event.getTable();

        // Gameplay
        if (id.equals(BuiltInLootTables.FISHERMAN_GIFT))
        {
            injectLoot(table, FOTLootManager.getFishermanGiftLoot(LootPool.lootPool()).entries);
        }
        else if (id.equals(BuiltInLootTables.FISHING_FISH))
        {
            injectLoot(table, FOTLootManager.getFishingLoot(LootPool.lootPool()).entries);
        }
        // Entity Loot
        else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable()))
        {
            injectLoot(table, FOTLootManager.getPolarBearLoot(LootPool.lootPool()).entries);
        }
        // Chests
        else if (id.equals(BuiltInLootTables.VILLAGE_FISHER))
        {
            injectLoot(table, FOTLootManager.getVillageFisherLoot(LootPool.lootPool()).entries);
        }
        else if (id.equals(BuiltInLootTables.BURIED_TREASURE))
        {
            table.addPool(FOTLootManager.getBuriedTreasureLoot(LootPool.lootPool()).build());
        }
    }

    @SubscribeEvent
    public void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.FISHERMAN)
        {
            event.getTrades().put(1, Util.make(Lists.newArrayList(), FishOfThieves::getTierOneTrades));
            event.getTrades().put(2, Util.make(Lists.newArrayList(), FishOfThieves::getTierTwoTrades));
        }
    }

    @SubscribeEvent
    public void registerMobSpawn(BiomeLoadingEvent event)
    {
        var category = event.getCategory();
        var name = event.getName();

        if (category == Biome.BiomeCategory.OCEAN)
        {
            event.getSpawns().addSpawn(FOTEntities.SPLASHTAIL.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.SPLASHTAIL, 15, 4, 8));
        }
        if (category == Biome.BiomeCategory.RIVER || category == Biome.BiomeCategory.FOREST)
        {
            event.getSpawns().addSpawn(FOTEntities.PONDIE.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.PONDIE, 15, 2, 4));
        }
        if (category == Biome.BiomeCategory.OCEAN || category == Biome.BiomeCategory.BEACH || category == Biome.BiomeCategory.JUNGLE || category == Biome.BiomeCategory.SWAMP || category == Biome.BiomeCategory.UNDERGROUND)
        {
            event.getSpawns().addSpawn(FOTEntities.ISLEHOPPER.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.ISLEHOPPER, 8, 2, 4));
        }
        if (Objects.equals(name, Biomes.LUKEWARM_OCEAN.location()) || Objects.equals(name, Biomes.DEEP_LUKEWARM_OCEAN.location()))
        {
            event.getSpawns().addSpawn(FOTEntities.ANCIENTSCALE.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 8, 4, 8));
        }
        if (Objects.equals(name, Biomes.WARM_OCEAN.location()) || Objects.equals(name, Biomes.LUKEWARM_OCEAN.location()) || Objects.equals(name, Biomes.DEEP_LUKEWARM_OCEAN.location()) || category == Biome.BiomeCategory.UNDERGROUND)
        {
            event.getSpawns().addSpawn(FOTEntities.PLENTIFIN.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 12, 4, 8));
        }
        if (Objects.equals(name, Biomes.LUSH_CAVES.location()) || Objects.equals(name, Biomes.WARM_OCEAN.location()) || category == Biome.BiomeCategory.OCEAN || category == Biome.BiomeCategory.BEACH || category == Biome.BiomeCategory.JUNGLE || category == Biome.BiomeCategory.SWAMP)
        {
            event.getSpawns().addSpawn(FOTEntities.WILDSPLASH.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.WILDSPLASH, 10, 2, 4));
        }
        if (!Objects.equals(name, Biomes.LUSH_CAVES.location()))
        {
            event.getSpawns().addSpawn(FOTEntities.DEVILFISH.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.DEVILFISH, 4, 1, 2));
        }

        event.getSpawns().addSpawn(FOTEntities.BATTLEGILL.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 5, 2, 4));

        if (category == Biome.BiomeCategory.OCEAN)
        {
            event.getSpawns().addSpawn(FOTEntities.WRECKER.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 50, 4, 8));
        }
        if (category == Biome.BiomeCategory.OCEAN || Objects.equals(name, Biomes.SPARSE_JUNGLE.location()))
        {
            event.getSpawns().addSpawn(FOTEntities.STORMFISH.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.STORMFISH, 12, 4, 8));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addFishSpawnToStructure(StructureSpawnListGatherEvent event)
    {
        if (event.getStructure() == StructureFeature.OCEAN_RUIN)
        {
            event.addEntitySpawns(MobCategory.WATER_AMBIENT, FOTEntities.ANCIENTSCALES.unwrap());
        }
        else if (event.getStructure() == StructureFeature.MINESHAFT || event.getStructure() == StructureFeature.STRONGHOLD)
        {
            event.addEntitySpawns(MobCategory.WATER_AMBIENT, FOTEntities.ANCIENTSCALES_AND_PLENTIFINS.unwrap());
        }
        else if (event.getStructure() == StructureFeature.OCEAN_MONUMENT || event.getStructure() == StructureFeature.PILLAGER_OUTPOST)
        {
            event.addEntitySpawns(MobCategory.WATER_AMBIENT, FOTEntities.BATTLEGILLS.unwrap());
        }
        else if (event.getStructure() == StructureFeature.SHIPWRECK || event.getStructure() == StructureFeature.RUINED_PORTAL)
        {
            event.addEntitySpawns(MobCategory.WATER_AMBIENT, FOTEntities.WRECKERS.unwrap());
        }
    }

    public void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes().build());
        event.put(FOTEntities.PONDIE, AbstractFish.createAttributes().build());
        event.put(FOTEntities.ISLEHOPPER, AbstractFish.createAttributes().build());
        event.put(FOTEntities.ANCIENTSCALE, AbstractFish.createAttributes().build());
        event.put(FOTEntities.PLENTIFIN, AbstractFish.createAttributes().build());
        event.put(FOTEntities.WILDSPLASH, AbstractFish.createAttributes().build());
        event.put(FOTEntities.DEVILFISH, Devilfish.createAttributes().build());
        event.put(FOTEntities.BATTLEGILL, Battlegill.createAttributes().build());
        event.put(FOTEntities.WRECKER, Wrecker.createAttributes().build());
        event.put(FOTEntities.STORMFISH, AbstractFish.createAttributes().build());
    }

    private static void injectLoot(LootTable table, List<LootPoolEntryContainer> entries)
    {
        var pool = table.getPool("main");
        pool.entries = ArrayUtils.addAll(pool.entries, entries.toArray(LootPoolEntryContainer[]::new));
    }
}