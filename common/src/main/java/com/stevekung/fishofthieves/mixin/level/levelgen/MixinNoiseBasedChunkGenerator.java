package com.stevekung.fishofthieves.mixin.level.levelgen;

import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

@Mixin(NoiseBasedChunkGenerator.class)
public class MixinNoiseBasedChunkGenerator
{
    @Inject(
            method = "buildTerrain(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/blending/Blender;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/server/level/WorldGenRegion;Ljava/util/Set;Lnet/minecraft/world/level/levelgen/NoiseSettings;)Lnet/minecraft/world/level/chunk/ChunkAccess;",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/levelgen/ChunkTerrainBuilder.<init>(Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/densityfunction/DensitySamplerSet;Lnet/minecraft/world/level/levelgen/densityfunction/DensityVolume;Lnet/minecraft/world/level/levelgen/VerticalAnchor$Context;Lnet/minecraft/world/level/levelgen/material/rule/MaterialRule;Lnet/minecraft/world/level/biome/BiomeResolver;Ljava/util/Set;)V"))
    private void fishofthieves$setChunkAccess(ChunkAccess chunk, Blender blender, RandomState randomState, StructureManager structureManager, @Nullable WorldGenRegion carverBiomeRegion, Set<Holder<Biome>> possibleBiomes, NoiseSettings noiseSettings, CallbackInfoReturnable<ChunkAccess> info)
    {
        randomState.fishofthieves$setChunkAccess(chunk);
    }
}