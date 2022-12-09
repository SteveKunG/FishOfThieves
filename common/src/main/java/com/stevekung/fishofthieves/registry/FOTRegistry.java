package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTRegistry
{
    public static Registry<SplashtailVariant> SPLASHTAIL_VARIANT;
    public static Registry<PondieVariant> PONDIE_VARIANT;
    public static Registry<IslehopperVariant> ISLEHOPPER_VARIANT;
    public static Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT;
    public static Registry<PlentifinVariant> PLENTIFIN_VARIANT;
    public static Registry<WildsplashVariant> WILDSPLASH_VARIANT;
    public static Registry<DevilfishVariant> DEVILFISH_VARIANT;
    public static Registry<BattlegillVariant> BATTLEGILL_VARIANT;
    public static Registry<WreckerVariant> WRECKER_VARIANT;
    public static Registry<StormfishVariant> STORMFISH_VARIANT;

    public static void init()
    {
        SPLASHTAIL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariant.RUBY);
        PONDIE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PONDIE_VARIANT_REGISTRY, registry -> PondieVariant.CHARCOAL);
        ISLEHOPPER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, registry -> IslehopperVariant.STONE);
        ANCIENTSCALE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, registry -> AncientscaleVariant.ALMOND);
        PLENTIFIN_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, registry -> PlentifinVariant.OLIVE);
        WILDSPLASH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WILDSPLASH_VARIANT_REGISTRY, registry -> WildsplashVariant.RUSSET);
        DEVILFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.DEVILFISH_VARIANT_REGISTRY, registry -> DevilfishVariant.ASHEN);
        BATTLEGILL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, registry -> BattlegillVariant.JADE);
        WRECKER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WRECKER_VARIANT_REGISTRY, registry -> WreckerVariant.ROSE);
        STORMFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.STORMFISH_VARIANT_REGISTRY, registry -> StormfishVariant.ANCIENT);
    }
}