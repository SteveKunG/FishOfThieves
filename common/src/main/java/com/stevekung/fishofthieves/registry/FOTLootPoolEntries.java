package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public class FOTLootPoolEntries
{
    public static final MapCodec<FOTLootItem> ITEM = register("item", FOTLootItem.MAP_CODEC);
    public static final MapCodec<FOTTagEntry> TAG = register("tag", FOTTagEntry.MAP_CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Loot Pool Entry Type");
    }

    private static <T extends LootPoolEntryContainer> MapCodec<T> register(String key, MapCodec<T> type)
    {
        return Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, FishOfThieves.id(key), type);
    }
}