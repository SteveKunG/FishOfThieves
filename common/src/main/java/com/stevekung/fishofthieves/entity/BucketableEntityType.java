package com.stevekung.fishofthieves.entity;

import org.jspecify.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PostSpawnProcessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface BucketableEntityType<T extends Entity>
{
    @Nullable T fishofthieves$spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, EntitySpawnReason entitySpawnReason);

    @Nullable
    T fishofthieves$spawnByBucket(ServerLevel level, @Nullable PostSpawnProcessor<T> postSpawnConfig, EntitySpawnReason entitySpawnReason);

    @Nullable
    T fishofthieves$createByBucket(ServerLevel level, @Nullable PostSpawnProcessor<T> postSpawnConfig, EntitySpawnReason entitySpawnReason);
}