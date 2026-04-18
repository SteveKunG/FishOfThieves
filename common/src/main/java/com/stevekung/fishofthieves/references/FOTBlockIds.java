package com.stevekung.fishofthieves.references;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class FOTBlockIds
{
    public static final ResourceKey<Block> POTTED_PINK_PLUMERIA = create("potted_pink_plumeria");
    public static final ResourceKey<Block> POTTED_LIGHT_BLUE_PLUMERIA = create("potted_light_blue_plumeria");
    public static final ResourceKey<Block> POTTED_WHITE_PLUMERIA = create("potted_white_plumeria");
    public static final ResourceKey<Block> POTTED_BANANA_SHOOTS = create("potted_banana_shoots");
    public static final ResourceKey<Block> POTTED_MANGO_PIT = create("potted_mango_pit");
    public static final ResourceKey<Block> POTTED_MANGO_SAPLING = create("potted_mango_sapling");
    public static final ResourceKey<Block> POTTED_POMEGRANATE_PLANT = create("potted_pomegranate_plant");
    public static final ResourceKey<Block> POTTED_POMEGRANATE_SAPLING = create("potted_pomegranate_sapling");
    public static final ResourceKey<Block> POTTED_TROPICAL_RED_FERN = create("potted_tropical_red_fern");
    public static final ResourceKey<Block> POTTED_TROPICAL_MONSTERA = create("potted_tropical_monstera");

    private static ResourceKey<Block> create(String name)
    {
        return ResourceKey.create(Registries.BLOCK, FishOfThieves.id(name));
    }
}