package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.HolderSet;
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
        var registerContext = AbstractFishVariant.RegisterContext.create("battlegill", BattlegillVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, JADE, "jade", 0);
        registerContext.register(context, SKY, "sky", 1, SeeSkyCondition.seeSky().build());
        registerContext.register(context, RUM, "rum", 2);
        registerContext.register(context, SAND, "sand", 3, AllOfCondition.allOf(ProbabilityCondition.defaultRareProbablity(), MatchBiomeCondition.biomes(HolderSet.direct(biomeLookup.getOrThrow(Biomes.DESERT), biomeLookup.getOrThrow(Biomes.WARM_OCEAN), biomeLookup.getOrThrow(Biomes.LUKEWARM_OCEAN), biomeLookup.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN)))).build());
        registerContext.register(context, BITTERSWEET, "bittersweet", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    public static void bootstrapSimple(BootstrapContext<BattlegillVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("battlegill", BattlegillVariant::new);
        registerContext.register(context, JADE, "jade", 0);
        registerContext.register(context, SKY, "sky", 1);
        registerContext.register(context, RUM, "rum", 2);
        registerContext.register(context, SAND, "sand", 3, ProbabilityCondition.defaultRareProbablity().build());
        registerContext.register(context, BITTERSWEET, "bittersweet", 4, true, AllOfCondition.allOf(NightCondition.night(), SeeSkyCondition.seeSky()).build());
    }

    private static ResourceKey<BattlegillVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.BATTLEGILL_VARIANT, FishOfThieves.id(name));
    }
}