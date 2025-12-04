package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.trigger.*;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTCriteriaTriggers
{
    public static final ItemUsedOnLocationWithNearbyEntityTrigger ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY = register("item_used_on_location_with_nearby_entity", new ItemUsedOnLocationWithNearbyEntityTrigger());
    public static final WaterDripOnBlockTrigger WATER_DRIP_ON_BLOCK = register("water_drip_on_block", new WaterDripOnBlockTrigger());
    public static final FallingAnvilCrushItemTrigger FALLING_ANVIL_CRUSH_ITEM = register("falling_anvil_crush_item", new FallingAnvilCrushItemTrigger());
    public static final ParticipateShoalTrigger PARTICIPATE_SHOAL = register("participate_shoal", new ParticipateShoalTrigger());
    public static final FollowLivingWithEffectTrigger FOLLOW_LIVING_WITH_EFFECT = register("follow_living_with_effect", new FollowLivingWithEffectTrigger());

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Trigger Type");
    }

    private static <T extends CriterionTrigger<?>> T register(String key, T type)
    {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, FishOfThieves.id(key), type);
    }
}