package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.TreasuredFishMapFunction;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class FOTLootItemFunctions
{
    public static final LootItemFunctionType<TreasuredFishMapFunction> TREASURED_FISH_MAP = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, FishOfThieves.id("treasured_fish_map"), new LootItemFunctionType<>(TreasuredFishMapFunction.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Loot Item Function");
    }
}