package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.trigger.ItemUsedOnBlockWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.trigger.WaterDripOnBlockTrigger;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnBlockWithNearbyEntityTrigger ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY = new ItemUsedOnBlockWithNearbyEntityTrigger();
    public static final WaterDripOnBlockTrigger WATER_DRIP_ON_BLOCK = new WaterDripOnBlockTrigger();

    public static void init()
    {
        FOTPlatform.registerCriteriaTriggers(ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY);
        FOTPlatform.registerCriteriaTriggers(WATER_DRIP_ON_BLOCK);
    }
}