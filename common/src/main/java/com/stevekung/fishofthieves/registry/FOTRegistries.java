package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionType;
import com.stevekung.fishofthieves.entity.variant.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FOTRegistries
{
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("splashtail_variant"));
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("pondie_variant"));
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("islehopper_variant"));
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("ancientscale_variant"));
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("plentifin_variant"));
    public static final ResourceKey<Registry<WildsplashVariant>> WILDSPLASH_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("wildsplash_variant"));
    public static final ResourceKey<Registry<DevilfishVariant>> DEVILFISH_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("devilfish_variant"));
    public static final ResourceKey<Registry<BattlegillVariant>> BATTLEGILL_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("battlegill_variant"));
    public static final ResourceKey<Registry<WreckerVariant>> WRECKER_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("wrecker_variant"));
    public static final ResourceKey<Registry<StormfishVariant>> STORMFISH_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.id("stormfish_variant"));

    public static final ResourceKey<Registry<FishPlaqueInteraction>> FISH_PLAQUE_INTERACTION = ResourceKey.createRegistryKey(FishOfThieves.id("fish_plaque_interaction"));

    public static final ResourceKey<Registry<SpawnCondition>> SPAWN_CONDITION = ResourceKey.createRegistryKey(FishOfThieves.id("spawn_condition"));
    public static final ResourceKey<Registry<SpawnConditionType>> SPAWN_CONDITION_TYPE = ResourceKey.createRegistryKey(FishOfThieves.id("spawn_condition_type"));
}