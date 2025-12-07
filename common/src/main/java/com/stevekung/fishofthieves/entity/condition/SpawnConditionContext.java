package com.stevekung.fishofthieves.entity.condition;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public record SpawnConditionContext(ServerLevel level, @Nullable Entity entity, RegistryAccess registryAccess, BlockPos blockPos, RandomSource random) {}