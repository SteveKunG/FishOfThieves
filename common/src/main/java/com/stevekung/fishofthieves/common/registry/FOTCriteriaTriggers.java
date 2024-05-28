package com.stevekung.fishofthieves.common.registry;

import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.trigger.ItemUsedOnLocationWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.common.utils.FOTPlatform;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnLocationWithNearbyEntityTrigger ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY = new ItemUsedOnLocationWithNearbyEntityTrigger();

    public static void init()
    {
        FOTPlatform.registerCriteriaTriggers(FishOfThieves.MOD_RESOURCES + "item_used_on_location_with_nearby_entity", ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY);
    }
}