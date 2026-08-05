package com.stevekung.fishofthieves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.stevekung.fishofthieves.api.block.FishPlaqueRegistry;
import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.item.trade.FOTItemsForEmeralds;
import com.stevekung.fishofthieves.item.trade.TreasuredFishMapForEmeralds;
import com.stevekung.fishofthieves.registry.*;

import net.minecraft.Util;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.DispenserBlock;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class FishOfThieves
{
    public static final String MOD_ID = "fishofthieves";
    public static final String MOD_RESOURCES = MOD_ID + ":";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final FishOfThievesConfig CONFIG = AutoConfig.register(FishOfThievesConfig.class, GsonConfigSerializer::new).getConfig();

    public static final GameRules.Key<GameRules.BooleanValue> SHOAL_SPAWNING = FOTPlatform.registerGameRule("shoal_spawning", GameRules.Category.SPAWNING, FOTPlatform.getGameRuleBoolean(true));

    public static final ResourceLocation RECEIVE_FISHING_HOOK_BAIT = FishOfThieves.id("receive_fishing_hook_bait");
    public static final ResourceLocation STRUCTURE_CENTER_POS_DEBUG = FishOfThieves.id("structure_center_pos_debug");
    public static final ResourceLocation SYNC_CLIENT_SHOAL_FISH = FishOfThieves.id("sync_client_shoal_fish");
    public static final ResourceLocation REQUEST_SERVER_SHOAL_FISH = FishOfThieves.id("request_server_shoal_fish");

    public static ResourceLocation id(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init()
    {
        FOTSoundEvents.init();
        FOTCriteriaTriggers.init();
    }

    public static void initCommon()
    {
        FOTDataSerializers.init();
        FOTLootItemFunctions.init();
        FOTLootPoolEntries.init();
        FOTStructures.init();
        FOTEntitySubPredicate.init();
        FOTBlockPredicateTypes.init();

        // Naturalist compatibility
        if (FOTPlatform.isModLoaded("naturalist"))
        {
            FishPlaqueRegistry.registerInteractionItem(Items.BUCKET, "naturalist:snail");
        }
        // Alex's Mobs compatibility
        if (FOTPlatform.isModLoaded("alexsmobs"))
        {
            FishPlaqueRegistry.registerInteractionItem(Items.BUCKET, "alexsmobs:cosmic_cod");
            FishPlaqueRegistry.registerInteractionItem(Items.LAVA_BUCKET, "alexsmobs:stradpole");
        }
        // Alex's Caves compatibility
        if (FOTPlatform.isModLoaded("alexscaves"))
        {
            FishPlaqueRegistry.registerInteractionItem(BuiltInRegistries.ITEM.get(new ResourceLocation("alexscaves", "acid_bucket")), "alexscaves:radgill");
        }

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
        DispenserBlock.registerBehavior(FOTItems.COCONUT_BOAT, new BoatDispenseItemBehavior(FOTBoatTypes.COCONUT));
        DispenserBlock.registerBehavior(FOTItems.COCONUT_CHEST_BOAT, new BoatDispenseItemBehavior(FOTBoatTypes.COCONUT, true));

        PotionBrewing.addMix(Potions.AWKWARD, FOTItems.PLENTIFIN, Potions.LUCK);
        PotionBrewing.addMix(Potions.AWKWARD, FOTItems.ISLEHOPPER, Potions.WATER_BREATHING);

        FOTPlatform.addComposting(FOTItems.EARTHWORMS, 0.4F);
        FOTPlatform.addComposting(FOTItems.GRUBS, 0.4F);
        FOTPlatform.addComposting(FOTItems.LEECHES, 0.4F);
        FOTPlatform.addComposting(FOTItems.COCONUT, 0.5F);
        FOTPlatform.addComposting(FOTItems.BANANA, 0.3F);
        FOTPlatform.addComposting(FOTItems.PINEAPPLE, 0.7F);
        FOTPlatform.addComposting(FOTItems.CROWNLESS_PINEAPPLE, 0.65F);
        FOTPlatform.addComposting(FOTItems.HALF_PINEAPPLE, 0.35F);
        FOTPlatform.addComposting(FOTItems.PINEAPPLE_SEEDS, 0.1F);
        FOTPlatform.addComposting(FOTItems.PINEAPPLE_CROWN, 0.15F);
        FOTPlatform.addComposting(FOTItems.MANGO, 0.3F);
        FOTPlatform.addComposting(FOTItems.RAW_MANGO, 0.3F);
        FOTPlatform.addComposting(FOTItems.MANGO_PIT, 0.2F);
        FOTPlatform.addComposting(FOTItems.POMEGRANATE, 0.2F);
        FOTPlatform.addComposting(FOTItems.POMEGRANATE_SEEDS, 0.1F);
        FOTPlatform.addComposting(FOTItems.PINK_PLUMERIA, 0.6F);
        FOTPlatform.addComposting(FOTItems.LIGHT_BLUE_PLUMERIA, 0.6F);
        FOTPlatform.addComposting(FOTItems.WHITE_PLUMERIA, 0.6F);
        FOTPlatform.addComposting(FOTItems.COCONUT_FRONDS, 0.8F);
        FOTPlatform.addComposting(FOTItems.BANANA_LEAVES, 0.8F);
        FOTPlatform.addComposting(FOTItems.BANANA_BLOSSOM, 0.3F);
        FOTPlatform.addComposting(FOTItems.BANANA_SHOOTS, 0.25F);
        FOTPlatform.addComposting(FOTItems.UNDERRIPE_BANANA_CLUSTER, 0.5F);
        FOTPlatform.addComposting(FOTItems.BARELY_RIPE_BANANA_CLUSTER, 0.85F);
        FOTPlatform.addComposting(FOTItems.RIPE_BANANA_CLUSTER, 0.85F);
        FOTPlatform.addComposting(FOTItems.BANANA_STEM, 0.8F);
        FOTPlatform.addComposting(FOTItems.UNDERRIPE_PINEAPPLE_BLOCK, 0.6F);
        FOTPlatform.addComposting(FOTItems.RIPE_PINEAPPLE_BLOCK, 0.75F);
        FOTPlatform.addComposting(FOTItems.CROWNLESS_RIPE_PINEAPPLE_BLOCK, 0.7F);
        FOTPlatform.addComposting(FOTItems.MANGO_LEAVES, 0.3F);
        FOTPlatform.addComposting(FOTItems.MANGO_SAPLING, 0.25F);
        FOTPlatform.addComposting(FOTItems.POMEGRANATE_PLANT, 0.25F);
        FOTPlatform.addComposting(FOTItems.TALL_POMEGRANATE_PLANT, 0.4F);
        FOTPlatform.addComposting(FOTItems.TROPICAL_RED_FERN, 0.4F);
        FOTPlatform.addComposting(FOTItems.TROPICAL_MONSTERA, 0.3F);
        FOTPlatform.addComposting(FOTItems.GUARDIAN_FRUIT, 0.3F);

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
    }

    private static List<VillagerTrades.ItemListing> getFishermanTradesByLevel(int level, List<VillagerTrades.ItemListing> list)
    {
        switch (level)
        {
            case 1 ->
            {
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.SPLASHTAIL, 6, FOTItems.COOKED_SPLASHTAIL, 6, 8, 1));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PONDIE, 6, FOTItems.COOKED_PONDIE, 6, 8, 1));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ISLEHOPPER, 2, 2, FOTItems.COOKED_ISLEHOPPER, 2, 8, 2));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.ANCIENTSCALE, 3, FOTItems.COOKED_ANCIENTSCALE, 3, 5, 3));

                list.add(new VillagerTrades.EmeraldForItems(FOTItems.EARTHWORMS, 48, 8, 10));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.GRUBS, 32, 8, 12));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.LEECHES, 24, 8, 14));

                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.SPLASHTAIL_BUCKET), 3, 1, 16, 1));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.PONDIE_BUCKET), 3, 1, 16, 1));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.ISLEHOPPER_BUCKET), 3, 1, 16, 1));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.ANCIENTSCALE_BUCKET), 3, 1, 16, 1));
            }
            case 2 ->
            {
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.SPLASHTAIL, 12, 8, 12));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.PONDIE, 12, 8, 12));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.ISLEHOPPER, 8, 8, 15));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.ANCIENTSCALE, 8, 9, 15));

                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.PLENTIFIN, 3, 2, FOTItems.COOKED_PLENTIFIN, 3, 5, 3));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WILDSPLASH, 4, 2, FOTItems.COOKED_WILDSPLASH, 4, 6, 3));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.DEVILFISH, 4, 3, FOTItems.COOKED_DEVILFISH, 4, 6, 4));

                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.PLENTIFIN_BUCKET), 3, 1, 16, 1));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.WILDSPLASH_BUCKET), 3, 1, 16, 1));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.DEVILFISH_BUCKET), 3, 1, 16, 1));
            }
            case 3 ->
            {
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.PLENTIFIN, 8, 9, 17));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.WILDSPLASH, 8, 9, 17));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.DEVILFISH, 6, 10, 20));
                list.add(new TreasuredFishMapForEmeralds(Shoal.FILLED_MAP_TREASURED_FISH, 1, 20, 1));
            }
            case 4 ->
            {
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.BATTLEGILL, 6, 10, 20));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.WRECKER, 5, 12, 25));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.STORMFISH, 5, 12, 25));
                list.add(new TreasuredFishMapForEmeralds(Shoal.FILLED_MAP_TREASURED_FISH, 1, 24, 2));
            }
            case 5 ->
            {
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.BATTLEGILL, 4, 3, FOTItems.COOKED_BATTLEGILL, 4, 6, 4));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.WRECKER, 5, 5, FOTItems.COOKED_WRECKER, 5, 8, 5));
                list.add(new VillagerTrades.ItemsAndEmeraldsToItems(FOTItems.STORMFISH, 5, 5, FOTItems.COOKED_STORMFISH, 5, 8, 8));

                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.BATTLEGILL_BUCKET), 6, 1, 8, 2));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.WRECKER_BUCKET), 6, 1, 8, 2));
                list.add(new FOTItemsForEmeralds(FOTMobBucketItem.createRandomBucket(FOTItems.STORMFISH_BUCKET), 6, 1, 8, 2));
            }
        }
        return list;
    }

    private static List<VillagerTrades.ItemListing> getFarmerTradesByLevel(int level, List<VillagerTrades.ItemListing> list)
    {
        switch (level)
        {
            case 1 ->
            {
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.BANANA, 10, 16, 2));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.COCONUT, 6, 12, 2));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.POMEGRANATE, 8, 12, 4));
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.RAW_MANGO, 6, 12, 3));
            }
            case 2 ->
            {
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.MANGO, 8, 10, 6));
            }
            case 3 ->
            {
                list.add(new VillagerTrades.EmeraldForItems(FOTItems.PINEAPPLE, 2, 6, 5));
                list.add(new VillagerTrades.ItemsForEmeralds(FOTItems.MANGO, 1, 3, 10, 8));
            }
            case 4 ->
            {
                list.add(new VillagerTrades.ItemsForEmeralds(FOTItems.PINEAPPLE, 1, 1, 5, 10));
            }
        }
        return list;
    }

    public static Int2ObjectOpenHashMap<Function<List<VillagerTrades.ItemListing>, List<VillagerTrades.ItemListing>>> getFishermanTrades()
    {
        return Util.make(new Int2ObjectOpenHashMap<>(), map -> IntStream.rangeClosed(1, 5)
                .boxed()
                .forEach(level -> map.put((int)level, list -> getFishermanTradesByLevel(level, list))));
    }

    public static Int2ObjectOpenHashMap<Function<List<VillagerTrades.ItemListing>, List<VillagerTrades.ItemListing>>> getFarmerTrades()
    {
        return Util.make(new Int2ObjectOpenHashMap<>(), map -> IntStream.rangeClosed(1, 4)
                .boxed()
                .forEach(level -> map.put((int)level, list -> getFarmerTradesByLevel(level, list))));
    }

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> getEntityAttributes()
    {
        return Util.make(new HashMap<>(), map ->
        {
            map.put(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes());
            map.put(FOTEntities.PONDIE, AbstractFish.createAttributes());
            map.put(FOTEntities.ISLEHOPPER, AbstractFish.createAttributes());
            map.put(FOTEntities.ANCIENTSCALE, AbstractFish.createAttributes());
            map.put(FOTEntities.PLENTIFIN, AbstractFish.createAttributes());
            map.put(FOTEntities.WILDSPLASH, AbstractFish.createAttributes());
            map.put(FOTEntities.DEVILFISH, Devilfish.createAttributes());
            map.put(FOTEntities.BATTLEGILL, Battlegill.createAttributes());
            map.put(FOTEntities.WRECKER, Wrecker.createAttributes());
            map.put(FOTEntities.STORMFISH, AbstractFish.createAttributes());
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