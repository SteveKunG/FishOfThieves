package com.stevekung.fishofthieves.entity;

public interface PartyFish
{
    default boolean isPartying()
    {
        return false;
    }
}