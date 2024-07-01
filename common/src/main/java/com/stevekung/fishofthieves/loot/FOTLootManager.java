package com.stevekung.fishofthieves.loot;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FOTLootItem;
import com.stevekung.fishofthieves.loot.function.FOTTagEntry;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.registry.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
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
        var provider = VanillaRegistries.createLookup(); //TODO TEMP Waiting for Loot v3 then remove biome and structure check from FOTLocationPredicate

        //@formatter:off
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .setWeight(50)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_SPLASHTAILS))))

                .add(FOTLootItem.lootTableItem(FOTItems.PONDIE)
                        .setWeight(50)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_PONDIES))))

                .add(FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .setWeight(40)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_ISLEHOPPERS)).and(FOTLootItemConditions.COAST_CONTINENTALNESS.or(FOTLootItemConditions.OCEAN_CONTINENTALNESS).or(FOTLootItemConditions.LOW_PEAKTYPE).or(FOTLootItemConditions.MID_PEAKTYPE))))

                .add(FOTLootItem.lootTableItem(FOTItems.ANCIENTSCALE)
                        .setWeight(40)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_ANCIENTSCALES)).or(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructures(FOTTags.Structures.ANCIENTSCALES_SPAWN_IN)))))

                .add(FOTLootItem.lootTableItem(FOTItems.PLENTIFIN)
                        .setWeight(45)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_PLENTIFINS)).or(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructures(FOTTags.Structures.PLENTIFINS_SPAWN_IN)))))

                .add(FOTLootItem.lootTableItem(FOTItems.WILDSPLASH)
                        .setWeight(45)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_WILDSPLASH))))

                .add(FOTLootItem.lootTableItem(FOTItems.DEVILFISH)
                        .setWeight(35)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_DEVILFISH)).and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setY(MinMaxBounds.Doubles.atMost(0))).and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN)).invert()))))

                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .setWeight(35)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_BATTLEGILLS)).and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructures(FOTTags.Structures.BATTLEGILLS_SPAWN_IN)).or(FOTLootItemConditions.HAS_RAIDS))))

                .add(FOTLootItem.lootTableItem(FOTItems.WRECKER)
                        .setWeight(20)
                        .when(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setStructures(FOTTags.Structures.WRECKERS_SPAWN_IN)).and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_WRECKERS)))))

                .add(FOTLootItem.lootTableItem(FOTItems.STORMFISH)
                        .setWeight(20)
                        .when(FOTLootItemConditions.THUNDERING.and(FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomes(FOTTags.Biomes.SPAWNS_STORMFISH)))));
        //@formatter:on
    }

    public static LootPool.Builder getGuardianLoot(LootPool.Builder builder, boolean elder)
    {
        var provider = VanillaRegistries.createLookup(); //TODO TEMP Waiting for Loot v3

        //@formatter:off
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
        //@formatter:on
    }

    public static LootPool.Builder getDolphinLoot(LootPool.Builder builder)
    {
        var provider = VanillaRegistries.createLookup(); //TODO TEMP Waiting for Loot v3

        //@formatter:off
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
        //@formatter:on
    }

    public static LootPool.Builder getPolarBearLoot(LootPool.Builder builder)
    {
        var provider = VanillaRegistries.createLookup(); //TODO TEMP Waiting for Loot v3

        //@formatter:off
        return builder.add(FOTLootItem.lootTableItem(FOTItems.SPLASHTAIL)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.PONDIE)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(1)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))))
                .add(FOTLootItem.lootTableItem(FOTItems.ISLEHOPPER)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(8)
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
                .add(FOTLootItem.lootTableItem(FOTItems.BATTLEGILL)
                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(provider)))
                        .setWeight(1)
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
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(provider, UniformGenerator.between(0.0F, 1.0F))));
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

    public static AnyOfCondition.Builder shouldSmeltLoot(HolderLookup.Provider provider)
    {
        //@formatter:off
        var registryLookup = provider.lookupOrThrow(Registries.ENCHANTMENT);
        return AnyOfCondition.anyOf(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
                        .flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))),
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity()
                        .equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item()
                                .withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(
                                        new EnchantmentPredicate(registryLookup.getOrThrow(EnchantmentTags.SMELTS_LOOT), MinMaxBounds.Ints.ANY)
                                )))))));
        //@formatter:on
    }
}