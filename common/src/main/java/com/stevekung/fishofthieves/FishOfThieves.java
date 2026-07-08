package com.stevekung.fishofthieves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class FishOfThieves
{
    public static final String MOD_ID = "fishofthieves";
    public static final String MOD_RESOURCES = MOD_ID + ":";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final FishOfThievesConfig CONFIG = AutoConfig.register(FishOfThievesConfig.class, GsonConfigSerializer::new).getConfig();
    public static final Identifier RECEIVE_FISHING_HOOK_BAIT = FishOfThieves.id("receive_fishing_hook_bait");
    public static final Identifier SYNC_CLIENT_SHOAL_FISH = FishOfThieves.id("sync_client_shoal_fish");
    public static final Identifier REQUEST_SERVER_SHOAL_FISH = FishOfThieves.id("request_server_shoal_fish");

    public static Identifier id(String path)
    {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
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
        DispenserBlock.registerBehavior(FOTItems.COCONUT_BOAT, new BoatDispenseItemBehavior(FOTEntities.COCONUT_BOAT));
        DispenserBlock.registerBehavior(FOTItems.COCONUT_CHEST_BOAT, new BoatDispenseItemBehavior(FOTEntities.COCONUT_CHEST_BOAT));

        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_FRONDS, 30, 60);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_LEAVES, 30, 60);
        FOTPlatform.addFlammableBlock(FOTBlocks.VERTICAL_COCONUT_FRONDS, 30, 60);
        FOTPlatform.addFlammableBlock(FOTBlocks.VERTICAL_BANANA_LEAVES, 30, 60);
        FOTPlatform.addFlammableBlock(FOTBlocks.PINK_PLUMERIA, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.LIGHT_BLUE_PLUMERIA, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.WHITE_PLUMERIA, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_SAPLING, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_PLANKS, 5, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_FENCE, 5, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_FENCE_GATE, 5, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_SLAB, 5, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_STAIRS, 5, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_FRUIT, 5, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.SMALL_COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.SMALL_TOP_COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.SMALL_COCONUT_WOOD, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.MEDIUM_COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.MEDIUM_COCONUT_WOOD, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_WOOD, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.STRIPPED_COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.STRIPPED_COCONUT_WOOD, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.STRIPPED_SMALL_COCONUT_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_STEM, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_CLUSTER_GROWABLE_STEM, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.PRISMARIZED_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.BUDDING_PRISMARIZED_LOG, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.MANGO_LEAVES, 30, 60);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_SHOOTS_PLANT, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_SHOOTS, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.MANGO_SAPLING, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.MANGO_PIT, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.MANGO_FRUIT, 30, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.POMEGRANATE_PLANT, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.TALL_POMEGRANATE_PLANT, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_BLOSSOM, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.BANANA_BLOSSOM_PLANT, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.POMEGRANATE_SAPLING, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.TROPICAL_RED_FERN, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.TROPICAL_MONSTERA, 60, 100);
        FOTPlatform.addFlammableBlock(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.UNDERRIPE_BANANA_CLUSTER, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.RIPE_BANANA_CLUSTER, 5, 5);
        FOTPlatform.addFlammableBlock(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, 30, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, 30, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.RIPE_PINEAPPLE_BLOCK, 30, 20);
        FOTPlatform.addFlammableBlock(FOTBlocks.COCONUT_SHELF, 30, 20);
    }

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> getEntityAttributes()
    {
        return Util.make(new HashMap<>(), map ->
        {
            map.put(FOTEntities.SPLASHTAIL, AbstractSchoolingThievesFish.createAttributes());
            map.put(FOTEntities.PONDIE, AbstractSchoolingThievesFish.createAttributes());
            map.put(FOTEntities.ISLEHOPPER, AbstractSchoolingThievesFish.createAttributes());
            map.put(FOTEntities.ANCIENTSCALE, AbstractSchoolingThievesFish.createAttributes());
            map.put(FOTEntities.PLENTIFIN, AbstractSchoolingThievesFish.createAttributes());
            map.put(FOTEntities.WILDSPLASH, AbstractSchoolingThievesFish.createAttributes());
            map.put(FOTEntities.DEVILFISH, Devilfish.createAttributes());
            map.put(FOTEntities.BATTLEGILL, Battlegill.createAttributes());
            map.put(FOTEntities.WRECKER, Wrecker.createAttributes());
            map.put(FOTEntities.STORMFISH, AbstractSchoolingThievesFish.createAttributes());
        });
    }

    @SuppressWarnings("unchecked")
    public static List<SpawnPlacementEntry<Mob>> getSpawnPlacements()
    {
        return Util.make(new ArrayList<SpawnPlacementEntry<?>>(), list ->
        {
            list.add(new SpawnPlacementEntry<>(FOTEntities.SPLASHTAIL, WaterAnimal::checkSurfaceWaterAnimalSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.PONDIE, WaterAnimal::checkSurfaceWaterAnimalSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.ISLEHOPPER, Islehopper::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.ANCIENTSCALE, Ancientscale::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.PLENTIFIN, Plentifin::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.WILDSPLASH, Wildsplash::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.DEVILFISH, Devilfish::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.BATTLEGILL, Battlegill::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.WRECKER, Wrecker::checkSpawnRules));
            list.add(new SpawnPlacementEntry<>(FOTEntities.STORMFISH, Stormfish::checkSpawnRules));
        }).stream().map(entry -> (FishOfThieves.SpawnPlacementEntry<Mob>) entry).toList();
    }

    public record SpawnPlacementEntry<T extends Mob>(EntityType<T> type, SpawnPlacements.SpawnPredicate<T> spawnPredicate)
    {}
}