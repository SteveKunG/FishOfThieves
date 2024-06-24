package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;

public class IslehopperVariants
{
    public static final ResourceKey<IslehopperVariant> STONE = createKey("stone");
    public static final ResourceKey<IslehopperVariant> MOSS = createKey("moss");
    public static final ResourceKey<IslehopperVariant> HONEY = createKey("honey");
    public static final ResourceKey<IslehopperVariant> RAVEN = createKey("raven");
    public static final ResourceKey<IslehopperVariant> AMETHYST = createKey("amethyst");

    public static void bootstrap(BootstrapContext<IslehopperVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("islehopper", FOTItems.ISLEHOPPER, IslehopperVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, STONE, "stone", 0);
        registerContext.register(context, MOSS, "moss", 1, AnyOfCondition.anyOf(MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.IS_JUNGLE)), MatchBiomeCondition.biomes(biomeLookup.getOrThrow(BiomeTags.HAS_CLOSER_WATER_FOG)), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.LUSH_CAVES)))).build());
        registerContext.register(context, HONEY, "honey", 2, HasBeehiveCondition.beehive(5, 9).build());
        registerContext.register(context, RAVEN, "raven", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), HeightCondition.height(MinMaxBounds.Ints.atMost(0))).build());
        registerContext.register(context, AMETHYST, "amethyst", 4, true, MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON)), Optional.empty(), 4, 16).build());
    }

    public static void bootstrapSimple(BootstrapContext<IslehopperVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("islehopper", FOTItems.ISLEHOPPER, IslehopperVariant::new);
        registerContext.register(context, STONE, "stone", 0);
        registerContext.register(context, MOSS, "moss", 1);
        registerContext.register(context, HONEY, "honey", 2);
        registerContext.register(context, RAVEN, "raven", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, AMETHYST, "amethyst", 4, true, List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater()).build()), List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build()));
    }

    private static ResourceKey<IslehopperVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ISLEHOPPER_VARIANT, FishOfThieves.id(name));
    }
}