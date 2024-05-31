package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class AncientscaleVariants
{
    public static final ResourceKey<AncientscaleVariant> ALMOND = createKey("almond");
    public static final ResourceKey<AncientscaleVariant> SAPPHIRE = createKey("sapphire");
    public static final ResourceKey<AncientscaleVariant> SMOKE = createKey("smoke");
    public static final ResourceKey<AncientscaleVariant> BONE = createKey("bone");
    public static final ResourceKey<AncientscaleVariant> STARSHINE = createKey("starshine");

    public static void bootstrap(BootstrapContext<AncientscaleVariant> context)
    {
        register(context, ALMOND, "almond", 0);
        register(context, SAPPHIRE, "sapphire", 1);
        register(context, SMOKE, "smoke", 2);
        register(context, BONE, "bone", 3, AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), MatchStructureCondition.structures(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN).and(RandomChanceCondition.chance(10))).build());
        register(context, STARSHINE, "starshine", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater(), MoonBrightnessCondition.moonBrightness(0f, 0.25f)).build());
    }

    public static void bootstrapSimple(BootstrapContext<AncientscaleVariant> context)
    {
        register(context, ALMOND, "almond", 0);
        register(context, SAPPHIRE, "sapphire", 1);
        register(context, SMOKE, "smoke", 2);
        register(context, BONE, "bone", 3, ProbabilityCondition.defaultRareProbablity().build());
        register(context, STARSHINE, "starshine", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyInWaterCondition.seeSkyInWater()).build());
    }

    static void register(BootstrapContext<AncientscaleVariant> context, ResourceKey<AncientscaleVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<AncientscaleVariant> context, ResourceKey<AncientscaleVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/ancientscale/" + name);
        var glowTexture = FishOfThieves.id("entity/ancientscale/" + name + "_glow");
        context.register(key, new AncientscaleVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.ANCIENTSCALE), customModelData));
    }

    private static ResourceKey<AncientscaleVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ANCIENTSCALE_VARIANT, FishOfThieves.id(name));
    }
}