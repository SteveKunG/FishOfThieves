package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class BattlegillVariants
{
    public static final ResourceKey<BattlegillVariant> JADE = createKey("jade");
    public static final ResourceKey<BattlegillVariant> SKY = createKey("sky");
    public static final ResourceKey<BattlegillVariant> RUM = createKey("rum");
    public static final ResourceKey<BattlegillVariant> SAND = createKey("sand");
    public static final ResourceKey<BattlegillVariant> BITTERSWEET = createKey("bittersweet");

    public static void bootstrap(BootstrapContext<BattlegillVariant> context)
    {
        register(context, JADE, "jade");
        register(context, SKY, "sky", AnyOfCondition.anyOf(SeeSkyInWaterCondition.seeSkyInWater()).build());
        register(context, RUM, "rum");
        register(context, SAND, "sand", AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(context.lookup(Registries.BIOME).getOrThrow(FOTTags.Biomes.SPAWNS_SAND_BATTLEGILLS))).build());
        register(context, BITTERSWEET, "bittersweet", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<BattlegillVariant> context, ResourceKey<BattlegillVariant> key, String name, SpawnCondition... conditions)
    {
        register(context, key, name, false, conditions);
    }

    static void register(BootstrapContext<BattlegillVariant> context, ResourceKey<BattlegillVariant> key, String name, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.res("entity/battlegill/" + name);
        var glowTexture = FishOfThieves.res("entity/battlegill/" + name + "_glow");
        context.register(key, new BattlegillVariant(texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions)));
    }

    private static ResourceKey<BattlegillVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.BATTLEGILL_VARIANT, FishOfThieves.res(name));
    }
}