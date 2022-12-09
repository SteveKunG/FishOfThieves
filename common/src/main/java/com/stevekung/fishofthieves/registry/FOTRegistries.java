package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class FOTRegistries
{
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT_REGISTRY = Registries.createRegistryKey("splashtail_variant");
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT_REGISTRY = Registries.createRegistryKey("pondie_variant");
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT_REGISTRY = Registries.createRegistryKey("islehopper_variant");
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT_REGISTRY = Registries.createRegistryKey("ancientscale_variant");
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT_REGISTRY = Registries.createRegistryKey("plentifin_variant");
    public static final ResourceKey<Registry<WildsplashVariant>> WILDSPLASH_VARIANT_REGISTRY = Registries.createRegistryKey("wildsplash_variant");
    public static final ResourceKey<Registry<DevilfishVariant>> DEVILFISH_VARIANT_REGISTRY = Registries.createRegistryKey("devilfish_variant");
    public static final ResourceKey<Registry<BattlegillVariant>> BATTLEGILL_VARIANT_REGISTRY = Registries.createRegistryKey("battlegill_variant");
    public static final ResourceKey<Registry<WreckerVariant>> WRECKER_VARIANT_REGISTRY = Registries.createRegistryKey("wrecker_variant");
    public static final ResourceKey<Registry<StormfishVariant>> STORMFISH_VARIANT_REGISTRY = Registries.createRegistryKey("stormfish_variant");
}