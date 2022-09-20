package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.block.FishBoneBlock;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class FOTBlocks
{
    public static final Block FISH_BONE = new FishBoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(0.25f).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.BONE_BLOCK));

    public static void init()
    {
        register("fish_bone", FISH_BONE);
    }

    private static void register(String key, Block block)
    {
        FOTPlatform.registerBlock(key, block);
    }
}