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
    public static final ResourceKey<WildsplashVariant> CALICO = createKey("calico");

    public static void bootstrap(BootstrapContext<WildsplashVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("wildsplash", WildsplashVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, RUSSET, "russet", 0);
        registerContext.register(context, SANDY, "sandy", 1,
                registerContext.select(new BiomeCheck(HolderSet.direct(biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND))), 1),
                registerContext.select(AllConditionCheck.allOf(new BiomeCheck(biomeLookup.getOrThrow(BiomeTags.IS_BEACH)), ContinentalnessCheck.continentalness(Continentalness.COAST)), 0)
        );
        registerContext.register(context, OCEAN, "ocean", 2, new BiomeCheck(biomeLookup.getOrThrow(BiomeTags.IS_OCEAN)));
        registerContext.register(context, MUDDY, "muddy", 3, AllConditionCheck.allOf(ProbabilityCheck.defaultRareProbablity(), new BiomeCheck(HolderSet.direct(biomeLookup.getOrThrow(Biomes.SWAMP), biomeLookup.getOrThrow(Biomes.MANGROVE_SWAMP)))));
        registerContext.register(context, CORAL, "coral", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky(),
                new BiomeCheck(HolderSet.direct(biomeLookup.getOrThrow(Biomes.WARM_OCEAN))),
                MinimumBlockRangeCheck.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON)), Optional.empty(), 4, 16)));
        registerContext.register(context, CALICO, "calico", 5, true, true);
    }

    public static void bootstrapSimple(BootstrapContext<WildsplashVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("wildsplash", WildsplashVariant::new);
        registerContext.register(context, RUSSET, "russet", 0);
        registerContext.register(context, SANDY, "sandy", 1);
        registerContext.register(context, OCEAN, "ocean", 2);
        registerContext.register(context, MUDDY, "muddy", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, CORAL, "coral", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
        registerContext.register(context, CALICO, "calico", 5, true, true);
    }

    private static ResourceKey<WildsplashVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WILDSPLASH_VARIANT, FishOfThieves.id(name));
    }
}