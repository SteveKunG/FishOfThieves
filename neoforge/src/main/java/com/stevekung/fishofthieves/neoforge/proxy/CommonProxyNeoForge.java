package com.stevekung.fishofthieves.neoforge.proxy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

public class CommonProxyNeoForge
{
    public void init()
    {
        NeoForge.EVENT_BUS.register(this);
        var eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerAttributes);
        eventBus.addListener(this::registerSpawnPlacement);
        eventBus.addListener(this::onAddPackFinders);
    }

    @SuppressWarnings("deprecation")
    public void commonSetup(FMLCommonSetupEvent event)
    {
        ComposterBlock.COMPOSTABLES.put(FOTItems.EARTHWORMS, 0.4F);
        ComposterBlock.COMPOSTABLES.put(FOTItems.GRUBS, 0.4F);
        ComposterBlock.COMPOSTABLES.put(FOTItems.LEECHES, 0.4F);
    }

    @SubscribeEvent
    public void onFuelBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        var itemStack = event.getItemStack();

        if (itemStack.is(FOTTags.Items.WOODEN_FISH_PLAQUE))
        {
            event.setBurnTime(300);
        }
    }

    @SubscribeEvent
    public void registerLootTables(LootTableLoadEvent event)
    {
        var id = event.getName();
        var table = event.getTable();

        // Gameplay
        if (id.equals(BuiltInLootTables.FISHERMAN_GIFT.location()))
        {
            injectLoot(table, FOTLootManager.getFishermanGiftLoot(LootPool.lootPool()).entries);
        }
        else if (id.equals(BuiltInLootTables.FISHING_FISH.location()))
        {
            injectLoot(table, FOTLootManager.getFishingLoot(LootPool.lootPool()).entries);
        }
        // Entity Loot
        else if (id.equals(EntityType.POLAR_BEAR.getDefaultLootTable().location()))
        {
            injectLoot(table, FOTLootManager.getPolarBearLoot(LootPool.lootPool()).entries);
        }
        // Chests
        else if (id.equals(BuiltInLootTables.VILLAGE_FISHER.location()))
        {
            injectLoot(table, FOTLootManager.getVillageFisherLoot(LootPool.lootPool()).entries);
        }
        else if (id.equals(BuiltInLootTables.BURIED_TREASURE.location()))
        {
            table.addPool(FOTLootManager.getBuriedTreasureLoot(LootPool.lootPool()).build());
        }
        // Archaeology
        else if (id.equals(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.location()) || id.equals(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.location()))
        {
            injectLoot(table, FOTLootManager.getOceanRuinsArchaeologyLoot(LootPool.lootPool()).entries);
        }
    }

    @SubscribeEvent
    public void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.FISHERMAN)
        {
            var trades = event.getTrades();
            trades.get(1).addAll(FishOfThieves.getFishermanTradesByLevel(1, Lists.newArrayList()));
            trades.get(2).addAll(FishOfThieves.getFishermanTradesByLevel(2, Lists.newArrayList()));
            trades.get(3).addAll(FishOfThieves.getFishermanTradesByLevel(3, Lists.newArrayList()));
            trades.get(4).addAll(FishOfThieves.getFishermanTradesByLevel(4, Lists.newArrayList()));
            trades.get(5).addAll(FishOfThieves.getFishermanTradesByLevel(5, Lists.newArrayList()));
        }
    }

    private void registerSpawnPlacement(SpawnPlacementRegisterEvent event)
    {
        event.register(FOTEntities.SPLASHTAIL, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.PONDIE, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.ISLEHOPPER, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Islehopper::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.ANCIENTSCALE, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ancientscale::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.PLENTIFIN, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Plentifin::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.WILDSPLASH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wildsplash::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.DEVILFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Devilfish::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.BATTLEGILL, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Battlegill::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.WRECKER, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wrecker::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.STORMFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stormfish::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    private void registerAttributes(EntityAttributeCreationEvent event)
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

    private void onAddPackFinders(AddPackFindersEvent event)
    {
        event.addPackFinders(FishOfThieves.id("simple_spawning_condition_pack"), PackType.SERVER_DATA, Component.translatable("dataPack.simple_spawning_condition_pack.description"), PackSource.FEATURE, false, Pack.Position.TOP);
    }

    private static void injectLoot(LootTable table, ImmutableList.Builder<LootPoolEntryContainer> entries)
    {
        var pool = table.getPool("main");
        var modifiedEntries = Lists.newArrayList(pool.entries);
        modifiedEntries.addAll(entries.build());
        pool.entries = modifiedEntries;
    }
}