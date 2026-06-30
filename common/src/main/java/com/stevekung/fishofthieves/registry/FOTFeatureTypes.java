package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import com.stevekung.fishofthieves.feature.SimpleAgeBlockFeature;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FOTFeatureTypes
{
    public static final MapCodec<FishBoneFeature> FISH_BONE = register("fish_bone", FishBoneFeature.CODEC);
    public static final MapCodec<SimpleAgeBlockFeature> SIMPLE_AGE_BLOCK = register("simple_age_block", SimpleAgeBlockFeature.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Feature Types");
    }

    private static <T extends Feature> MapCodec<T> register(String key, MapCodec<T> codec)
    {
        return Registry.register(BuiltInRegistries.FEATURE_TYPE, FishOfThieves.id(key), codec);
    }
}