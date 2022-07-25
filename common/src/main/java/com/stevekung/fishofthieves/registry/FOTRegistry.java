package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
import com.stevekung.fishofthieves.registry.variants.SplashtailVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FOTRegistry
{
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT_REGISTRY = createRegistryKey("splashtail_variant");
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = Registry.registerSimple(SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariant.RUBY);
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT_REGISTRY = createRegistryKey("pondie_variant");
    public static final Registry<PondieVariant> PONDIE_VARIANT = Registry.registerSimple(PONDIE_VARIANT_REGISTRY, registry -> PondieVariant.CHARCOAL);

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String registryName)
    {
        return ResourceKey.createRegistryKey(new ResourceLocation(FishOfThieves.MOD_ID, registryName));
    }
}