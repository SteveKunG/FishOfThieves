package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.AllConditionCheck;
import com.stevekung.fishofthieves.entity.condition.NightCheck;
import com.stevekung.fishofthieves.entity.condition.ProbabilityCheck;
import com.stevekung.fishofthieves.entity.condition.SeeSkyCheck;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.MoonBrightnessCheck;

public class AncientscaleVariants
{
    public static final ResourceKey<AncientscaleVariant> ALMOND = createKey("almond");
    public static final ResourceKey<AncientscaleVariant> SAPPHIRE = createKey("sapphire");
    public static final ResourceKey<AncientscaleVariant> SMOKE = createKey("smoke");
    public static final ResourceKey<AncientscaleVariant> BONE = createKey("bone");
    public static final ResourceKey<AncientscaleVariant> STARSHINE = createKey("starshine");
    public static final ResourceKey<AncientscaleVariant> BLOSSOM = createKey("blossom");

    public static void bootstrap(BootstrapContext<AncientscaleVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("ancientscale", AncientscaleVariant::new);

        registerContext.register(context, ALMOND, "almond", 0);
        registerContext.register(context, SAPPHIRE, "sapphire", 1);
        registerContext.register(context, SMOKE, "smoke", 2);
        registerContext.register(context, BONE, "bone", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, STARSHINE, "starshine", 4, true,
                AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky(), new MoonBrightnessCheck(MinMaxBounds.Doubles.atMost(0.25d))));
        registerContext.register(context, BLOSSOM, "blossom", 5, true, true);
    }

    public static void bootstrapSimple(BootstrapContext<AncientscaleVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("ancientscale", AncientscaleVariant::new);
        registerContext.register(context, ALMOND, "almond", 0);
        registerContext.register(context, SAPPHIRE, "sapphire", 1);
        registerContext.register(context, SMOKE, "smoke", 2);
        registerContext.register(context, BONE, "bone", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, STARSHINE, "starshine", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
        registerContext.register(context, BLOSSOM, "blossom", 5, true, true);
    }

    private static ResourceKey<AncientscaleVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ANCIENTSCALE_VARIANT, FishOfThieves.id(name));
    }
}