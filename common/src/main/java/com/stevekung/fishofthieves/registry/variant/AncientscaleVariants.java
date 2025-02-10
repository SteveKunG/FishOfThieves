package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.MoonBrightnessCheck;
import net.minecraft.world.entity.variant.StructureCheck;

public class AncientscaleVariants
{
    public static final ResourceKey<AncientscaleVariant> ALMOND = createKey("almond");
    public static final ResourceKey<AncientscaleVariant> SAPPHIRE = createKey("sapphire");
    public static final ResourceKey<AncientscaleVariant> SMOKE = createKey("smoke");
    public static final ResourceKey<AncientscaleVariant> BONE = createKey("bone");
    public static final ResourceKey<AncientscaleVariant> STARSHINE = createKey("starshine");

    public static void bootstrap(BootstrapContext<AncientscaleVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("ancientscale", AncientscaleVariant::new);
        var structureLookup = context.lookup(Registries.STRUCTURE);

        registerContext.register(context, ALMOND, "almond", 0);
        registerContext.register(context, SAPPHIRE, "sapphire", 1);
        registerContext.register(context, SMOKE, "smoke", 2);
        registerContext.register(context, BONE, "bone", 3,
                registerContext.select(AllOfCondition.allOf(new StructureCheck(structureLookup.getOrThrow(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN)), RandomChanceCondition.chance(10)), 1),
                registerContext.select(ProbabilityCondition.defaultRareProbablity(), 0));
        registerContext.register(context, STARSHINE, "starshine", 4, true,
                AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky(), new MoonBrightnessCheck(MinMaxBounds.Doubles.atMost(0.25d))));
    }

    public static void bootstrapSimple(BootstrapContext<AncientscaleVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("ancientscale", AncientscaleVariant::new);
        registerContext.register(context, ALMOND, "almond", 0);
        registerContext.register(context, SAPPHIRE, "sapphire", 1);
        registerContext.register(context, SMOKE, "smoke", 2);
        registerContext.register(context, BONE, "bone", 3, ProbabilityCondition.defaultRareProbablity());
        registerContext.register(context, STARSHINE, "starshine", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()));
    }

    private static ResourceKey<AncientscaleVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ANCIENTSCALE_VARIANT, FishOfThieves.id(name));
    }
}