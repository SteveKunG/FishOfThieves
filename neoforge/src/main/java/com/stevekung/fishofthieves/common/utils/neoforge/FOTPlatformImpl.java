package com.stevekung.fishofthieves.common.utils.neoforge;

import com.stevekung.fishofthieves.neoforge.FishOfThievesNeoForge;
import com.stevekung.fishofthieves.neoforge.mixin.MobBucketItemAccessor;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.fml.ModList;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return ModList.get().isLoaded(modId);
    }

    public static EntityType<?> getMobInBucketItem(MobBucketItem bucket)
    {
        return ((MobBucketItemAccessor) bucket).invokeGetFishType();
    }

    public static SoundEvent getEmptySoundInBucketItem(MobBucketItem bucket)
    {
        return ((MobBucketItemAccessor) bucket).invokeGetEmptySound();
    }

    public static void registerCriteriaTriggers(String key, CriterionTrigger<?> trigger)
    {
        CriteriaTriggers.register(key, trigger);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width, dimensions.height).clientTrackingRange(4).build("");
    }

    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        FishOfThievesNeoForge.BLOCK_ENTITY_TYPE.register(key, () -> type);
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        FishOfThievesNeoForge.ENTITY.register(key, () -> type);
    }

    public static void registerBlock(String key, Block block)
    {
        FishOfThievesNeoForge.BLOCK.register(key, () -> block);
        registerItem(key, new BlockItem(block, new Item.Properties()));
    }

    public static void registerItem(String key, Item item)
    {
        FishOfThievesNeoForge.ITEM.register(key, () -> item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        FishOfThievesNeoForge.SOUND_EVENTS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }

    public static void registerFeature(String key, Feature<?> feature)
    {
        FishOfThievesNeoForge.FEATURES.register(key, () -> feature);
    }

    public static <U extends Sensor<?>> void registerSensorType(String key, SensorType<U> sensorType)
    {
        FishOfThievesNeoForge.SENSOR_TYPES.register(key, () -> sensorType);
    }

    public static void registerMemoryModuleType(String key, MemoryModuleType<?> type)
    {
        FishOfThievesNeoForge.MEMORY_MODULE_TYPES.register(key, () -> type);
    }
}