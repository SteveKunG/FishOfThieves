package com.stevekung.fishofthieves.mixin.level.levelgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.ChunkTerrainBuilder;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

@Mixin(NoiseBasedChunkGenerator.class)
public class MixinNoiseBasedChunkGenerator
{
    @ModifyVariable(
            method = "buildTerrain(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/blending/Blender;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/server/level/WorldGenRegion;Ljava/util/Set;Lnet/minecraft/world/level/levelgen/NoiseSettings;)Lnet/minecraft/world/level/chunk/ChunkAccess;",
            at = @At("STORE"))
    private ChunkTerrainBuilder fishofthieves$setChunkAccess(ChunkTerrainBuilder builder, @Local(argsOnly = true) ChunkAccess chunkAccess)
    {
        builder.setChunkAccess(chunkAccess);
        return builder;
    }
}