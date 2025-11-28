package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.StormfishVariant;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.utils.Continentalness;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.level.biome.Biomes;

public class StormfishVariants
{
    public static final ResourceKey<StormfishVariant> ANCIENT = createKey("ancient");
    public static final ResourceKey<StormfishVariant> SHORES = createKey("shores");
    public static final ResourceKey<StormfishVariant> WILD = createKey("wild");
    public static final ResourceKey<StormfishVariant> SHADOW = createKey("shadow");
    public static final ResourceKey<StormfishVariant> TWILIGHT = createKey("twilight");
    public static final ResourceKey<StormfishVariant> STARSHINE = createKey("starshine");

    public static void bootstrap(BootstrapContext<StormfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("stormfish", StormfishVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, ANCIENT, "ancient", 0);
        registerContext.register(context, SHORES, "shores", 1, ContinentalnessCheck.continentalness(Continentalness.COAST));
        registerContext.register(context, WILD, "wild", 2, new BiomeCheck(HolderSet.direct(
                biomeLookup.getOrThrow(Biomes.SPARSE_JUNGLE),
                biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND))));
        registerContext.register(context, SHADOW, "shadow", 3,
                registerContext.select(AllConditionCheck.allOf(ProbabilityCheck.defaultRareProbablity(), SkyBrightnessCheck.skyBrightness(MinMaxBounds.Ints.atMost(4))), 1),
                registerContext.select(ProbabilityCheck.defaultRareProbablity(), 0));
        registerContext.register(context, TWILIGHT, "twilight", 4, true, SkyDarkenCheck.skyDarken(MinMaxBounds.Ints.between(9, 16)));
        registerContext.register(context, STARSHINE, "starshine", 5, true, true);
    }

    public static void bootstrapSimple(BootstrapContext<StormfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("stormfish", StormfishVariant::new);
        registerContext.register(context, ANCIENT, "ancient", 0);
        registerContext.register(context, SHORES, "shores", 1);
        registerContext.register(context, WILD, "wild", 2);
        registerContext.register(context, SHADOW, "shadow", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, TWILIGHT, "twilight", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
        registerContext.register(context, STARSHINE, "starshine", 5, true, true);
    }

    private static ResourceKey<StormfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.STORMFISH_VARIANT, FishOfThieves.id(name));
    }
}