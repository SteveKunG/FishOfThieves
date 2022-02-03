package com.stevekung.fishofthieves.spawn;

import java.util.Random;

import com.stevekung.fishofthieves.utils.Continentalness;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

public record SpawnConditionContext(ServerLevel level, BlockPos blockPos, Random random, boolean isDay, boolean isNight, boolean isRaining, boolean isThundering, boolean seeSkyInWater, Biome.BiomeCategory biomeCategory, Continentalness continentalness) {}