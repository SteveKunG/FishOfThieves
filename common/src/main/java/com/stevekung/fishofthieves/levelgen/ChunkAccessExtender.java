package com.stevekung.fishofthieves.levelgen;

import net.minecraft.world.level.chunk.ChunkAccess;

public interface ChunkAccessExtender
{
    default ChunkAccess fishofthieves$getChunkAccess()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setChunkAccess(ChunkAccess chunkAccess)
    {
        throw new AssertionError("Implemented via mixin");
    }
}