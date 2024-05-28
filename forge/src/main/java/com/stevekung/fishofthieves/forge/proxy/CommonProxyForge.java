package com.stevekung.fishofthieves.forge.proxy;

import com.google.common.collect.Lists;
import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.entity.animal.*;
import com.stevekung.fishofthieves.common.registry.FOTEntities;
import com.stevekung.fishofthieves.common.registry.FOTItems;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxyForge
{
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerSpawnPlacement);
    }

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
        event.register(FOTEntities.SPLASHTAIL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.PONDIE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.ISLEHOPPER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Islehopper::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.ANCIENTSCALE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ancientscale::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.PLENTIFIN, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Plentifin::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.WILDSPLASH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wildsplash::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.DEVILFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Devilfish::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.BATTLEGILL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Battlegill::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.WRECKER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wrecker::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FOTEntities.STORMFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stormfish::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
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
}