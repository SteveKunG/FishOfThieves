package com.stevekung.fishofthieves.common.registry;

import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.loot.function.SetRandomFireworkFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class FOTLootItemFunctions
{
    public static final LootItemFunctionType SET_RANDOM_FIREWORK = new LootItemFunctionType(new SetRandomFireworkFunction.Serializer());

    public static void init()
    {
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, FishOfThieves.id("set_random_firework"), SET_RANDOM_FIREWORK);
    }
}