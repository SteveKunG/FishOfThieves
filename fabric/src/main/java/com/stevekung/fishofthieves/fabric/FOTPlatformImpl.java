package com.stevekung.fishofthieves.fabric;

import java.util.Set;

import com.stevekung.fishofthieves.FishOfThieves;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FOTPlatformImpl
{
    public static boolean isModLoaded(String modId)
    {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static void registerCriteriaTriggers(String key, CriterionTrigger<?> trigger)
    {
        CriteriaTriggers.register(key, trigger);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return new BlockEntityType<>(factory, Set.of(validBlocks));
    }

    public static <T extends Entity> EntityType<T> createEntityType(String key, EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width(), dimensions.height()).clientTrackingRange(4).build(ResourceKey.create(Registries.ENTITY_TYPE, FishOfThieves.id(key)));
    }

    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishOfThieves.id(key), type);
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, FishOfThieves.id(key), type);
    }

    public static void registerBlock(String key, Block block)
    {
        Registry.register(BuiltInRegistries.BLOCK, FishOfThieves.id(key), block);
        registerItem(key, new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, FishOfThieves.id(key))).useBlockDescriptionPrefix()));
    }

    public static void registerItem(String key, Item item)
    {
        Registry.register(BuiltInRegistries.ITEM, FishOfThieves.id(key), item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        Registry.register(BuiltInRegistries.SOUND_EVENT, soundEvent.location().getPath(), soundEvent);
    }

    public static void registerFeature(String key, Feature<?> feature)
    {
        Registry.register(BuiltInRegistries.FEATURE, FishOfThieves.id(key), feature);
    }

    public static <U extends Sensor<?>> void registerSensorType(String key, SensorType<U> sensorType)
    {
        Registry.register(BuiltInRegistries.SENSOR_TYPE, FishOfThieves.id(key), sensorType);
    }

    public static void registerMemoryModuleType(String key, MemoryModuleType<?> type)
    {
        Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, FishOfThieves.id(key), type);
    }
}