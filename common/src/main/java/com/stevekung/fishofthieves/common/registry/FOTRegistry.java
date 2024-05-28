package com.stevekung.fishofthieves.common.registry;

import com.stevekung.fishofthieves.common.entity.variant.*;
import com.stevekung.fishofthieves.common.registry.variant.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTRegistry
{
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariants.RUBY);
    public static final Registry<PondieVariant> PONDIE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PONDIE_VARIANT_REGISTRY, registry -> PondieVariants.CHARCOAL);
    public static final Registry<IslehopperVariant> ISLEHOPPER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, registry -> IslehopperVariants.STONE);
    public static final Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, registry -> AncientscaleVariants.ALMOND);
    public static final Registry<PlentifinVariant> PLENTIFIN_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, registry -> PlentifinVariants.OLIVE);
    public static final Registry<WildsplashVariant> WILDSPLASH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WILDSPLASH_VARIANT_REGISTRY, registry -> WildsplashVariants.RUSSET);
    public static final Registry<DevilfishVariant> DEVILFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.DEVILFISH_VARIANT_REGISTRY, registry -> DevilfishVariants.ASHEN);
    public static final Registry<BattlegillVariant> BATTLEGILL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, registry -> BattlegillVariants.JADE);
    public static final Registry<WreckerVariant> WRECKER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WRECKER_VARIANT_REGISTRY, registry -> WreckerVariants.ROSE);
    public static final Registry<StormfishVariant> STORMFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.STORMFISH_VARIANT_REGISTRY, registry -> StormfishVariants.ANCIENT);

    public static void forge()
    {
        SplashtailVariants.init();
        PondieVariants.init();
        IslehopperVariants.init();
        AncientscaleVariants.init();
        PlentifinVariants.init();
        WildsplashVariants.init();
        DevilfishVariants.init();
        BattlegillVariants.init();
        WreckerVariants.init();
        StormfishVariants.init();
    }
}