package com.stevekung.fishofthieves.utils.forge;

import com.stevekung.fishofthieves.forge.core.FishOfThievesForge;
import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FOTPlatformImpl
{
    public static void registerCriteriaTriggers(CriterionTrigger<?> trigger)
    {
        CriteriaTriggers.register(trigger);
    }

    public static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width, dimensions.height).clientTrackingRange(4).build("");
    }

    public static <T extends Entity> void registerEntityType(String key, EntityType<T> type)
    {
        FishOfThievesForge.ENTITY.register(key, () -> type);
    }

    public static void registerBlock(String key, Block block)
    {
        FishOfThievesForge.BLOCK.register(key, () -> block);
        registerItem(key, new BlockItem(block, new Item.Properties()));
    }

    public static void registerItem(String key, Item item)
    {
        FishOfThievesForge.ITEM.register(key, () -> item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        FishOfThievesForge.SOUND_EVENTS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }

    public static void registerSplashtailVariant(String key, SplashtailVariant variant)
    {
        FishOfThievesForge.SPLASHTAIL_VARIANT.register(key, () -> variant);
    }

    public static void registerPondieVariant(String key, PondieVariant variant)
    {
        FishOfThievesForge.PONDIE_VARIANT.register(key, () -> variant);
    }

    public static void registerIslehopperVariant(String key, IslehopperVariant variant)
    {
        FishOfThievesForge.ISLEHOPPER_VARIANT.register(key, () -> variant);
    }

    public static void registerAncientscaleVariant(String key, AncientscaleVariant variant)
    {
        FishOfThievesForge.ANCIENTSCALE_VARIANT.register(key, () -> variant);
    }

    public static void registerPlentifinVariant(String key, PlentifinVariant variant)
    {
        FishOfThievesForge.PLENTIFIN_VARIANT.register(key, () -> variant);
    }

    public static void registerWildsplashVariant(String key, WildsplashVariant variant)
    {
        FishOfThievesForge.WILDSPLASH_VARIANT.register(key, () -> variant);
    }

    public static void registerDevilfishVariant(String key, DevilfishVariant variant)
    {
        FishOfThievesForge.DEVILFISH_VARIANT.register(key, () -> variant);
    }

    public static void registerBattlegillVariant(String key, BattlegillVariant variant)
    {
        FishOfThievesForge.BATTLEGILL_VARIANT.register(key, () -> variant);
    }

    public static void registerWreckerVariant(String key, WreckerVariant variant)
    {
        FishOfThievesForge.WRECKER_VARIANT.register(key, () -> variant);
    }

    public static void registerStormfishVariant(String key, StormfishVariant variant)
    {
        FishOfThievesForge.STORMFISH_VARIANT.register(key, () -> variant);
    }
}