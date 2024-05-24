package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class IslehopperVariants
{
    public static final ResourceKey<IslehopperVariant> STONE = createKey("stone");
    public static final ResourceKey<IslehopperVariant> MOSS = createKey("moss");
    public static final ResourceKey<IslehopperVariant> HONEY = createKey("honey");
    public static final ResourceKey<IslehopperVariant> RAVEN = createKey("raven");
    public static final ResourceKey<IslehopperVariant> AMETHYST = createKey("amethyst");

    public static void bootstrap(BootstrapContext<IslehopperVariant> context)
    {
        register(context, STONE, "stone");
        register(context, MOSS, "moss", MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_MOSS_ISLEHOPPERS)).build());
        register(context, HONEY, "honey", HasBeehiveCondition.beehive(5, 9).build());
        register(context, RAVEN, "raven", AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), HeightCondition.height(-64, 0)).build());
        register(context, AMETHYST, "amethyst", true, AllOfCondition.allOf(MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON)), Optional.empty(), 4, 16)).build());
    }

    static void register(BootstrapContext<IslehopperVariant> context, ResourceKey<IslehopperVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<IslehopperVariant> context, ResourceKey<IslehopperVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/islehopper/" + name);
        var glowTexture = FishOfThieves.res("entity/islehopper/" + name + "_glow");
        context.register(key, new IslehopperVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<IslehopperVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ISLEHOPPER_VARIANT, FishOfThieves.res(name));
    }
}