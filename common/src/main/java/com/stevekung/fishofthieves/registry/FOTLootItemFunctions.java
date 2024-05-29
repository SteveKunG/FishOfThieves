package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.SetRandomFireworkFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class FOTLootItemFunctions
{
    public static final LootItemFunctionType SET_RANDOM_FIREWORK = new LootItemFunctionType(SetRandomFireworkFunction.CODEC);

    public static void init()
    {
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, FishOfThieves.id("set_random_firework"), SET_RANDOM_FIREWORK);
    }
}