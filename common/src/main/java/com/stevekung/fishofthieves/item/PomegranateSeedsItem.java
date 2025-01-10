package com.stevekung.fishofthieves.item;

import java.util.Map;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;

public class PomegranateSeedsItem extends ItemNameBlockItem
{
    public PomegranateSeedsItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void registerBlocks(Map<Block, Item> blockToItemMap, Item item)
    {
        // Don't add item to block map, it will be replaced by FOTBlocks.POMEGRANATE_PLANT
    }
}