package com.stevekung.fishofthieves.loot;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.*;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FOTLootManager
{
    public static void dropWorms(List<ItemStack> droppedList, BlockState blockState, ReloadableServerRegistries.Holder holder, LootParams lootParams)
    {
        if (FishOfThieves.CONFIG.general.enableEarthwormsDrop && blockState.is(FOTTags.Blocks.EARTHWORMS_DROPS) && !blockState.is(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST))
        {
            droppedList.addAll(holder.getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FOTLootTables.Blocks.EARTHWORMS_DROPS)).getRandomItems(lootParams));
        }
        if (FishOfThieves.CONFIG.general.enableGrubsDrop && blockState.is(FOTTags.Blocks.GRUBS_DROPS))
        {
            droppedList.addAll(holder.getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FOTLootTables.Blocks.GRUBS_DROPS)).getRandomItems(lootParams));
        }
        if (FishOfThieves.CONFIG.general.enableLeechesDrop && blockState.is(FOTTags.Blocks.LEECHES_DROPS))
        {
            droppedList.addAll(holder.getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FOTLootTables.Blocks.LEECHES_DROPS)).getRandomItems(lootParams));
        }
    }

    public static LootPool.Builder getFishermanGiftLoot(LootPool.Builder builder)
    {
        //@formatter:off
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL))
                .add(FOTLootItem.lootTableItem(FOTItems.PONDIE))
                .add(FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH))
                .add(FOTLootItem.lootTableItem(FOTItems.DEVILFISH))
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL))
                .add(FOTLootItem.lootTableItem(FOTItems.WRECKER))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH));
        //@formatter:on
    }

    public static LootPool.Builder getFishingLoot(LootPool.Builder builder)
    {
        //@formatter:off
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .setWeight(50)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_SPLASHTAILS))))
                .add(FOTLootItem.lootTableItem(FOTItems.PONDIE)
                        .setWeight(50)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_PONDIES))))
                .add(FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .setWeight(40)
                        .when(FOTLootItemConditions.COAST))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .setWeight(40)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_ANCIENTSCALES))))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .setWeight(45)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_PLENTIFINS))))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .setWeight(45)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_WILDSPLASH))))
                .add(FOTLootItem.lootTableItem(FOTItems.DEVILFISH)
                        .setWeight(35)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_DEVILFISH)).and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setY(MinMaxBounds.Doubles.atMost(0))))))
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .setWeight(35)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructures(FOTTags.Structures.BATTLEGILLS_SPAWN_IN)).or(FOTLootItemConditions.HAS_RAIDS)))
                .add(FOTLootItem.lootTableItem(FOTItems.WRECKER)
                        .setWeight(20)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructures(FOTTags.Structures.WRECKERS_SPAWN_IN))))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                        .setWeight(20)
                        .when(FOTLootItemConditions.THUNDERING));
        //@formatter:on
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder)
    {
        //@formatter:off
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.PONDIE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.DEVILFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.WRECKER)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))));
        //@formatter:on
    }

    public static LootPool.Builder getOceanRuinsArchaeologyLoot(LootPool.Builder builder)
    {
        return builder.add(LootItem.lootTableItem(FOTBlocks.FISH_BONE));
    }

    public static LootPool.Builder getVillageFisherLoot(LootPool.Builder builder)
    {
        return builder.add(FOTTagEntry.expandTag(FOTTags.Items.THIEVES_FISH).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f))));
    }

    public static LootPool.Builder getBuriedTreasureLoot(LootPool.Builder builder)
    {
        return builder.setRolls(ConstantValue.exactly(2.0f)).add(TagEntry.expandTag(FOTTags.Items.COOKED_THIEVES_FISH).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f))));
    }
}