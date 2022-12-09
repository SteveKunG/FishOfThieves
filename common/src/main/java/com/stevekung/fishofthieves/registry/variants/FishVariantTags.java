package com.stevekung.fishofthieves.registry.variants;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class FishVariantTags
{
    public static final TagKey<SplashtailVariant> DEFAULT_SPLASHTAIL_SPAWNS = TagKey.create(FOTRegistries.SPLASHTAIL_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_splashtail_spawns"));
    public static final TagKey<PondieVariant> DEFAULT_PONDIE_SPAWNS = TagKey.create(FOTRegistries.PONDIE_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_pondie_spawns"));
    public static final TagKey<IslehopperVariant> DEFAULT_ISLEHOPPER_SPAWNS = TagKey.create(FOTRegistries.ISLEHOPPER_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_islehopper_spawns"));
    public static final TagKey<AncientscaleVariant> DEFAULT_ANCIENTSCALE_SPAWNS = TagKey.create(FOTRegistries.ANCIENTSCALE_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_ancientscale_spawns"));
    public static final TagKey<PlentifinVariant> DEFAULT_PLENTIFIN_SPAWNS = TagKey.create(FOTRegistries.PLENTIFIN_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_plentifin_spawns"));
    public static final TagKey<WildsplashVariant> DEFAULT_WILDSPLASH_SPAWNS = TagKey.create(FOTRegistries.WILDSPLASH_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_wildsplash_spawns"));
    public static final TagKey<DevilfishVariant> DEFAULT_DEVILFISH_SPAWNS = TagKey.create(FOTRegistries.DEVILFISH_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_devilfish_spawns"));
    public static final TagKey<BattlegillVariant> DEFAULT_BATTLEGILL_SPAWNS = TagKey.create(FOTRegistries.BATTLEGILL_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_battlegill_spawns"));
    public static final TagKey<WreckerVariant> DEFAULT_WRECKER_SPAWNS = TagKey.create(FOTRegistries.WRECKER_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_wrecker_spawns"));
    public static final TagKey<StormfishVariant> DEFAULT_STORMFISH_SPAWNS = TagKey.create(FOTRegistries.STORMFISH_VARIANT_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "default_stormfish_spawns"));
}