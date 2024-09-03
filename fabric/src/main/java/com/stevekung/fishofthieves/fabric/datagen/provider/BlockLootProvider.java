package com.stevekung.fishofthieves.fabric.datagen.provider;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

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
    }
}