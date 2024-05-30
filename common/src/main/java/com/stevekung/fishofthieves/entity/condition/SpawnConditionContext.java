package com.stevekung.fishofthieves.entity.condition;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

public record SpawnConditionContext(ServerLevel level, RegistryAccess registryAccess, BlockPos blockPos, RandomSource random)
{}