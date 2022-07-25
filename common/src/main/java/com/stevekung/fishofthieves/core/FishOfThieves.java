package com.stevekung.fishofthieves.core;

import java.util.List;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variants.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.variants.IslehopperVariant;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
import com.stevekung.fishofthieves.registry.variants.SplashtailVariant;
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
        SplashtailVariant.init();
        PondieVariant.init();
        IslehopperVariant.init();
        AncientscaleVariant.init();
        FOTDataSerializers.init();

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
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.SPLASHTAIL, 6, FOTItems.COOKED_SPLASHTAIL, 6, 16, 5));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PONDIE, 4, FOTItems.COOKED_PONDIE, 4, 13, 3));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ISLEHOPPER, 8, FOTItems.COOKED_ISLEHOPPER, 2, 6, 4));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ANCIENTSCALE, 10, FOTItems.COOKED_ANCIENTSCALE, 2, 4, 6));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PLENTIFIN, 9, FOTItems.COOKED_PLENTIFIN, 3, 5, 8));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WILDSPLASH, 5, FOTItems.COOKED_WILDSPLASH, 2, 6, 10));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.DEVILFISH, 2, FOTItems.COOKED_DEVILFISH, 8, 4, 15));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.BATTLEGILL, 2, FOTItems.COOKED_BATTLEGILL, 12, 4, 18));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WRECKER, 1, FOTItems.COOKED_WRECKER, 14, 4, 20));
        list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.STORMFISH, 1, FOTItems.COOKED_STORMFISH, 20, 5, 30));
    }

    public static void getTierTwoTrades(List<VillagerTrades.ItemListing> list)
    {
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.SPLASHTAIL, 13, 16, 20));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.PONDIE, 10, 12, 16));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.ISLEHOPPER, 16, 8, 25));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.ANCIENTSCALE, 16, 4, 28));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.PLENTIFIN, 12, 6, 25));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.WILDSPLASH, 10, 4, 26));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.DEVILFISH, 12, 2, 30));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.BATTLEGILL, 14, 2, 30));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.WRECKER, 15, 1, 30));
        list.add(new VillagerTrades.EmeraldForItems(FOTItems.STORMFISH, 20, 1, 30));
    }
}