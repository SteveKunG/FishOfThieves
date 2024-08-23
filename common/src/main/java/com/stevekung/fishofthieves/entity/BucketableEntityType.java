package com.stevekung.fishofthieves.entity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface BucketableEntityType<T extends Entity>
{
    @Nullable
    Entity spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, EntitySpawnReason entitySpawnReason);

    @Nullable
    T spawnByBucket(ServerLevel level, @Nullable Consumer<T> consumer, EntitySpawnReason entitySpawnReason);

    @Nullable
    T createByBucket(ServerLevel level, @Nullable Consumer<T> consumer, EntitySpawnReason entitySpawnReason);
}