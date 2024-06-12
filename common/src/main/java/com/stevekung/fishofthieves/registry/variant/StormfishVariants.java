package com.stevekung.fishofthieves.registry.variant;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;

public class StormfishVariants
{
    public static final ResourceKey<StormfishVariant> ANCIENT = createKey("ancient");
    public static final ResourceKey<StormfishVariant> SHORES = createKey("shores");
    public static final ResourceKey<StormfishVariant> WILD = createKey("wild");
    public static final ResourceKey<StormfishVariant> SHADOW = createKey("shadow");
    public static final ResourceKey<StormfishVariant> TWILIGHT = createKey("twilight");

    public static void bootstrap(BootstrapContext<StormfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("stormfish", FOTItems.STORMFISH, StormfishVariant::new);
        registerContext.register(context, ANCIENT, "ancient", 0);
        registerContext.register(context, SHORES, "shores", 1, ContinentalnessCondition.builder().continentalness(Continentalness.COAST).build());
        registerContext.register(context, WILD, "wild", 2, MatchBiomeCondition.biomes(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.SPARSE_JUNGLE))).build());
        registerContext.register(context, SHADOW, "shadow", 3, List.of(AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), SkyBrightnessCondition.skyBrightness(0, 4)).build()), List.of(ProbabilityCondition.defaultRareProbablity().build()));
        registerContext.register(context, TWILIGHT, "twilight", 4, true, SkyDarkenCondition.skyDarken(9, 16).build());
    }

    public static void bootstrapSimple(BootstrapContext<StormfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("stormfish", FOTItems.STORMFISH, StormfishVariant::new);
        registerContext.register(context, ANCIENT, "ancient", 0);
        registerContext.register(context, SHORES, "shores", 1);
        registerContext.register(context, WILD, "wild", 2);
        registerContext.register(context, SHADOW, "shadow", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, TWILIGHT, "twilight", 4, true, List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater()).build()), List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build()));
    }

    private static ResourceKey<StormfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.STORMFISH_VARIANT, FishOfThieves.id(name));
    }
}