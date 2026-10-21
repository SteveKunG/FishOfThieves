package com.stevekung.fishofthieves.entity;

public interface FakeWaterEntity
{
    default void fishofthieves$setIsInFakeWater()
    {
        throw new AssertionError("Implemented via mixin");
    }
}