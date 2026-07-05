package com.stevekung.fishofthieves.forge.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.compatibility.biolith.FOTBiolith;
import com.stevekung.fishofthieves.loot.FOTLootManager;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxyForge
{
    public void init()
    {
        var context = FMLJavaModLoadingContext.get();
        MinecraftForge.EVENT_BUS.register(this);
        context.getModEventBus().addListener(this::commonSetup);
        context.getModEventBus().addListener(this::registerAttributes);
        context.getModEventBus().addListener(this::registerSpawnPlacement);
    }

    public void commonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            FishOfThieves.initCommon();

            if (FishOfThieves.CONFIG.biome.tropicalIslandBiomeGeneration && FOTPlatform.isModLoaded("biolith"))
            {
                FOTBiolith.init();
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
    public void onLootTableLoad(LootTableLoadEvent event)
    {
        var table = event.getTable();
        var id = event.getName();

        FOTLootManager.getInjectedLootTableMap().forEach((resourceKey, function) ->
        {
            if (id.equals(resourceKey))
            {
                injectLoot(table, id.toString(), function.apply(LootPool.lootPool()).entries);
            }
        });
        FOTLootManager.getInjectedLootPoolMap().forEach((resourceKey, function) ->
        {
            if (id.equals(resourceKey))
            {
                table.addPool(function.apply(LootPool.lootPool()).build());
            }
        });
    }

    @SubscribeEvent
    public void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.FISHERMAN)
        {
            var trades = event.getTrades();
            FishOfThieves.getFishermanTrades().forEach((level, factories) -> trades.get(level.intValue()).addAll(factories.apply(new ArrayList<>())));
        }
        else if (event.getType() == VillagerProfession.FARMER)
        {
            var trades = event.getTrades();
            FishOfThieves.getFarmerTrades().forEach((level, factories) -> trades.get(level.intValue()).addAll(factories.apply(new ArrayList<>())));
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

    private void registerSpawnPlacement(SpawnPlacementRegisterEvent event)
    {
        FishOfThieves.getSpawnPlacements().forEach(entry -> event.register(entry.type(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entry.spawnPredicate(), SpawnPlacementRegisterEvent.Operation.OR));
    }

    private void registerAttributes(EntityAttributeCreationEvent event)
    {
        FishOfThieves.getEntityAttributes().forEach((entityType, builder) -> event.put(entityType, builder.build()));
    }

    private static void injectLoot(LootTable table, String id, List<LootPoolEntryContainer> entries)
    {
        var pool = Objects.requireNonNullElse(table.getPool("main"), table.getPool("pool0"));

        //noinspection ConstantValue
        if (pool != null)
        {
            pool.entries = ArrayUtils.addAll(pool.entries, entries.toArray(LootPoolEntryContainer[]::new));
        }
        else
        {
            FishOfThieves.LOGGER.error("Couldn't inject loot into {}, please report to developer.", id);
        }
    }
}