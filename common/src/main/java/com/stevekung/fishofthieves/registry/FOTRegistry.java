package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTRegistry
{
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariant.RUBY);
    public static final Registry<PondieVariant> PONDIE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PONDIE_VARIANT_REGISTRY, registry -> PondieVariant.CHARCOAL);
    public static final Registry<IslehopperVariant> ISLEHOPPER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, registry -> IslehopperVariant.STONE);
    public static final Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, registry -> AncientscaleVariant.ALMOND);
    public static final Registry<PlentifinVariant> PLENTIFIN_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, registry -> PlentifinVariant.OLIVE);
    public static final Registry<WildsplashVariant> WILDSPLASH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WILDSPLASH_VARIANT_REGISTRY, registry -> WildsplashVariant.RUSSET);
    public static final Registry<DevilfishVariant> DEVILFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.DEVILFISH_VARIANT_REGISTRY, registry -> DevilfishVariant.ASHEN);
    public static final Registry<BattlegillVariant> BATTLEGILL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, registry -> BattlegillVariant.JADE);
    public static final Registry<WreckerVariant> WRECKER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WRECKER_VARIANT_REGISTRY, registry -> WreckerVariant.ROSE);
    public static final Registry<StormfishVariant> STORMFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.STORMFISH_VARIANT_REGISTRY, registry -> StormfishVariant.ANCIENT);
}