package com.stevekung.fishofthieves.registry;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class FOTMemoryModuleTypes
{
    public static final MemoryModuleType<LivingEntity> NEAREST_VISIBLE_TROPHY = new MemoryModuleType<>(Optional.empty());
    public static final MemoryModuleType<BlockPos> NEAREST_SHIPWRECK = new MemoryModuleType<>(Optional.empty());

    public static void init()
    {
        register("nearest_visible_trophy", NEAREST_VISIBLE_TROPHY);
        register("nearest_shipwreck", NEAREST_SHIPWRECK);
    }

    private static void register(String key, MemoryModuleType<?> type)
    {
        Registry.register(Registry.MEMORY_MODULE_TYPE, FishOfThieves.res(key), type);
    }
}