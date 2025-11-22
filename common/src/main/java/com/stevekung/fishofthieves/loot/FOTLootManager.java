package com.stevekung.fishofthieves.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.condition.BaitAttachedCondition;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.registry.*;

import net.minecraft.Util;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
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
    public static void dropWorms(List<ItemStack> droppedList, BlockState blockState, LootDataManager lootDataManager, LootParams lootParams)
    {
        if (FishOfThieves.CONFIG.general.enableEarthwormsDrop && blockState.is(FOTTags.Blocks.EARTHWORMS_DROPS) && !blockState.is(FOTTags.Blocks.EARTHWORMS_DROP_BLACKLIST))
        {
            droppedList.addAll(lootDataManager.getLootTable(FOTLootTables.Blocks.EARTHWORMS_DROPS).getRandomItems(lootParams));
        }
        if (FishOfThieves.CONFIG.general.enableGrubsDrop && blockState.is(FOTTags.Blocks.GRUBS_DROPS))
        {
            droppedList.addAll(lootDataManager.getLootTable(FOTLootTables.Blocks.GRUBS_DROPS).getRandomItems(lootParams));
        }
        if (FishOfThieves.CONFIG.general.enableLeechesDrop && blockState.is(FOTTags.Blocks.LEECHES_DROPS))
        {
            droppedList.addAll(lootDataManager.getLootTable(FOTLootTables.Blocks.LEECHES_DROPS).getRandomItems(lootParams));
        }
    }

    public static Map<ResourceLocation, Function<LootPool.Builder, LootPool.Builder>> getInjectedLootTableMap()
    {
        return Util.make(new HashMap<>(), map ->
        {
            // Gameplay
            map.put(BuiltInLootTables.FISHERMAN_GIFT, FOTLootManager::getFishermanGiftLoot);
            map.put(BuiltInLootTables.FISHING, builder -> getFishingLoot(builder, true));
            map.put(BuiltInLootTables.FISHING_FISH, builder -> getFishingLoot(builder, false));

            // Entity Loot
            map.put(EntityType.POLAR_BEAR.getDefaultLootTable(), FOTLootManager::getPolarBearLoot);
            map.put(EntityType.DOLPHIN.getDefaultLootTable(), FOTLootManager::getDolphinLoot);

            // Archaeology
            map.put(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, FOTLootManager::getOceanRuinsArchaeologyLoot);
            map.put(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY, FOTLootManager::getOceanRuinsArchaeologyLoot);
        });
    }

    public static Map<ResourceLocation, Function<LootPool.Builder, LootPool.Builder>> getInjectedLootPoolMap()
    {
        return Util.make(new HashMap<>(), map ->
        {
            // Entity Loot
            map.put(EntityType.GUARDIAN.getDefaultLootTable(), builder -> FOTLootManager.getGuardianLoot(builder, false));
            map.put(EntityType.ELDER_GUARDIAN.getDefaultLootTable(), builder -> FOTLootManager.getGuardianLoot(builder, true));

            // Chests
            map.put(BuiltInLootTables.VILLAGE_FISHER, FOTLootManager::getVillageFisherLoot);
            map.put(BuiltInLootTables.BURIED_TREASURE, FOTLootManager::getBuriedTreasureLoot);
            map.put(BuiltInLootTables.SHIPWRECK_SUPPLY, FOTLootManager::getShipwreckSupplyLoot);
            map.put(BuiltInLootTables.JUNGLE_TEMPLE, FOTLootManager::getJungleTempleLoot);
        });
    }

    public static LootPool.Builder getFishermanGiftLoot(LootPool.Builder builder)
    {
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL))
                .add(FOTLootItem.lootTableItem(FOTItems.PONDIE))
                .add(FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH))
                .add(FOTLootItem.lootTableItem(FOTItems.DEVILFISH))
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL))
                .add(FOTLootItem.lootTableItem(FOTItems.WRECKER))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH).when(FOTLootItemConditions.THUNDERING));
    }

    public static LootPool.Builder getFishingLoot(LootPool.Builder builder, boolean useBaits)
    {
        var fishLoot = new ArrayList<Pair<TagKey<Item>, LootPoolSingletonContainer.Builder<?>>>();

        fishLoot.add(Pair.of(null, FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                .setWeight(50)
                .when(FOTLootItemConditions.IN_OCEAN)));
        fishLoot.add(Pair.of(null, FOTLootItem.lootTableItem(FOTItems.PONDIE)
                .setWeight(50)
                .when(FOTLootItemConditions.IN_RIVER.or(FOTLootItemConditions.IN_FOREST))));
        fishLoot.add(Pair.of(null, FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER)
                .setWeight(40)
                .when(FOTLootItemConditions.COAST)));
        fishLoot.add(Pair.of(FOTTags.Items.LEECHES_FOOD, FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                .setWeight(40)
                .when(FOTLootItemConditions.IN_LUKEWARM_OCEAN.or(FOTLootItemConditions.IN_DEEP_LUKEWARM_OCEAN))));
        fishLoot.add(Pair.of(FOTTags.Items.EARTHWORMS_FOOD, FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                .setWeight(45)
                .when(FOTLootItemConditions.IN_LUKEWARM_OCEAN.or(FOTLootItemConditions.IN_DEEP_LUKEWARM_OCEAN).or(FOTLootItemConditions.IN_WARM_OCEAN))));
        fishLoot.add(Pair.of(FOTTags.Items.EARTHWORMS_FOOD, FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                .setWeight(45)
                .when(FOTLootItemConditions.IN_LUSH_CAVES.or(FOTLootItemConditions.IN_JUNGLE))));
        fishLoot.add(Pair.of(FOTTags.Items.GRUBS_FOOD, FOTLootItem.lootTableItem(FOTItems.DEVILFISH)
                .setWeight(35)
                .when(FOTLootItemConditions.IN_DRIPSTONE_CAVES)));
        fishLoot.add(Pair.of(FOTTags.Items.GRUBS_FOOD, FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                .setWeight(35)
                .when(FOTLootItemConditions.IN_OCEAN_MONUMENTS.or(FOTLootItemConditions.IN_PILLAGER_OUTPOSTS).or(FOTLootItemConditions.HAS_RAIDS))));
        fishLoot.add(Pair.of(FOTTags.Items.EARTHWORMS_FOOD, FOTLootItem.lootTableItem(FOTItems.WRECKER)
                .setWeight(20)
                .when(FOTLootItemConditions.IN_SHIPWRECKS_OR_RUINED_PORTAL_OCEAN)));
        fishLoot.add(Pair.of(FOTTags.Items.LEECHES_FOOD, FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                .setWeight(20)
                .when(FOTLootItemConditions.THUNDERING)));

        if (useBaits)
        {
            fishLoot.forEach(pair ->
            {
                if (pair.getFirst() != null)
                {
                    pair.getSecond().fishofthieves$addWeight(100);
                    pair.getSecond().when(BaitAttachedCondition.baitMatches(ItemPredicate.Builder.item().of(pair.getFirst()), EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.FISHING_BOBBER))));
                    builder.add(pair.getSecond());
                }
            });
        }
        else
        {
            fishLoot.forEach(pair -> builder.add(pair.getSecond()));
        }
        return builder;
    }

    public static LootPool.Builder getGuardianLoot(LootPool.Builder builder, boolean elder)
    {
        var weight = elder ? 3 : 2;
        return builder.setRolls(ConstantValue.exactly(1.0F))
                .add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .setWeight(weight)
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE))))
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .setWeight(weight)
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE))));
    }

    public static LootPool.Builder getDolphinLoot(LootPool.Builder builder)
    {
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE))))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE))))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE))))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE))))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .when(FOTLootItemConditions.THUNDERING));
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder)
    {
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted()
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityLootSubProvider.ENTITY_ON_FIRE)))
                        .setWeight(10)
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
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))
                        .when(FOTLootItemConditions.THUNDERING));
    }

    public static LootPool.Builder getOceanRuinsArchaeologyLoot(LootPool.Builder builder)
    {
        return builder
                .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                .add(LootItem.lootTableItem(FOTItems.STORMFISH_POTTERY_SHERD))
                .add(LootItem.lootTableItem(FOTItems.KRAKEN_POTTERY_SHERD))
                .add(LootItem.lootTableItem(FOTItems.MEGALODON_POTTERY_SHERD))
                .add(LootItem.lootTableItem(FOTItems.GREAT_MOUTH_POTTERY_SHERD))
                ;
    }

    public static LootPool.Builder getVillageFisherLoot(LootPool.Builder builder)
    {
        return builder.add(FOTTagEntry.expandTag(FOTTags.Items.THIEVES_FISH).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f))));
    }

    public static LootPool.Builder getBuriedTreasureLoot(LootPool.Builder builder)
    {
        return builder.setRolls(ConstantValue.exactly(2.0f)).add(TagEntry.expandTag(FOTTags.Items.COOKED_THIEVES_FISH)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f))));
    }

    public static LootPool.Builder getShipwreckSupplyLoot(LootPool.Builder builder)
    {
        return builder.setRolls(UniformGenerator.between(1.0F, 3.0F))
                .add(LootItem.lootTableItem(FOTItems.BANANA).setWeight(9)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                .add(LootItem.lootTableItem(FOTItems.COCONUT).setWeight(7)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                .add(LootItem.lootTableItem(FOTItems.POMEGRANATE).setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                .add(LootItem.lootTableItem(FOTItems.MANGO).setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))
                .add(LootItem.lootTableItem(FOTItems.PINEAPPLE).setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))
                ;
    }

    public static LootPool.Builder getJungleTempleLoot(LootPool.Builder builder)
    {
        return builder.setRolls(UniformGenerator.between(1.0F, 2.0F))
                .add(LootItem.lootTableItem(FOTItems.BANANA).setWeight(9)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(FOTBiomes.TROPICAL_ISLAND)))
                .add(LootItem.lootTableItem(FOTItems.POMEGRANATE).setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(FOTBiomes.TROPICAL_ISLAND)))
                .add(LootItem.lootTableItem(FOTItems.MANGO).setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(FOTBiomes.TROPICAL_ISLAND)))
                .add(LootItem.lootTableItem(FOTItems.PINEAPPLE).setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(FOTBiomes.TROPICAL_ISLAND)))
                ;
    }
}