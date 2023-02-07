package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.SetRandomFireworkFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class FOTLootItemFunctions
{
    public static final LootItemFunctionType SET_RANDOM_FIREWORK = new LootItemFunctionType(new SetRandomFireworkFunction.Serializer());

    public static void init()
    {
        Registry.register(Registry.LOOT_FUNCTION_TYPE, FishOfThieves.res("set_random_firework"), SET_RANDOM_FIREWORK);
    }
}