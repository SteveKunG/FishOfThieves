package com.stevekung.fishofthieves.entity;

import java.util.function.Predicate;

import com.stevekung.fishofthieves.spawn.SpawnConditionContext;

public interface FishVariant
{
    String getName();
    int getId();
    Predicate<SpawnConditionContext> getCondition();
}