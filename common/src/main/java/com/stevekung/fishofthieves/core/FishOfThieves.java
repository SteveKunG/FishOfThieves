package com.stevekung.fishofthieves.core;

import java.util.List;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.Heightmap;

public class FishOfThieves
{
    public static final String MOD_ID = "fishofthieves";
    public static final String MOD_RESOURCES = MOD_ID + ":";
    public static final CreativeModeTab FOT_TAB = FOTPlatform.createCreativeTab();
    public static final Logger LOGGER = LogUtils.getLogger();
    public static FishOfThievesConfig CONFIG;

    public static void init()
    {
        FOTSoundEvents.init();
        FOTCriteriaTriggers.init();
        AutoConfig.register(FishOfThievesConfig.class, GsonConfigSerializer::new);
        FishOfThieves.CONFIG = AutoConfig.getConfigHolder(FishOfThievesConfig.class).getConfig();
    }

    public static void initCommon()
    {
        var bucket = DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);
        DispenserBlock.registerBehavior(FOTItems.SPLASHTAIL_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.PONDIE_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.ISLEHOPPER_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.ANCIENTSCALE_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.PLENTIFIN_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.WILDSPLASH_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.DEVILFISH_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.BATTLEGILL_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.WRECKER_BUCKET, bucket);
        DispenserBlock.registerBehavior(FOTItems.STORMFISH_BUCKET, bucket);

        FOTPlatform.registerSpawnPlacements(FOTEntities.SPLASHTAIL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.PONDIE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.ISLEHOPPER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Islehopper::checkSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.ANCIENTSCALE, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.PLENTIFIN, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Plentifin::checkSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.WILDSPLASH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wildsplash::checkSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.DEVILFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Devilfish::checkSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.BATTLEGILL, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Battlegill::checkSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.WRECKER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wrecker::checkSpawnRules);
        FOTPlatform.registerSpawnPlacements(FOTEntities.STORMFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stormfish::checkSpawnRules);
    }

    public static void getTierOneTrades(List<VillagerTrades.ItemListing> list)
    {
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.SPLASHTAIL, 6, FOTItems.COOKED_SPLASHTAIL, 6, 8, 1));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PONDIE, 6, FOTItems.COOKED_PONDIE, 6, 8, 1));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ISLEHOPPER, 2, 2, FOTItems.COOKED_ISLEHOPPER, 2, 8, 2));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ANCIENTSCALE, 3, FOTItems.COOKED_ANCIENTSCALE, 3, 5, 3));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PLENTIFIN, 3, 2, FOTItems.COOKED_PLENTIFIN, 3, 5, 3));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WILDSPLASH, 4, 2, FOTItems.COOKED_WILDSPLASH, 4, 6, 3));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.DEVILFISH, 4, 3, FOTItems.COOKED_DEVILFISH, 4, 6, 4));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.BATTLEGILL, 4, 3, FOTItems.COOKED_BATTLEGILL, 4, 6, 4));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WRECKER, 5, 5, FOTItems.COOKED_WRECKER, 5, 8, 5));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.STORMFISH, 5, 5, FOTItems.COOKED_STORMFISH, 5, 8, 8));
    }

    public static void getTierTwoTrades(List<VillagerTrades.ItemListing> list)
    {
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.SPLASHTAIL, 12, 8, 12));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.PONDIE, 12, 8, 12));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.ISLEHOPPER, 8, 8, 15));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.ANCIENTSCALE, 8, 9, 15));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.PLENTIFIN, 8, 9, 17));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.WILDSPLASH, 8, 9, 17));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.DEVILFISH, 6, 10, 20));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.BATTLEGILL, 6, 10, 20));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.WRECKER, 5, 12, 25));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.STORMFISH, 5, 12, 25));
    }
}