package com.stevekung.fishofthieves.entity;

import org.jetbrains.annotations.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface BucketableEntityType<T extends Entity>
{
    @Nullable
    Entity spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, MobSpawnType spawnType);

    @Nullable
    T spawnByBucket(ServerLevel level, @Nullable CompoundTag compound, @Nullable Component customName, @Nullable Player player, MobSpawnType spawnType);

    @Nullable
    T createByBucket(ServerLevel level, @Nullable CompoundTag compound, @Nullable Component customName, @Nullable Player player, MobSpawnType spawnType);
}