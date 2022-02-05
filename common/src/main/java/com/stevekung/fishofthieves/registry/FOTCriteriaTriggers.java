package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.trigger.ItemUsedOnBlockWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.utils.FOTPlatform;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnBlockWithNearbyEntityTrigger ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY = new ItemUsedOnBlockWithNearbyEntityTrigger();

    public static void init()
    {
        FOTPlatform.registerCriteriaTriggers(ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY);
    }
}