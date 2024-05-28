package com.stevekung.fishofthieves.common.registry;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.common.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.common.utils.FOTPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class FOTMemoryModuleTypes
{
    public static final MemoryModuleType<LivingEntity> NEAREST_VISIBLE_TROPHY = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<BlockPos> NEAREST_SHIPWRECK = new MemoryModuleType<>(Optional.empty());
    @SuppressWarnings("rawtypes")
    public static final MemoryModuleType<List<AbstractSchoolingThievesFish>> NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<Integer> FOLLOW_FLOCK_COOLDOWN_TICKS = new MemoryModuleType<>(Optional.of(Codec.INT));
    public static final MemoryModuleType<Integer> SCHOOL_SIZE = new MemoryModuleType<>(Optional.of(Codec.INT));
    @SuppressWarnings("rawtypes")
    public static final MemoryModuleType<AbstractSchoolingThievesFish> FLOCK_LEADER = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<Boolean> IS_FLOCK_LEADER = new MemoryModuleType<>(Optional.of(Codec.BOOL));
    public static final MemoryModuleType<Boolean> IS_FLOCK_FOLLOWER = new MemoryModuleType<>(Optional.of(Codec.BOOL));
    public static final MemoryModuleType<Boolean> MERGE_FROM_OTHER_FLOCK = new MemoryModuleType<>(Optional.of(Codec.BOOL));
    @SuppressWarnings("rawtypes")
    public static final MemoryModuleType<List<AbstractSchoolingThievesFish>> FLOCK_FOLLOWERS = new MemoryModuleType<>(Optional.empty());
    @SuppressWarnings("rawtypes")
    public static final MemoryModuleType<List<AbstractSchoolingThievesFish>> NEAREST_VISIBLE_FLOCK_LEADER = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<BlockPos> NEAREST_LOW_BRIGHTNESS = new MemoryModuleType<>(Optional.empty());

    public static void init()
    {
        register("nearest_visible_trophy", NEAREST_VISIBLE_TROPHY);
        register("nearest_shipwreck", NEAREST_SHIPWRECK);
        register("nearest_visible_schooling_thieves_fish", NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH);
        register("follow_flock_cooldown_ticks", FOLLOW_FLOCK_COOLDOWN_TICKS);
        register("school_size", SCHOOL_SIZE);
        register("flock_leader", FLOCK_LEADER);
        register("is_flock_leader", IS_FLOCK_LEADER);
        register("is_flock_follower", IS_FLOCK_FOLLOWER);
        register("merge_from_other_flock", MERGE_FROM_OTHER_FLOCK);
        register("flock_followers", FLOCK_FOLLOWERS);
        register("nearest_visible_flock_leader", NEAREST_VISIBLE_FLOCK_LEADER);
        register("nearest_low_brightness", NEAREST_LOW_BRIGHTNESS);
    }

    private static void register(String key, MemoryModuleType<?> type)
    {
        FOTPlatform.registerMemoryModuleType(key, type);
    }
}