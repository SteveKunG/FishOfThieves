package com.stevekung.fishofthieves.neoforge.proxy;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.compatibility.terrablender.FOTTerraBlender;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.levelgen.Heightmap;
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
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
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

    public void commonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            if (FOTPlatform.isModLoaded("terrablender"))
            {
                FOTTerraBlender.init();
            }
        });
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event)
    {
        var provider = event.getRegistries();
        var table = event.getTable();
        var id = event.getKey();

        FOTLootManager.getInjectedLootTableMap().forEach((resourceKey, function) ->
        {
            if (id.equals(resourceKey))
            {
                injectLoot(table, function.apply(LootPool.lootPool(), provider).entries);
            }
        });
        FOTLootManager.getInjectedLootPoolMap().forEach((resourceKey, function) ->
        {
            if (id.equals(resourceKey))
            {
                table.addPool(function.apply(LootPool.lootPool(), provider).build());
            }
        });
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
    public void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.FISHERMAN)
        {
            var trades = event.getTrades();
            FishOfThieves.getFishermanTrades().forEach((level, factories) -> trades.get(level.intValue()).addAll(factories.apply(new ArrayList<>())));
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event)
    {
        if (event.getLevel() instanceof ServerLevel serverLevel)
        {
            serverLevel.getBaitPreserve().spawnBaitOnLoad(serverLevel);
        }
    }

    private static void injectLoot(LootTable table, ImmutableList.Builder<LootPoolEntryContainer> builder)
    {
        var pool = table.getPool("main");
        pool.entries = new ArrayList<>(pool.entries);
        pool.entries.addAll(builder.build());
    }

    private void registerSpawnPlacement(RegisterSpawnPlacementsEvent event)
    {
        FishOfThieves.getSpawnPlacements().forEach(entry -> event.register(entry.type(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entry.spawnPredicate(), RegisterSpawnPlacementsEvent.Operation.OR));
    }

    private void registerAttributes(EntityAttributeCreationEvent event)
    {
        FishOfThieves.getEntityAttributes().forEach((entityType, builder) -> event.put(entityType, builder.build()));
    }

    private void onAddPackFinders(AddPackFindersEvent event)
    {
        event.addPackFinders(FishOfThieves.id("simple_spawning_condition_pack"), PackType.SERVER_DATA, Component.translatable("dataPack.simple_spawning_condition_pack.name"), PackSource.FEATURE, false, Pack.Position.TOP);
    }
}