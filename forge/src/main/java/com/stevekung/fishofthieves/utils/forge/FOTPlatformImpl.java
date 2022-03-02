package com.stevekung.fishofthieves.utils.forge;

import com.stevekung.fishofthieves.forge.core.FishOfThievesForge;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;

public class FOTPlatformImpl
{
    public static CreativeModeTab createCreativeTab()
    {
        return new CreativeModeTab("fishofthieves.main")
        {
            @Override
            public ItemStack makeIcon()
            {
                return new ItemStack(FOTItems.SPLASHTAIL_BUCKET);
            }
        };
    }

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

    public static void registerItem(String key, Item item)
    {
        FishOfThievesForge.ITEM.register(key, () -> item);
    }

    public static void registerSoundEvent(SoundEvent soundEvent)
    {
        FishOfThievesForge.SOUND_EVENTS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }

    public static <T extends Mob> void registerSpawnPlacements(EntityType<T> type, SpawnPlacements.Type location, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate)
    {
        SpawnPlacements.register(type, location, heightmap, spawnPredicate);
    }
}