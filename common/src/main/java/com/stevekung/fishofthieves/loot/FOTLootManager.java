package com.stevekung.fishofthieves.loot;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootTables;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class FOTLootManager
{
    public static void dropWorms(List<ItemStack> droppedList, BlockState blockState, ReloadableServerRegistries.Holder holder, LootParams lootParams)
    {
        if (FishOfThieves.CONFIG.general.enableEarthwormsDrop && blockState.is(FOTTags.Blocks.EARTHWORMS_DROPS) && !blockState.is(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST))
        {
            droppedList.addAll(holder.getLootTable(FOTLootTables.Blocks.EARTHWORMS_DROPS).getRandomItems(lootParams));
        }
        if (FishOfThieves.CONFIG.general.enableGrubsDrop && blockState.is(FOTTags.Blocks.GRUBS_DROPS))
        {
            droppedList.addAll(holder.getLootTable(FOTLootTables.Blocks.GRUBS_DROPS).getRandomItems(lootParams));
        }
        if (FishOfThieves.CONFIG.general.enableLeechesDrop && blockState.is(FOTTags.Blocks.LEECHES_DROPS))
        {
            droppedList.addAll(holder.getLootTable(FOTLootTables.Blocks.LEECHES_DROPS).getRandomItems(lootParams));
        }
    }

    public static LootPool.Builder getFishermanGiftLoot(LootPool.Builder builder)
    {
        return builder.add(NestedLootTable.lootTableReference(FOTLootTables.Gift.FISHERMAN_GIFT));
    }

    public static LootPool.Builder getFishingLoot(LootPool.Builder builder)
    {
        return builder.add(NestedLootTable.lootTableReference(FOTLootTables.Fishing.FISHING_FISH));
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder)
    {
        return builder.add(NestedLootTable.lootTableReference(FOTLootTables.Entities.POLAR_BEAR));
    }

    public static LootPool.Builder getOceanRuinsArchaeologyLoot(LootPool.Builder builder)
    {
        return builder.add(NestedLootTable.lootTableReference(FOTLootTables.Archaeology.OCEAN_RUINS));
    }

    public static LootPool.Builder getVillageFisherLoot(LootPool.Builder builder)
    {
        return builder.add(NestedLootTable.lootTableReference(FOTLootTables.Chests.VILLAGE_FISHER));
    }

    public static LootPool.Builder getBuriedTreasureLoot(LootPool.Builder builder)
    {
        return builder.add(NestedLootTable.lootTableReference(FOTLootTables.Chests.BURIED_TREASURE));
    }
}