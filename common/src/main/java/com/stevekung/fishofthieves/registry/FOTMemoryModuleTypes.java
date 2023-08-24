package com.stevekung.fishofthieves.registry;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class FOTMemoryModuleTypes
{
    public static final MemoryModuleType<LivingEntity> NEAREST_VISIBLE_TROPHY = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<BlockPos> NEAREST_SHIPWRECK = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<Integer> FOLLOW_FLOCK_COOLDOWN_TICKS = new MemoryModuleType<>(Optional.of(Codec.INT));
    public static final MemoryModuleType<Boolean> IS_FLOCK_FOLLOWER = new MemoryModuleType<>(Optional.of(Codec.BOOL));
    public static final MemoryModuleType<Boolean> IS_FLOCK_LEADER = new MemoryModuleType<>(Optional.of(Codec.BOOL));
    public static final MemoryModuleType<LivingEntity> NEAREST_FLOCK_LEADER = new MemoryModuleType<>(Optional.empty());

    public static void init()
    {
        register("nearest_visible_trophy", NEAREST_VISIBLE_TROPHY);
        register("nearest_shipwreck", NEAREST_SHIPWRECK);
        register("follow_flock_cooldown_ticks", FOLLOW_FLOCK_COOLDOWN_TICKS);
        register("is_flock_follower", IS_FLOCK_FOLLOWER);
        register("is_flock_leader", IS_FLOCK_LEADER);
        register("flock_leader", NEAREST_FLOCK_LEADER);
    }

    private static void register(String key, MemoryModuleType<?> type)
    {
        Registry.register(Registry.MEMORY_MODULE_TYPE, FishOfThieves.res(key), type);
    }
}