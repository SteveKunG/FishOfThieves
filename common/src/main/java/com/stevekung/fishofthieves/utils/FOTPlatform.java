package com.stevekung.fishofthieves.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;

public class FOTPlatform
{
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
    public static <T extends Mob> void registerSpawnPlacements(EntityType<T> type, SpawnPlacements.Type location, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate)
    {
        throw new AssertionError();
    }
}