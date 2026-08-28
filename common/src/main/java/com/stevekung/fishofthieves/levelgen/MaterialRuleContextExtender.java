package com.stevekung.fishofthieves.levelgen;

import net.minecraft.world.level.chunk.ChunkAccess;

public interface MaterialRuleContextExtender
{
    default ChunkAccess getChunkAccess()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void setChunkAccess(ChunkAccess chunkAccess)
    {
        throw new AssertionError("Implemented via mixin");
    }
}