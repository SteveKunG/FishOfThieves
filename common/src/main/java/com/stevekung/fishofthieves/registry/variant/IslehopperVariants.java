package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.BuiltInRegistries;
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
        register(context, STONE, "stone", 0);
        register(context, MOSS, "moss", 1, MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_MOSS_ISLEHOPPERS)).build());
        register(context, HONEY, "honey", 2, HasBeehiveCondition.beehive(5, 9).build());
        register(context, RAVEN, "raven", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), HeightCondition.height(-64, 0)).build());
        register(context, AMETHYST, "amethyst", 4, true, MatchMinimumBlocksInRangeCondition.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON)), Optional.empty(), 4, 16).build());
    }

    static void register(BootstrapContext<IslehopperVariant> context, ResourceKey<IslehopperVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<IslehopperVariant> context, ResourceKey<IslehopperVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/islehopper/" + name);
        var glowTexture = FishOfThieves.id("entity/islehopper/" + name + "_glow");
        context.register(key, new IslehopperVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.ISLEHOPPER), customModelData == 0 ? Optional.empty() : Optional.of(customModelData)));
    }

    private static ResourceKey<IslehopperVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ISLEHOPPER_VARIANT, FishOfThieves.id(name));
    }
}