package com.stevekung.fishofthieves.fabric.datagen.provider;

import com.stevekung.fishofthieves.block.BananaLeavesBlock;
import com.stevekung.fishofthieves.block.CoconutFruitBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class BlockLootProvider extends FabricBlockLootTableProvider
{
    public BlockLootProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void generate()
    {
        this.add(FOTBlocks.FISH_BONE, this.createSingleItemTable(FOTBlocks.FISH_BONE));

        this.add(FOTBlocks.OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.OAK_FISH_PLAQUE));
        this.add(FOTBlocks.SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.SPRUCE_FISH_PLAQUE));
        this.add(FOTBlocks.BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.BIRCH_FISH_PLAQUE));
        this.add(FOTBlocks.JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.JUNGLE_FISH_PLAQUE));
        this.add(FOTBlocks.ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.ACACIA_FISH_PLAQUE));
        this.add(FOTBlocks.DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.DARK_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.MANGROVE_FISH_PLAQUE));
        this.add(FOTBlocks.CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.CHERRY_FISH_PLAQUE));
        this.add(FOTBlocks.BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.BAMBOO_FISH_PLAQUE));
        this.add(FOTBlocks.CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.CRIMSON_FISH_PLAQUE));
        this.add(FOTBlocks.WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.WARPED_FISH_PLAQUE));

        this.add(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_SPRUCE_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_BIRCH_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_JUNGLE_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_ACACIA_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_DARK_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_MANGROVE_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_CHERRY_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE));
        this.add(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE));

        this.add(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_BAMBOO_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_CRIMSON_FISH_PLAQUE));
        this.add(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GOLDEN_FRAME_WARPED_FISH_PLAQUE));

        this.add(FOTBlocks.GILDED_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_SPRUCE_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_BIRCH_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_JUNGLE_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_ACACIA_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_DARK_OAK_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_MANGROVE_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_CHERRY_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE));
        this.add(FOTBlocks.GILDED_WARPED_FISH_PLAQUE, this.createSingleItemTable(FOTBlocks.GILDED_WARPED_FISH_PLAQUE));

        this.dropSelf(FOTBlocks.PINK_PLUMERIA);
        this.dropPottedContents(FOTBlocks.POTTED_PINK_PLUMERIA);
        this.dropSelf(FOTBlocks.SMALL_COCONUT_LOG);
        this.dropSelf(FOTBlocks.SMALL_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.MEDIUM_COCONUT_LOG);
        this.dropSelf(FOTBlocks.MEDIUM_COCONUT_WOOD);
        this.dropSelf(FOTBlocks.COCONUT_LOG);
        this.dropSelf(FOTBlocks.COCONUT_WOOD);
        this.dropSelf(FOTBlocks.COCONUT_SAPLING);
        this.add(FOTBlocks.COCONUT_FRUIT, block -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(FOTItems.COCONUT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 4f))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CoconutFruitBlock.AGE, 2)))));
        this.dropSelf(FOTBlocks.COCONUT_FRONDS);
        this.dropSelf(FOTBlocks.BANANA_STEM);
        this.add(FOTBlocks.BANANA_LEAVES, block -> this.createSinglePropConditionTable(block, BananaLeavesBlock.PART, BananaLeavesBlock.Part.STEM));
        this.dropOther(FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.BANANA_LEAVES);
        this.dropOther(FOTBlocks.VERTICAL_COCONUT_FRONDS, FOTBlocks.COCONUT_FRONDS);
    }
}