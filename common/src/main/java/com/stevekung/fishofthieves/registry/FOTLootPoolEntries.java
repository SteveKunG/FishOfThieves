package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;

public class FOTLootPoolEntries
{
    public static final LootPoolEntryType FOT_ITEM = new LootPoolEntryType(new FOTLootItem.Serializer());
    public static final LootPoolEntryType FOT_TAG = new LootPoolEntryType(new FOTTagEntry.Serializer());

    public static void init()
    {
        register("fot_item", FOT_ITEM);
        register("fot_tag", FOT_TAG);
    }

    private static void register(String key, LootPoolEntryType type)
    {
        Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, FishOfThieves.res(key), type);
    }
}