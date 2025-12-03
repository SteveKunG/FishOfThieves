package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.trigger.*;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnBlockWithNearbyEntityTrigger ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY = new ItemUsedOnBlockWithNearbyEntityTrigger();
    public static final WaterDripOnBlockTrigger WATER_DRIP_ON_BLOCK = new WaterDripOnBlockTrigger();
    public static final FallingAnvilCrushItemTrigger FALLING_ANVIL_CRUSH_ITEM = new FallingAnvilCrushItemTrigger();
    public static final ParticipateShoalTrigger PARTICIPATE_SHOAL = new ParticipateShoalTrigger();
    public static final FollowLivingWithEffectTrigger FOLLOW_LIVING_WITH_EFFECT = new FollowLivingWithEffectTrigger();

    public static void init()
    {
        FOTPlatform.registerCriteriaTriggers(ITEM_USED_ON_BLOCK_WITH_NEARBY_ENTITY);
        FOTPlatform.registerCriteriaTriggers(WATER_DRIP_ON_BLOCK);
        FOTPlatform.registerCriteriaTriggers(FALLING_ANVIL_CRUSH_ITEM);
        FOTPlatform.registerCriteriaTriggers(PARTICIPATE_SHOAL);
        FOTPlatform.registerCriteriaTriggers(FOLLOW_LIVING_WITH_EFFECT);
    }
}