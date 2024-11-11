package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.trigger.ItemUsedOnLocationWithNearbyEntityTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnLocationWithNearbyEntityTrigger ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY = Registry.register(BuiltInRegistries.TRIGGER_TYPES, FishOfThieves.id("item_used_on_location_with_nearby_entity"), new ItemUsedOnLocationWithNearbyEntityTrigger());

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Trigger Type");
    }
}