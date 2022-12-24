package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.registry.variant.*;
import net.minecraft.core.Registry;

public class FOTRegistry
{
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = Registry.registerSimple(FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariants.RUBY);
    public static final Registry<PondieVariant> PONDIE_VARIANT = Registry.registerSimple(FOTRegistries.PONDIE_VARIANT_REGISTRY, registry -> PondieVariants.CHARCOAL);
    public static final Registry<IslehopperVariant> ISLEHOPPER_VARIANT = Registry.registerSimple(FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, registry -> IslehopperVariants.STONE);
    public static final Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT = Registry.registerSimple(FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, registry -> AncientscaleVariants.ALMOND);
    public static final Registry<PlentifinVariant> PLENTIFIN_VARIANT = Registry.registerSimple(FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, registry -> PlentifinVariants.OLIVE);
    public static final Registry<WildsplashVariant> WILDSPLASH_VARIANT = Registry.registerSimple(FOTRegistries.WILDSPLASH_VARIANT_REGISTRY, registry -> WildsplashVariants.RUSSET);
    public static final Registry<DevilfishVariant> DEVILFISH_VARIANT = Registry.registerSimple(FOTRegistries.DEVILFISH_VARIANT_REGISTRY, registry -> DevilfishVariants.ASHEN);
    public static final Registry<BattlegillVariant> BATTLEGILL_VARIANT = Registry.registerSimple(FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, registry -> BattlegillVariants.JADE);
    public static final Registry<WreckerVariant> WRECKER_VARIANT = Registry.registerSimple(FOTRegistries.WRECKER_VARIANT_REGISTRY, registry -> WreckerVariants.ROSE);
    public static final Registry<StormfishVariant> STORMFISH_VARIANT = Registry.registerSimple(FOTRegistries.STORMFISH_VARIANT_REGISTRY, registry -> StormfishVariants.ANCIENT);
}