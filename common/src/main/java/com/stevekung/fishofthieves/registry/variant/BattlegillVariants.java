package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;

public class BattlegillVariants
{
    public static final ResourceKey<BattlegillVariant> JADE = createKey("jade");
    public static final ResourceKey<BattlegillVariant> SKY = createKey("sky");
    public static final ResourceKey<BattlegillVariant> RUM = createKey("rum");
    public static final ResourceKey<BattlegillVariant> SAND = createKey("sand");
    public static final ResourceKey<BattlegillVariant> BITTERSWEET = createKey("bittersweet");

    public static void bootstrap(BootstrapContext<BattlegillVariant> context)
    {
        var biomeLookup = context.lookup(Registries.BIOME);
        register(context, JADE, "jade", 0);
        register(context, SKY, "sky", 1, SeeSkyInWaterCondition.seeSkyInWater().build());
        register(context, RUM, "rum", 2);
        register(context, SAND, "sand", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.DESERT), biomeLookup.getOrThrow(Biomes.WARM_OCEAN), biomeLookup.getOrThrow(Biomes.LUKEWARM_OCEAN), biomeLookup.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN)))).build());
        register(context, BITTERSWEET, "bittersweet", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    public static void bootstrapSimple(BootstrapContext<BattlegillVariant> context)
    {
        register(context, JADE, "jade", 0);
        register(context, SKY, "sky", 1);
        register(context, RUM, "rum", 2);
        register(context, SAND, "sand", 3, ProbabilityCondition.defaultRareProbablity().build());
        register(context, BITTERSWEET, "bittersweet", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<BattlegillVariant> context, ResourceKey<BattlegillVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<BattlegillVariant> context, ResourceKey<BattlegillVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/battlegill/" + name);
        var glowTexture = FishOfThieves.id("entity/battlegill/" + name + "_glow");
        context.register(key, new BattlegillVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.BATTLEGILL), customModelData));
    }

    private static ResourceKey<BattlegillVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.BATTLEGILL_VARIANT, FishOfThieves.id(name));
    }
}