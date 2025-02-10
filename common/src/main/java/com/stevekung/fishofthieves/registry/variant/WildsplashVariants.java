package com.stevekung.fishofthieves.registry.variant;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.variant.BiomeCheck;
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
        registerContext.register(context, SANDY, "sandy", 1,
                new BiomeCheck(biomeLookup.getOrThrow(BiomeTags.IS_BEACH)).and(ContinentalnessCondition.continentalness(Continentalness.COAST))
                        .or(new BiomeCheck(HolderSet.direct(biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND)))));
        registerContext.register(context, OCEAN, "ocean", 2, new BiomeCheck(biomeLookup.getOrThrow(BiomeTags.IS_OCEAN)));
        registerContext.register(context, MUDDY, "muddy", 3, ProbabilityCondition.defaultRareProbablity().and(new BiomeCheck(biomeLookup.getOrThrow(BiomeTags.HAS_CLOSER_WATER_FOG))));
        registerContext.register(context, CORAL, "coral", 4, true, NightCondition.night().and(SeeSkyCondition.seeSky())
                .and(new BiomeCheck(HolderSet.direct(biomeLookup.getOrThrow(Biomes.WARM_OCEAN))))
                .and(MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON)), Optional.empty(), 4, 24)));
    }

    public static void bootstrapSimple(BootstrapContext<WildsplashVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("wildsplash", WildsplashVariant::new);
        registerContext.register(context, RUSSET, "russet", 0);
        registerContext.register(context, SANDY, "sandy", 1);
        registerContext.register(context, OCEAN, "ocean", 2);
        registerContext.register(context, MUDDY, "muddy", 3, ProbabilityCondition.defaultRareProbablity());
        registerContext.register(context, CORAL, "coral", 4, true, NightCondition.night().and(SeeSkyCondition.seeSky()));
    }

    private static ResourceKey<WildsplashVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WILDSPLASH_VARIANT, FishOfThieves.id(name));
    }
}