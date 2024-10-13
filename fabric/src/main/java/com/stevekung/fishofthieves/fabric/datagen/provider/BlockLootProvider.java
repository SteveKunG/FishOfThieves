package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.List;

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
        this.dropSelf(FOTBlocks.FISH_BONE);

        this.dropSelf(FOTBlocks.OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.CHERRY_FISH_PLAQUE);
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
        this.dropSelf(FOTBlocks.IRON_FRAME_BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.GOLDEN_FRAME_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_SPRUCE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_BIRCH_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_JUNGLE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_ACACIA_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_DARK_OAK_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_MANGROVE_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_CHERRY_FISH_PLAQUE);
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
        this.dropSelf(FOTBlocks.GILDED_BAMBOO_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_CRIMSON_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_WARPED_FISH_PLAQUE);

        this.dropSelf(FOTBlocks.PINK_PLUMERIA);
        this.dropPottedContents(FOTBlocks.POTTED_PINK_PLUMERIA);
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
        this.dropSelf(FOTBlocks.COCONUT_FRONDS);
        this.dropSelf(FOTBlocks.BANANA_STEM);
        this.dropOther(FOTBlocks.VERTICAL_BANANA_LEAVES, FOTBlocks.BANANA_LEAVES);
        this.dropOther(FOTBlocks.VERTICAL_COCONUT_FRONDS, FOTBlocks.COCONUT_FRONDS);
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
        this.add(FOTBlocks.COCONUT_SLAB, this::createSlabItemTable);
        this.dropSelf(FOTBlocks.COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.IRON_FRAME_COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GOLDEN_FRAME_COCONUT_FISH_PLAQUE);
        this.dropSelf(FOTBlocks.GILDED_COCONUT_FISH_PLAQUE);
    }
}