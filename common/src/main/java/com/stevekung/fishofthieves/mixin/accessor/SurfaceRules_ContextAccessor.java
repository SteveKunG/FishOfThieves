package com.stevekung.fishofthieves.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.chunk.ChunkAccess;

@SuppressWarnings("public-target")
@Mixin(targets = "net.minecraft.world.level.levelgen.SurfaceRules$Context")
public interface SurfaceRules_ContextAccessor
{
    @Accessor
    int getBlockX();

    @Accessor
    int getBlockY();

    @Accessor
    int getBlockZ();

    @Accessor
    ChunkAccess getChunk();
}