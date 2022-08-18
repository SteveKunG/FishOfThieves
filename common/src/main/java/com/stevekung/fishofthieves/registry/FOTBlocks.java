package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.block.BoneFishBlock;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class FOTBlocks
{
    public static final Block BONE_FISH = new BoneFishBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(0.25f).dynamicShape().sound(SoundType.BONE_BLOCK));

    public static void init()
    {
        register("bone_fish", BONE_FISH);
    }

    private static void register(String key, Block block)
    {
        FOTPlatform.registerBlock(key, block);
    }
}