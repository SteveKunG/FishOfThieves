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
    public static final ResourceKey<DevilfishVariant> LEOPARD = createKey("leopard");

    public static void bootstrap(BootstrapContext<DevilfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("devilfish", DevilfishVariant::new);
        registerContext.register(context, ASHEN, "ashen", 0);
        registerContext.register(context, SEASHELL, "seashell", 1);
        registerContext.register(context, LAVA, "lava", 2, BlockRangeCheck.blocksInRange(Optional.empty(), Optional.of(context.lookup(Registries.FLUID).getOrThrow(FluidTags.LAVA)), 4));
        registerContext.register(context, FORSAKEN, "forsaken", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, FIRELIGHT, "firelight", 4, true, AllConditionCheck.allOf(NightCheck.night(),
                BlockRangeCheck.blocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS)), Optional.of(context.lookup(Registries.FLUID).getOrThrow(FluidTags.LAVA)), 4)));
        registerContext.register(context, LEOPARD, "leopard", 5, true, true);
    }

    public static void bootstrapSimple(BootstrapContext<DevilfishVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("devilfish", DevilfishVariant::new);
        registerContext.register(context, ASHEN, "ashen", 0);
        registerContext.register(context, SEASHELL, "seashell", 1);
        registerContext.register(context, LAVA, "lava", 2);
        registerContext.register(context, FORSAKEN, "forsaken", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, FIRELIGHT, "firelight", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
        registerContext.register(context, LEOPARD, "leopard", 5, true, true);
    }

    private static ResourceKey<DevilfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.DEVILFISH_VARIANT, FishOfThieves.id(name));
    }
}