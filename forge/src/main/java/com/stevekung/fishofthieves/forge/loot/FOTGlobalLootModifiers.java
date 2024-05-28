package com.stevekung.fishofthieves.forge.loot;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.forge.FishOfThievesForge;

public class FOTGlobalLootModifiers
{
    public static final Codec<AddLootModifier> ADD_LOOT = AddLootModifier.CODEC;

    public static void init()
    {
        FishOfThievesForge.GLOBAL_LOOT_MODIFIERS.register("add_loot", () -> ADD_LOOT);
    }
}