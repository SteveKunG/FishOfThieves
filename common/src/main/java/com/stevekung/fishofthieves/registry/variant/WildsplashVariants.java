package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.WildsplashVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
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
        var biomeLookup = context.lookup(Registries.BIOME);
        register(context, RUSSET, "russet", 0);
        register(context, SANDY, "sandy", 1, MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.IS_BEACH)).and(ContinentalnessCondition.builder().continentalness(Continentalness.COAST)).build());
        register(context, OCEAN, "ocean", 2, MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.IS_OCEAN)).build());
        register(context, MUDDY, "muddy", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.HAS_CLOSER_WATER_FOG))).build());
        register(context, CORAL, "coral", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater(), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.WARM_OCEAN))), MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.CORAL_WILDSPLASH_SPAWNABLE_ON)), Optional.empty(), 4, 24)).build());
    }

    static void register(BootstrapContext<WildsplashVariant> context, ResourceKey<WildsplashVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<WildsplashVariant> context, ResourceKey<WildsplashVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/wildsplash/" + name);
        var glowTexture = FishOfThieves.id("entity/wildsplash/" + name + "_glow");
        context.register(key, new WildsplashVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.WILDSPLASH), customModelData));
    }

    private static ResourceKey<WildsplashVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.WILDSPLASH_VARIANT, FishOfThieves.id(name));
    }
}