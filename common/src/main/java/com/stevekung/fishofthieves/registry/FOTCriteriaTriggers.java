package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.trigger.ItemUsedOnLocationWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.utils.FOTPlatform;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnLocationWithNearbyEntityTrigger ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY = new ItemUsedOnLocationWithNearbyEntityTrigger();

    public static void init()
    {
        FOTPlatform.registerCriteriaTriggers(FishOfThieves.MOD_RESOURCES + "item_used_on_location_with_nearby_entity", ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY);
    }
}