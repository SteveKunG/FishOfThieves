package com.stevekung.fishofthieves.mixin.level.levelgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.stevekung.fishofthieves.levelgen.ChunkAccessExtender;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;

@Mixin(RandomState.class)
public class MixinRandomState implements ChunkAccessExtender
{
    @Unique
    private ChunkAccess chunkAccess;

    @Override
    public ChunkAccess fishofthieves$getChunkAccess()
    {
        return this.chunkAccess;
    }

    @Override
    public void fishofthieves$setChunkAccess(ChunkAccess chunkAccess)
    {
        this.chunkAccess = chunkAccess;
    }
}