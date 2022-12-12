package com.stevekung.fishofthieves.entity;

import net.minecraft.core.BlockPos;

public interface PartyFish
{
    default boolean isDancing()
    {
        return false;
    }

    default void setJukeboxPlaying(BlockPos jukeboxPos, boolean jukeboxPlaying) {}
}