package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class FOTPlatform
{
    @ExpectPlatform
    public static boolean isModLoaded(String modId)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDevelopment()
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addComposting(Item item, float value)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addFlammableBlock(Block block, int encouragement, int flammability)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityType<?> getMobInBucketItem(MobBucketItem bucket)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SoundEvent getEmptySoundInBucketItem(MobBucketItem bucket)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BiomeSpecialEffects.GrassColorModifier getTropicalIslandGrassColor()
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory, EntityDimensions dimensions, int clientTrackingRange)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBlock(String key, Block block)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBlockWithItem(String key, Block block)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItem(String key, Item item)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerFeature(String key, Feature<?> feature)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <U extends Sensor<?>> void registerSensorType(String key, SensorType<U> sensorType)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerMemoryModuleType(String key, MemoryModuleType<?> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <P extends TreeDecorator> void registerTreeDecoratorType(String key, TreeDecoratorType<P> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <P extends FoliagePlacer> void registerFoliagePlacerType(String key, FoliagePlacerType<P> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <P extends BlockStateProvider> void registerBlockStateProviderType(String key, BlockStateProviderType<P> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String name, GameRules.Category category, GameRules.Type<T> type)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static GameRules.Type<GameRules.BooleanValue> getGameRuleBoolean(boolean defaultValue)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerMobEffect(int id, String key, MobEffect mobEffect)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendFishingHookBait(Player player, int entityId, ItemStack itemStack)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void syncShoalFish(Shoal shoal)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void requestShoalFish(Shoal shoal)
    {
        throw new AssertionError();
    }
}