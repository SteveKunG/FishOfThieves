package com.stevekung.fishofthieves.registry.variant;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;

public class DevilfishVariants
{
    public static final ResourceKey<DevilfishVariant> ASHEN = createKey("ashen");
    public static final ResourceKey<DevilfishVariant> SEASHELL = createKey("seashell");
    public static final ResourceKey<DevilfishVariant> LAVA = createKey("lava");
    public static final ResourceKey<DevilfishVariant> FORSAKEN = createKey("forsaken");
    public static final ResourceKey<DevilfishVariant> FIRELIGHT = createKey("firelight");

    public static void bootstrap(BootstrapContext<DevilfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("devilfish", DevilfishVariant::new);
        registerContext.register(context, ASHEN, "ashen");
        registerContext.register(context, SEASHELL, "seashell");
        registerContext.register(context, LAVA, "lava", MatchBlocksInRangeCondition.blocksInRange(Optional.empty(), Optional.of(context.lookup(Registries.FLUID).getOrThrow(FluidTags.LAVA)), 4).build());
        registerContext.register(context, FORSAKEN, "forsaken", ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, FIRELIGHT, "firelight", true, AllOfCondition.allOf(NightCondition.night(), MatchBlocksInRangeCondition.blocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS)), Optional.of(context.lookup(Registries.FLUID).getOrThrow(FluidTags.LAVA)), 4)).build());
    }

    public static void bootstrapSimple(BootstrapContext<DevilfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("devilfish", DevilfishVariant::new);
        registerContext.register(context, ASHEN, "ashen");
        registerContext.register(context, SEASHELL, "seashell");
        registerContext.register(context, LAVA, "lava");
        registerContext.register(context, FORSAKEN, "forsaken", ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, FIRELIGHT, "firelight", true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    private static ResourceKey<DevilfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.DEVILFISH_VARIANT, FishOfThieves.id(name));
    }
}