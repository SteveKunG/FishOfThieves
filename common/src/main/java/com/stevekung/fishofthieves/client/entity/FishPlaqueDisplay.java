package com.stevekung.fishofthieves.client.entity;

public interface FishPlaqueDisplay
{
    default void fishofthieves$setIsInFishPlaque(boolean inFishPlaque)
    {
        throw new AssertionError("Implemented via mixin");
    }
}