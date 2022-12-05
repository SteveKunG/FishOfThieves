package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class FOTRegistry
{
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT_REGISTRY = Registries.createRegistryKey("splashtail_variant");
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = BuiltInRegistries.registerSimple(SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariant.RUBY);
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT_REGISTRY = Registries.createRegistryKey("pondie_variant");
    public static final Registry<PondieVariant> PONDIE_VARIANT = BuiltInRegistries.registerSimple(PONDIE_VARIANT_REGISTRY, registry -> PondieVariant.CHARCOAL);
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT_REGISTRY = Registries.createRegistryKey("islehopper_variant");
    public static final Registry<IslehopperVariant> ISLEHOPPER_VARIANT = BuiltInRegistries.registerSimple(ISLEHOPPER_VARIANT_REGISTRY, registry -> IslehopperVariant.STONE);
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT_REGISTRY = Registries.createRegistryKey("ancientscale_variant");
    public static final Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT = BuiltInRegistries.registerSimple(ANCIENTSCALE_VARIANT_REGISTRY, registry -> AncientscaleVariant.ALMOND);
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT_REGISTRY = Registries.createRegistryKey("plentifin_variant");
    public static final Registry<PlentifinVariant> PLENTIFIN_VARIANT = BuiltInRegistries.registerSimple(PLENTIFIN_VARIANT_REGISTRY, registry -> PlentifinVariant.OLIVE);
    public static final ResourceKey<Registry<WildsplashVariant>> WILDSPLASH_VARIANT_REGISTRY = Registries.createRegistryKey("wildsplash_variant");
    public static final Registry<WildsplashVariant> WILDSPLASH_VARIANT = BuiltInRegistries.registerSimple(WILDSPLASH_VARIANT_REGISTRY, registry -> WildsplashVariant.RUSSET);
    public static final ResourceKey<Registry<DevilfishVariant>> DEVILFISH_VARIANT_REGISTRY = Registries.createRegistryKey("devilfish_variant");
    public static final Registry<DevilfishVariant> DEVILFISH_VARIANT = BuiltInRegistries.registerSimple(DEVILFISH_VARIANT_REGISTRY, registry -> DevilfishVariant.ASHEN);
    public static final ResourceKey<Registry<BattlegillVariant>> BATTLEGILL_VARIANT_REGISTRY = Registries.createRegistryKey("battlegill_variant");
    public static final Registry<BattlegillVariant> BATTLEGILL_VARIANT = BuiltInRegistries.registerSimple(BATTLEGILL_VARIANT_REGISTRY, registry -> BattlegillVariant.JADE);
    public static final ResourceKey<Registry<WreckerVariant>> WRECKER_VARIANT_REGISTRY = Registries.createRegistryKey("wrecker_variant");
    public static final Registry<WreckerVariant> WRECKER_VARIANT = BuiltInRegistries.registerSimple(WRECKER_VARIANT_REGISTRY, registry -> WreckerVariant.ROSE);
    public static final ResourceKey<Registry<StormfishVariant>> STORMFISH_VARIANT_REGISTRY = Registries.createRegistryKey("stormfish_variant");
    public static final Registry<StormfishVariant> STORMFISH_VARIANT = BuiltInRegistries.registerSimple(STORMFISH_VARIANT_REGISTRY, registry -> StormfishVariant.ANCIENT);
}