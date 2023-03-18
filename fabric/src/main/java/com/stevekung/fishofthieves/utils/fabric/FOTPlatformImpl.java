package com.stevekung.fishofthieves.utils.fabric;

import com.stevekung.fishofthieves.FishOfThieves;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
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

    public static EntityType<?> getMobInBucketItem(MobBucketItem bucket)
    {
        return bucket.type;
    }

    public static SoundEvent getEmptySoundInBucketItem(MobBucketItem bucket)
    {
        return bucket.emptySound;
    }

    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        CriteriaTriggers.register(trigger);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks)
    {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return FabricEntityTypeBuilder.create(MobCategory.WATER_AMBIENT, entityFactory).dimensions(dimensions).trackRangeBlocks(4).build();
    }

    public static <T extends BlockEntity> void registerBlockEntity(String key, BlockEntityType<T> type)
    {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishOfThieves.res(key), type);
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, FishOfThieves.res(key), type);
    }

    public static void registerBlock(String key, Block block)
    {
        Registry.register(BuiltInRegistries.BLOCK, FishOfThieves.res(key), block);
        registerItem(key, new BlockItem(block, new Item.Properties()));
    }

    public static void registerItem(String key, Item item)
    {
        Registry.register(BuiltInRegistries.ITEM, FishOfThieves.res(key), item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        Registry.register(BuiltInRegistries.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }

    public static void registerFeature(String key, Feature<?> feature)
    {
        Registry.register(BuiltInRegistries.FEATURE, FishOfThieves.res(key), feature);
    }
}