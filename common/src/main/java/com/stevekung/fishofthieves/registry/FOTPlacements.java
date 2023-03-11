package com.stevekung.fishofthieves.registry;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class FOTPlacements
{
    public static final ResourceKey<PlacedFeature> FISH_BONE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, FishOfThieves.res("fish_bone"));

    public static final Holder<PlacedFeature> FISH_BONE = register("fish_bone", FOTFeatures.FISH_BONE, RarityFilter.onAverageOnceEvery(384), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(UniformInt.of(4, 8)), BiomeFilter.biome());

    public static void init() {}

    private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placements)
    {
        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, FishOfThieves.res(name), new PlacedFeature(Holder.hackyErase(feature), List.of(placements)));
    }
}