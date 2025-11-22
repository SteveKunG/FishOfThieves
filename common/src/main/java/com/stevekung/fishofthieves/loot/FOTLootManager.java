package com.stevekung.fishofthieves.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.mojang.datafixers.util.Pair;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.condition.BaitAttachedCondition;
import com.stevekung.fishofthieves.loot.condition.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.*;

import net.minecraft.util.Util;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
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

    public static Map<ResourceKey<LootTable>, BiFunction<LootPool.Builder, HolderLookup.Provider, LootPool.Builder>> getInjectedLootTableMap()
    {
        return Util.make(new HashMap<>(), map ->
        {
            // Gameplay
            map.put(BuiltInLootTables.FISHERMAN_GIFT, (builder, provider) -> FOTLootManager.getFishermanGiftLoot(builder));
            map.put(BuiltInLootTables.FISHING, (builder, provider) -> getFishingLoot(builder, provider, true));
            map.put(BuiltInLootTables.FISHING_FISH, (builder, provider) -> getFishingLoot(builder, provider, false));

            // Entity Loot
            EntityType.POLAR_BEAR.getDefaultLootTable().ifPresent(key -> map.put(key, FOTLootManager::getPolarBearLoot));
            EntityType.DOLPHIN.getDefaultLootTable().ifPresent(key -> map.put(key, FOTLootManager::getDolphinLoot));

            // Archaeology
            map.put(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, (builder, provider) -> FOTLootManager.getOceanRuinsArchaeologyLoot(builder));
            map.put(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY, (builder, provider) -> FOTLootManager.getOceanRuinsArchaeologyLoot(builder));
        });
    }

    public static Map<ResourceKey<LootTable>, BiFunction<LootPool.Builder, HolderLookup.Provider, LootPool.Builder>> getInjectedLootPoolMap()
    {
        return Util.make(new HashMap<>(), map ->
        {
            // Entity Loot
            EntityType.GUARDIAN.getDefaultLootTable().ifPresent(key -> map.put(key, (builder, provider) -> FOTLootManager.getGuardianLoot(builder, provider, false)));
            EntityType.ELDER_GUARDIAN.getDefaultLootTable().ifPresent(key -> map.put(key, (builder, provider) -> FOTLootManager.getGuardianLoot(builder, provider, true)));

            // Chests
            map.put(BuiltInLootTables.VILLAGE_FISHER, (builder, provider) -> FOTLootManager.getVillageFisherLoot(builder));
            map.put(BuiltInLootTables.BURIED_TREASURE, (builder, provider) -> FOTLootManager.getBuriedTreasureLoot(builder));
            map.put(BuiltInLootTables.SHIPWRECK_SUPPLY, (builder, provider) -> FOTLootManager.getShipwreckSupplyLoot(builder));
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

    public static LootPool.Builder getFishingLoot(LootPool.Builder builder, HolderLookup.Provider provider, boolean useBaits)
    {
        var structureLookup = provider.lookupOrThrow(Registries.STRUCTURE);
        var biomeLookup = provider.lookupOrThrow(Registries.BIOME);
        var itemLookup = provider.lookupOrThrow(Registries.ITEM);
        var entityTypeLookup = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        var fishLoot = new ArrayList<Pair<TagKey<Item>, LootPoolSingletonContainer.Builder<?>>>();

        fishLoot.add(Pair.of(null, FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                .setWeight(50)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_SPLASHTAILS))))));
        fishLoot.add(Pair.of(null, FOTLootItem.lootTableItem(FOTItems.PONDIE)
                .setWeight(50)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_PONDIES))))));
        fishLoot.add(Pair.of(null, FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER)
                .setWeight(40)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_ISLEHOPPERS)))
                        .and(FOTLootItemConditions.COAST_CONTINENTALNESS
                                .and(FOTLootItemConditions.LOW_PEAKTYPE
                                        .or(FOTLootItemConditions.MID_PEAKTYPE)
                                        .or(FOTLootItemConditions.VALLEY_PEAKTYPE))))));
        fishLoot.add(Pair.of(FOTTags.Items.LEECHES_FOOD, FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                .setWeight(40)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_ANCIENTSCALES)))
                        .or(LocationCheck.checkLocation(LocationPredicate.Builder.location()
                                .setStructures(structureLookup.getOrThrow(FOTTags.Structures.ANCIENTSCALES_SPAWN_IN)))))));
        fishLoot.add(Pair.of(FOTTags.Items.EARTHWORMS_FOOD, FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                .setWeight(45)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_PLENTIFINS))))));
        fishLoot.add(Pair.of(FOTTags.Items.EARTHWORMS_FOOD, FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                .setWeight(45)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_WILDSPLASH))))));
        fishLoot.add(Pair.of(FOTTags.Items.GRUBS_FOOD, FOTLootItem.lootTableItem(FOTItems.DEVILFISH)
                .setWeight(35)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_DEVILFISH)))
                        .and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setY(MinMaxBounds.Doubles.atMost(0)))
                                .and(LocationCheck.checkLocation(LocationPredicate.Builder.location()
                                        .setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN))).invert())))));
        fishLoot.add(Pair.of(FOTTags.Items.GRUBS_FOOD, FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                .setWeight(35)
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_BATTLEGILLS)))
                        .and(LocationCheck.checkLocation(LocationPredicate.Builder.location()
                                .setStructures(structureLookup.getOrThrow(FOTTags.Structures.BATTLEGILLS_SPAWN_IN))).or(FOTLootItemConditions.HAS_RAIDS)))));
        fishLoot.add(Pair.of(FOTTags.Items.EARTHWORMS_FOOD, FOTLootItem.lootTableItem(FOTItems.WRECKER)
                .setWeight(20)
                .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructureInRange(structureLookup.getOrThrow(FOTTags.Structures.WRECKERS_SPAWN_IN), 32, 1))
                        .and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_WRECKERS)))))));
        fishLoot.add(Pair.of(FOTTags.Items.LEECHES_FOOD, FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                .setWeight(20)
                .when(FOTLootItemConditions.THUNDERING.and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(biomeLookup.getOrThrow(FOTTags.Biomes.SPAWNS_STORMFISH)))))));

        if (useBaits)
        {
            fishLoot.forEach(pair ->
            {
                if (pair.getFirst() != null)
                {
                    pair.getSecond().fishofthieves$addWeight(100);
                    pair.getSecond().when(BaitAttachedCondition.baitMatches(ItemPredicate.Builder.item().of(itemLookup, pair.getFirst()), EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeLookup, EntityType.FISHING_BOBBER))));
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

    public static LootPool.Builder getGuardianLoot(LootPool.Builder builder, HolderLookup.Provider provider, boolean elder)
    {
        var weight = elder ? 3 : 2;
        return builder.setRolls(ConstantValue.exactly(1.0F))
                .add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .setWeight(weight)
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider))))
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .setWeight(weight)
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider))));
    }

    public static LootPool.Builder getDolphinLoot(LootPool.Builder builder, HolderLookup.Provider provider)
    {
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider))))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider))))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider))))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider))))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .when(FOTLootItemConditions.THUNDERING));
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder, HolderLookup.Provider provider)
    {
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.WRECKER)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F)))
                        .when(FOTLootItemConditions.THUNDERING));
    }

    public static LootPool.Builder getOceanRuinsArchaeologyLoot(LootPool.Builder builder)
    {
        return builder
                .add(LootItem.lootTableItem(FOTBlocks.FISH_BONE))
                .add(LootItem.lootTableItem(FOTItems.STORMFISH_POTTERY_SHERD))
                .add(LootItem.lootTableItem(FOTItems.KRAKEN_POTTERY_SHERD))
                .add(LootItem.lootTableItem(FOTItems.MEGALODON_POTTERY_SHERD))
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

    public static LootPool.Builder getJungleTempleLoot(LootPool.Builder builder, HolderLookup.Provider provider)
    {
        var biomeLookup = provider.lookupOrThrow(Registries.BIOME);
        return builder.setRolls(UniformGenerator.between(1.0F, 2.0F))
                .add(LootItem.lootTableItem(FOTItems.BANANA).setWeight(9)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND)))))
                .add(LootItem.lootTableItem(FOTItems.POMEGRANATE).setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND)))))
                .add(LootItem.lootTableItem(FOTItems.MANGO).setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND)))))
                .add(LootItem.lootTableItem(FOTItems.PINEAPPLE).setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND)))))
                ;
    }

    public static AnyOfCondition.Builder shouldSmeltLoot(HolderLookup.Provider provider)
    {
        var registryLookup = provider.lookupOrThrow(Registries.ENCHANTMENT);
        return AnyOfCondition.anyOf(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
                        .flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))),
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity()
                        .equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item()
                                .withComponents(DataComponentMatchers.Builder.components()
                                        .partial(DataComponentPredicates.ENCHANTMENTS, EnchantmentsPredicate.enchantments(
                                                List.of(new EnchantmentPredicate(registryLookup.getOrThrow(EnchantmentTags.SMELTS_LOOT), MinMaxBounds.Ints.ANY))))
                                        .build())))));
    }
}