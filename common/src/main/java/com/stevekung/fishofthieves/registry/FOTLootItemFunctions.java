package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.TreasuredFishMapFunction;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class FOTLootItemFunctions
{
    public static final MapCodec<TreasuredFishMapFunction> TREASURED_FISH_MAP = register("treasured_fish_map", TreasuredFishMapFunction.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Loot Item Function");
    }

    private static <T extends LootItemFunction> MapCodec<T> register(String key, MapCodec<T> codec)
    {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, FishOfThieves.id(key), codec);
    }
}