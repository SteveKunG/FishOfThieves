package com.stevekung.fishofthieves.forge.predicates;

import com.stevekung.fishofthieves.forge.registry.FOTLootItemConditionsForge;
import com.stevekung.fishofthieves.predicates.FOTLocationCheck;
import com.stevekung.fishofthieves.predicates.FOTLocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FOTLocationCheckForge extends FOTLocationCheck
{
    protected FOTLocationCheckForge(FOTLocationPredicate locationPredicate, BlockPos blockPos)
    {
        super(locationPredicate, blockPos);
    }

    @Override
    public LootItemConditionType getType()
    {
        return FOTLootItemConditionsForge.FOT_LOCATION_CHECK;
    }

    public static LootItemCondition.Builder checkLocation(FOTLocationPredicate.Builder locationPredicateBuilder)
    {
        return () -> new FOTLocationCheckForge(locationPredicateBuilder.build(), BlockPos.ZERO);
    }
}