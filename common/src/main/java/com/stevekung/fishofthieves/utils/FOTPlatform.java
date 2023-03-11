package com.stevekung.fishofthieves.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FOTPlatform
{
    @ExpectPlatform
    public static boolean isModLoaded(String modId)
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
    public static CreativeModeTab createCreativeTab()
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
}