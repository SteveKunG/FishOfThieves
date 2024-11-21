package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;

public class FOTLootPoolEntries
{
    public static final LootPoolEntryType FOT_ITEM = register("fot_item", new LootPoolEntryType(FOTLootItem.CODEC));
    public static final LootPoolEntryType FOT_TAG = register("fot_tag", new LootPoolEntryType(FOTTagEntry.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Loot Pool Entry Type");
    }

    private static LootPoolEntryType register(String key, LootPoolEntryType type)
    {
        return Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, FishOfThieves.id(key), type);
    }
}