package com.stevekung.fishofthieves.registry.variants;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class FishVariantTags
{
    public static final TagKey<SplashtailVariant> DEFAULT_SPLASHTAIL_SPAWNS = TagKey.create(FOTRegistry.SPLASHTAIL_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_splashtail_spawns"));
}