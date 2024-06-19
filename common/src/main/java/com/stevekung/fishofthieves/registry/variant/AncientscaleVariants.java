package com.stevekung.fishofthieves.registry.variant;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class AncientscaleVariants
{
    public static final ResourceKey<AncientscaleVariant> ALMOND = createKey("almond");
    public static final ResourceKey<AncientscaleVariant> SAPPHIRE = createKey("sapphire");
    public static final ResourceKey<AncientscaleVariant> SMOKE = createKey("smoke");
    public static final ResourceKey<AncientscaleVariant> BONE = createKey("bone");
    public static final ResourceKey<AncientscaleVariant> STARSHINE = createKey("starshine");

    public static void bootstrap(BootstrapContext<AncientscaleVariant> context)
    {
        var structureLookup = context.lookup(Registries.STRUCTURE);
        var registerContext = AbstractFishVariant.RegisterContext.create("ancientscale", FOTItems.ANCIENTSCALE, AncientscaleVariant::new);
        registerContext.register(context, ALMOND, "almond", 0);
        registerContext.register(context, SAPPHIRE, "sapphire", 1);
        registerContext.register(context, SMOKE, "smoke", 2);
        registerContext.register(context, BONE, "bone", 3, AnyOfCondition.anyOf(ProbabilityCondition.defaultRareProbablity(), RandomChanceCondition.chance(10).and(MatchStructureCondition.structures(HolderSet.direct(structureLookup.getOrThrow(BuiltinStructures.STRONGHOLD))).or(MatchStructureCondition.structures(structureLookup.getOrThrow(StructureTags.MINESHAFT))))).build());
        registerContext.register(context, STARSHINE, "starshine", 4, true, List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater(), MoonBrightnessCondition.moonBrightness(0f, 0.25f)).build()), List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky(), MoonBrightnessCondition.moonBrightness(0f, 0.25f)).build()));
    }

    public static void bootstrapSimple(BootstrapContext<AncientscaleVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("ancientscale", FOTItems.ANCIENTSCALE, AncientscaleVariant::new);
        registerContext.register(context, ALMOND, "almond", 0);
        registerContext.register(context, SAPPHIRE, "sapphire", 1);
        registerContext.register(context, SMOKE, "smoke", 2);
        registerContext.register(context, BONE, "bone", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, STARSHINE, "starshine", 4, true, List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSkyBelowWater()).build()), List.of(AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build()));
    }

    private static ResourceKey<AncientscaleVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ANCIENTSCALE_VARIANT, FishOfThieves.id(name));
    }
}