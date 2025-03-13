package com.stevekung.fishofthieves.entity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface BucketableEntityType<T extends Entity>
{
    @Nullable
    Entity fishofthieves$spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, MobSpawnType spawnType);

    @Nullable
    T fishofthieves$spawnByBucket(ServerLevel level, @Nullable Consumer<T> consumer, MobSpawnType spawnType);

    @Nullable
    T fishofthieves$createByBucket(ServerLevel level, @Nullable Consumer<T> consumer, MobSpawnType spawnType);
}