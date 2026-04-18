package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import com.stevekung.fishofthieves.block.*;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class BlockLootProvider extends FabricBlockLootSubProvider
{
    public BlockLootProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    public void generate()
    {
        var registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.dropSelf(FOTBlocks.FISH_BONE);

        this.dropSelf(FOTBlocks.OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.CHERRY_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.PALE_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_PALE_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.COPPER_FRAME_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_CHERRY_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_PALE_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_PALE_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.GILDED_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_PALE_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.PINK_PLUMERIA);
        this.dropSelf(FOTBlocks.LIGHT_BLUE_PLUMERIA);
        this.dropSelf(FOTBlocks.WHITE_PLUMERIA);
        this.dropPottedContents(FOTBlocks.POTTED_PINK_PLUMERIA);
        this.dropPottedContents(FOTBlocks.POTTED_LIGHT_BLUE_PLUMERIA);
        this.dropPottedContents(FOTBlocks.POTTED_WHITE_PLUMERIA);
        this.dropSelf(FOTBlocks.SMALL_COCONUT_LOG);
        this.dropSelf(FOTBlocks.SMALL_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.MEDIUM_COCONUT_LOG);
        this.dropSelf(FOTBlocks.MEDIUM_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.COCONUT_LOG);
        this.dropSelf(FOTBlocks.COCONUT_WOOD);
        this.dropSelf(FOTBlocks.STRIPPED_COCONUT_LOG);
        this.dropSelf(FOTBlocks.STRIPPED_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.STRIPPED_MEDIUM_COCONUT_LOG);
        this.dropSelf(FOTBlocks.STRIPPED_MEDIUM_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.STRIPPED_SMALL_COCONUT_LOG);
        this.dropSelf(FOTBlocks.STRIPPED_SMALL_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.COCONUT_SAPLING);
        this.add(FOTBlocks.COCONUT_FRUIT, block -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(FOTItems.COCONUT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 4f))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CoconutFruitBlock.AGE, 2)))));
        this.add(FOTBlocks.COCONUT_FRONDS, block -> this.createSilkTouchOrShearsDispatchTable(block, LootItem.lootTableItem(block).when(BonusLevelTableCondition.bonusLevelFlatChance(registryLookup.getOrThrow(Enchantments.FORTUNE), 0.5F, 1.0F))));
        this.dropSelf(FOTBlocks.BANANA_STEM);
        this.add(FOTBlocks.BANANA_LEAVES, block -> LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block)
                                .apply(List.of(2), integer -> SetItemCountFunction.setCount(ConstantValue.exactly((float) integer))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BananaLeavesBlock.COUNT, integer))))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM)))))));

        this.dropSelf(FOTBlocks.COCONUT_PLANKS);
        this.dropSelf(FOTBlocks.COCONUT_BUTTON);
        this.dropSelf(FOTBlocks.COCONUT_FENCE);
        this.dropSelf(FOTBlocks.COCONUT_FENCE_GATE);
        this.dropSelf(FOTBlocks.COCONUT_PRESSURE_PLATE);
        this.dropSelf(FOTBlocks.COCONUT_STAIRS);
        this.dropSelf(FOTBlocks.COCONUT_TRAPDOOR);
        this.add(FOTBlocks.COCONUT_DOOR, this::createDoorTable);
        this.add(FOTBlocks.COCONUT_SLAB, this::createSlabItemTable);
        this.dropSelf(FOTBlocks.COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COPPER_FRAME_COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.COCONUT_SIGN);
        this.dropSelf(FOTBlocks.COCONUT_HANGING_SIGN);
        this.dropSelf(FOTBlocks.BANANA_SHOOTS);
        this.dropSelf(FOTBlocks.BANANA_BLOSSOM);

        this.add(FOTBlocks.RIPE_BANANA_CLUSTER_PLANT, this.createSilkTouchDispatchTable(FOTBlocks.RIPE_BANANA_CLUSTER, this.applyExplosionDecay(FOTBlocks.RIPE_BANANA_CLUSTER, LootItem.lootTableItem(FOTItems.BANANA)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 8.0F)))
                .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                .apply(LimitCount.limitCount(IntRange.upperBound(9))))));
        this.add(FOTBlocks.RIPE_BANANA_CLUSTER, block -> this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(FOTItems.BANANA)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 8.0F)))
                .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                .apply(LimitCount.limitCount(IntRange.upperBound(9))))));
        this.dropWhenSilkTouch(FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
        this.dropWhenSilkTouch(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER);
        this.otherWhenSilkTouch(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT, FOTBlocks.UNDERRIPE_BANANA_CLUSTER);
        this.otherWhenSilkTouch(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT, FOTBlocks.BARELY_RIPE_BANANA_CLUSTER);
        this.add(FOTBlocks.PINEAPPLE_CROP, this::createPineappleCropLoot);
        this.add(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK, block -> this.createSingleItemTableWithSilkTouch(block, FOTItems.PINEAPPLE_CROWN));
        this.add(FOTBlocks.RIPE_PINEAPPLE_BLOCK, block -> this.createSingleItemTableWithSilkTouch(block, FOTItems.PINEAPPLE));
        this.add(FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, block -> this.createSingleItemTableWithSilkTouch(block, FOTItems.CROWNLESS_PINEAPPLE));
        this.add(FOTBlocks.MANGO_LEAVES, block -> this.createLeavesDrops(block, FOTBlocks.MANGO_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(FOTBlocks.MANGO_FRUIT, this::createMangoFruitDrops);
        this.add(FOTBlocks.HANGING_MANGO_FRUIT, this::createMangoFruitDrops);
        this.dropSelf(FOTBlocks.MANGO_PIT);
        this.dropSelf(FOTBlocks.MANGO_SAPLING);
        this.dropPottedContents(FOTBlocks.POTTED_MANGO_PIT);
        this.dropPottedContents(FOTBlocks.POTTED_MANGO_SAPLING);
        this.dropPottedContents(FOTBlocks.POTTED_BANANA_SHOOTS);
        this.dropPottedContents(FOTBlocks.POTTED_POMEGRANATE_SAPLING);
        this.add(FOTBlocks.POMEGRANATE_PLANT, block -> this.createPomegranatePlant(block, registryLookup));
        this.createTallPomegranatePlant(registryLookup);
        this.dropPottedContents(FOTBlocks.POTTED_POMEGRANATE_PLANT);
        this.dropSelf(FOTBlocks.POMEGRANATE_SAPLING);
        this.dropSelf(FOTBlocks.TROPICAL_RED_FERN);
        this.dropPottedContents(FOTBlocks.POTTED_TROPICAL_RED_FERN);
        this.dropSelf(FOTBlocks.TROPICAL_MONSTERA);
        this.dropPottedContents(FOTBlocks.POTTED_TROPICAL_MONSTERA);
        this.dropSelf(FOTBlocks.PRISMARIZED_LOG);
        this.dropOther(FOTBlocks.BUDDING_PRISMARIZED_LOG, FOTBlocks.PRISMARIZED_LOG);
        this.dropOther(FOTBlocks.GUARDIAN_FRUIT, FOTItems.GUARDIAN_FRUIT);
        this.dropOther(FOTBlocks.VERTICAL_BANANA_LEAVES, FOTItems.BANANA_LEAVES);
        this.dropSelf(FOTBlocks.COCONUT_SHELF);
    }

    private LootTable.Builder createPomegranatePlant(Block block, HolderLookup.RegistryLookup<Enchantment> registryLookup)
    {
        return this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(AlternativesEntry.alternatives(
                                AlternativesEntry.alternatives(IntStream.rangeClosed(0, 3).boxed().toList(), age -> LootItem.lootTableItem(block)
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(PomegranatePlantBlock.AGE, age))))))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.POMEGRANATE)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(PomegranatePlantBlock.AGE, 3)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE))))));
    }

    private void createTallPomegranatePlant(HolderLookup.RegistryLookup<Enchantment> registryLookup)
    {
        var isLower = LootItemBlockStatePropertyCondition.hasBlockStateProperties(FOTBlocks.TALL_POMEGRANATE_PLANT).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

        this.add(FOTBlocks.TALL_POMEGRANATE_PLANT, blockx -> this.applyExplosionDecay(blockx, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(AlternativesEntry.alternatives(
                                AlternativesEntry.alternatives(IntStream.rangeClosed(0, 3).boxed().toList(), age -> LootItem.lootTableItem(blockx)
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blockx)
                                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(TallPomegranatePlantBlock.AGE, age))))))
                        .when(isLower)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(FOTItems.POMEGRANATE)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blockx)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(TallPomegranatePlantBlock.AGE, 3)))
                                .when(isLower)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))))));
    }

    private LootTable.Builder createMangoFruitDrops(Block block)
    {
        return this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(MangoFruitBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(FOTItems.MANGO))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(MangoFruitBlock.AGE, 1)))
                        .add(LootItem.lootTableItem(FOTItems.RAW_MANGO))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))));
    }

    private LootTable.Builder createPineappleCropLoot(Block block)
    {
        var isLower = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        return this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(AlternativesEntry.alternatives(

                        AlternativesEntry.alternatives(LootItem.lootTableItem(FOTItems.PINEAPPLE_SEEDS)
                                        .when(isLower)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(PineappleCropBlock.AGE, 0))),

                        AlternativesEntry.alternatives(LootItem.lootTableItem(FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .when(this.hasSilkTouch())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(PineappleCropBlock.AGE, 4))),

                        AlternativesEntry.alternatives(LootItem.lootTableItem(FOTBlocks.RIPE_PINEAPPLE_BLOCK)
                                        .when(this.hasSilkTouch())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .otherwise(LootItem.lootTableItem(FOTItems.PINEAPPLE)
                                                .when(isLower)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(PineappleCropBlock.AGE, 5))),

                        AlternativesEntry.alternatives(LootItem.lootTableItem(FOTItems.PINEAPPLE_CROWN)
                                        .apply(List.of(1, 2, 3, 4), age -> SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PineappleCropBlock.AGE, age)))))
                                .when(isLower)
                ))));
    }
}