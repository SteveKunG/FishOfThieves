package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.block.FishBoneBlock;
import com.stevekung.fishofthieves.block.FishPlaqueBlock;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class FOTBlocks
{
    public static final Block FISH_BONE = new FishBoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(0.25f).dynamicShape().offsetType(BlockBehaviour.OffsetType.XYZ).sound(SoundType.BONE_BLOCK));

    public static final Block OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.OAK_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block SPRUCE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.SPRUCE_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block BIRCH_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.BIRCH_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block JUNGLE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.JUNGLE_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block ACACIA_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.ACACIA_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block DARK_OAK_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.DARK_OAK_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block MANGROVE_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.MANGROVE_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    //TODO Bamboo
    public static final Block CRIMSON_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.CRIMSON_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));
    public static final Block WARPED_FISH_PLAQUE = new FishPlaqueBlock(BlockBehaviour.Properties.of(Material.WOOD, Blocks.WARPED_PLANKS.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD));

    public static void init()
    {
        register("fish_bone", FISH_BONE);

        register("oak_fish_plaque", OAK_FISH_PLAQUE);
        register("spruce_fish_plaque", SPRUCE_FISH_PLAQUE);
        register("birch_fish_plaque", BIRCH_FISH_PLAQUE);
        register("jungle_fish_plaque", JUNGLE_FISH_PLAQUE);
        register("acacia_fish_plaque", ACACIA_FISH_PLAQUE);
        register("dark_oak_fish_plaque", DARK_OAK_FISH_PLAQUE);
        register("mangrove_fish_plaque", MANGROVE_FISH_PLAQUE);
        register("crimson_fish_plaque", CRIMSON_FISH_PLAQUE);
        register("warped_fish_plaque", WARPED_FISH_PLAQUE);
    }

    private static void register(String key, Block block)
    {
        FOTPlatform.registerBlock(key, block);
    }
}