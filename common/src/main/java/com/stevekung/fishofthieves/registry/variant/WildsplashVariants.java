package com.stevekung.fishofthieves.registry.variant;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;

public class WildsplashVariants
{
    public static final ResourceKey<WildsplashVariant> RUSSET = createKey("russet");
    public static final ResourceKey<WildsplashVariant> SANDY = createKey("sandy");
    public static final ResourceKey<WildsplashVariant> OCEAN = createKey("ocean");
    public static final ResourceKey<WildsplashVariant> MUDDY = createKey("muddy");
    public static final ResourceKey<WildsplashVariant> CORAL = createKey("coral");

    public static void bootstrap(BootstrapContext<WildsplashVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("wildsplash", WildsplashVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, RUSSET, "russet", 0);
        registerContext.register(context, SANDY, "sandy", 1, MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.IS_BEACH)).and(ContinentalnessCondition.builder().continentalness(Continentalness.COAST)).build());
        registerContext.register(context, OCEAN, "ocean", 2, MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.IS_OCEAN)).build());
        registerContext.register(context, MUDDY, "muddy", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.HAS_CLOSER_WATER_FOG))).build());
        registerContext.register(context, CORAL, "coral", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky(), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.WARM_OCEAN))), MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON)), Optional.empty(), 4, 24)).build());
    }

    public static void bootstrapSimple(BootstrapContext<WildsplashVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("wildsplash", WildsplashVariant::new);
        registerContext.register(context, RUSSET, "russet", 0);
        registerContext.register(context, SANDY, "sandy", 1);
        registerContext.register(context, OCEAN, "ocean", 2);
        registerContext.register(context, MUDDY, "muddy", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, CORAL, "coral", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    private static ResourceKey<WildsplashVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WILDSPLASH_VARIANT, FishOfThieves.id(name));
    }
}