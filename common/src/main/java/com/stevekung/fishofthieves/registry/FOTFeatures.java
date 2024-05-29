package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.FishBoneFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FOTFeatures
{
    private static final FishBoneFeature FISH_BONE_FEATURE = new FishBoneFeature(NoneFeatureConfiguration.CODEC);

    public static final ResourceKey<ConfiguredFeature<?, ?>> FISH_BONE = createKey("fish_bone");

    public static void init()
    {
        register("fish_bone", FISH_BONE_FEATURE);
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> BootstrapContext)
    {
        FeatureUtils.register(BootstrapContext, FISH_BONE, FISH_BONE_FEATURE, NoneFeatureConfiguration.INSTANCE);
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> void register(String key, F value)
    {
        FOTPlatform.registerFeature(key, value);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FishOfThieves.id(name));
    }
}