package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.blockpredicates.BlockBrightnessPredicate;
import com.stevekung.fishofthieves.feature.blockpredicates.SeeSkyPredicate;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class FOTBlockPredicateTypes
{
    public static final BlockPredicateType<SeeSkyPredicate> SEE_SKY = () -> SeeSkyPredicate.CODEC;
    public static final BlockPredicateType<BlockBrightnessPredicate> BLOCK_BRIGHTNESS = () -> BlockBrightnessPredicate.CODEC;

    public static void init()
    {
        register("see_sky", SEE_SKY);
        register("block_brightness", BLOCK_BRIGHTNESS);
    }

    private static <P extends BlockPredicate> void register(String name, BlockPredicateType<P> type)
    {
        Registry.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, FishOfThieves.id(name), type);
    }
}