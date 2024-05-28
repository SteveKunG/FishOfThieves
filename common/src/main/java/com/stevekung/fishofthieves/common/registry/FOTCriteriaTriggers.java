package com.stevekung.fishofthieves.common.registry;

import com.stevekung.fishofthieves.common.trigger.ItemUsedOnBlockWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.common.utils.FOTPlatform;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnBlockWithNearbyEntityTrigger ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY = new ItemUsedOnBlockWithNearbyEntityTrigger();

    public static void init()
    {
        FOTPlatform.registerCriteriaTriggers(ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY);
    }
}