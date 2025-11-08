package com.stevekung.fishofthieves.loot.function;

public interface LootPoolSingletonContainerWeightAdder
{
    default void fishofthieves$addWeight(int weight)
    {
        throw new AssertionError("Implemented via mixin");
    }
}