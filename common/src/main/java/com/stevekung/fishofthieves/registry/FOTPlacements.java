package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.*;

public class FOTPlacements
{
    public static final ResourceKey<PlacedFeature> FISH_BONE = createKey("fish_bone");
    public static final ResourceKey<PlacedFeature> COCONUT_TREE_CHECKED = createKey("coconut_tree_checked");

    public static void bootstrap(BootstapContext<PlacedFeature> bootstapContext)
    {
        var holderGetter = bootstapContext.lookup(Registries.CONFIGURED_FEATURE);
        var coconutTreeHolder = holderGetter.getOrThrow(FOTFeatures.COCONUT_TREE);

        PlacementUtils.register(bootstapContext, FISH_BONE, holderGetter.getOrThrow(FOTFeatures.FISH_BONE), RarityFilter.onAverageOnceEvery(384), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(UniformInt.of(4, 8)), BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, COCONUT_TREE_CHECKED, coconutTreeHolder, PlacementUtils.filteredByBlockSurvival(FOTBlocks.COCONUT_SAPLING));
    }

    private static ResourceKey<PlacedFeature> createKey(String key)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, FishOfThieves.id(key));
    }
}