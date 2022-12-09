package com.stevekung.fishofthieves.utils;

import com.stevekung.fishofthieves.registry.variants.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FOTPlatform
{
    @ExpectPlatform
    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
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
    public static void registerSplashtailVariant(String key, SplashtailVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPondieVariant(String key, PondieVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerIslehopperVariant(String key, IslehopperVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerAncientscaleVariant(String key, AncientscaleVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPlentifinVariant(String key, PlentifinVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerWildsplashVariant(String key, WildsplashVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerDevilfishVariant(String key, DevilfishVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBattlegillVariant(String key, BattlegillVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerWreckerVariant(String key, WreckerVariant variant)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerStormfishVariant(String key, StormfishVariant variant)
    {
        throw new AssertionError();
    }
}