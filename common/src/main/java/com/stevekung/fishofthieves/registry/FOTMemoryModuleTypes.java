package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractFlockFish;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class FOTMemoryModuleTypes
{
    public static final MemoryModuleType<BlockPos> NEAREST_WRECKER_LOCATED = register("nearest_wrecker_located", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<List<AbstractFlockFish>> NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH = register("nearest_visible_schooling_thieves_fish", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<Integer> FOLLOW_FLOCK_COOLDOWN_TICKS = register("follow_flock_cooldown_ticks", new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final MemoryModuleType<Integer> SCHOOL_SIZE = register("school_size", new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final MemoryModuleType<Integer> BREACHED_TICK = new MemoryModuleType<>(Optional.of(Codec.INT));
    public static final MemoryModuleType<AbstractFlockFish> FLOCK_LEADER = register("flock_leader", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<Boolean> IS_FLOCK_LEADER = register("is_flock_leader", new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final MemoryModuleType<Boolean> IS_FLOCK_FOLLOWER = register("is_flock_follower", new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final MemoryModuleType<Boolean> MERGE_FROM_OTHER_FLOCK = register("merge_from_other_flock", new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final MemoryModuleType<List<AbstractFlockFish>> FLOCK_FOLLOWERS = register("flock_followers", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<List<AbstractFlockFish>> NEAREST_VISIBLE_FLOCK_LEADER = register("nearest_visible_flock_leader", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<BlockPos> NEAREST_LOW_BRIGHTNESS = register("nearest_low_brightness", new MemoryModuleType<>(Optional.empty()));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Memory Module Type");
    }

    private static <T> MemoryModuleType<T> register(String key, MemoryModuleType<T> type)
    {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, FishOfThieves.id(key), type);
    }
}