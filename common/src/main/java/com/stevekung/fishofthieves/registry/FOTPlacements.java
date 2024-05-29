package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.*;

public class FOTPlacements
{
    public static final ResourceKey<PlacedFeature> FISH_BONE = createKey("fish_bone");

    public static void bootstrap(BootstrapContext<PlacedFeature> BootstrapContext)
    {
        var holderGetter = BootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(BootstrapContext, FISH_BONE, holderGetter.getOrThrow(FOTFeatures.FISH_BONE), RarityFilter.onAverageOnceEvery(384), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(UniformInt.of(4, 8)), BiomeFilter.biome());
    }

    private static ResourceKey<PlacedFeature> createKey(String key)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, FishOfThieves.id(key));
    }
}