package com.stevekung.fishofthieves.common.spawn;

import com.stevekung.fishofthieves.common.utils.Continentalness;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;

public record SpawnConditionContext(ServerLevel level, BlockPos blockPos, RandomSource random, boolean isDay, boolean isNight, boolean isRaining, boolean isThundering, boolean seeSkyInWater, Holder<Biome> biomeTag, Continentalness continentalness) {}