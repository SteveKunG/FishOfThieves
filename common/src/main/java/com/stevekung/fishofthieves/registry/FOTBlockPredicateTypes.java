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
    public static final BlockPredicateType<SeeSkyPredicate> SEE_SKY = register("see_sky", () -> SeeSkyPredicate.CODEC);
    public static final BlockPredicateType<BlockBrightnessPredicate> BLOCK_BRIGHTNESS = register("block_brightness", () -> BlockBrightnessPredicate.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Block Predicate Type");
    }

    private static <P extends BlockPredicate> BlockPredicateType<P> register(String name, BlockPredicateType<P> type)
    {
        return Registry.register(BuiltInRegistries.BLOCK_PREDICATE_TYPE, FishOfThieves.id(name), type);
    }
}