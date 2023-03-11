package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FOTFeatures
{
    private static final FishBoneFeature FISH_BONE_FEATURE = new FishBoneFeature(NoneFeatureConfiguration.CODEC);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> FISH_BONE = register("fish_bone", FISH_BONE_FEATURE, NoneFeatureConfiguration.INSTANCE);

    public static void init()
    {
        register("fish_bone", FISH_BONE_FEATURE);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC config)
    {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, FishOfThieves.MOD_RESOURCES + name, new ConfiguredFeature<>(feature, config));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> void register(String key, F value)
    {
        FOTPlatform.registerFeature(key, value);
    }
}