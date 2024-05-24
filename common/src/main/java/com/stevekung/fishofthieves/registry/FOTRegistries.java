package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.entity.variant.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FOTRegistries
{
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("pondie_variant"));
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("islehopper_variant"));
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("ancientscale_variant"));
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("plentifin_variant"));
    public static final ResourceKey<Registry<WildsplashVariant>> WILDSPLASH_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("wildsplash_variant"));
    public static final ResourceKey<Registry<DevilfishVariant>> DEVILFISH_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("devilfish_variant"));
    public static final ResourceKey<Registry<BattlegillVariant>> BATTLEGILL_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("battlegill_variant"));
    public static final ResourceKey<Registry<WreckerVariant>> WRECKER_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("wrecker_variant"));
    public static final ResourceKey<Registry<StormfishVariant>> STORMFISH_VARIANT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("stormfish_variant"));
}