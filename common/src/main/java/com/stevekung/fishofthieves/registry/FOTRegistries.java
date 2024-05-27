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
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("splashtail_variant"));
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("pondie_variant"));
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("islehopper_variant"));
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("ancientscale_variant"));
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("plentifin_variant"));
    public static final ResourceKey<Registry<WildsplashVariant>> WILDSPLASH_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("wildsplash_variant"));
    public static final ResourceKey<Registry<DevilfishVariant>> DEVILFISH_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("devilfish_variant"));
    public static final ResourceKey<Registry<BattlegillVariant>> BATTLEGILL_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("battlegill_variant"));
    public static final ResourceKey<Registry<WreckerVariant>> WRECKER_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("wrecker_variant"));
    public static final ResourceKey<Registry<StormfishVariant>> STORMFISH_VARIANT = ResourceKey.createRegistryKey(FishOfThieves.res("stormfish_variant"));

    public static final ResourceKey<Registry<FishPlaqueInteraction>> FISH_PLAQUE_INTERACTION = ResourceKey.createRegistryKey(FishOfThieves.res("fish_plaque_interaction"));

    public static final ResourceKey<Registry<SpawnCondition>> SPAWN_CONDITION = ResourceKey.createRegistryKey(FishOfThieves.res("spawn_condition"));
    public static final ResourceKey<Registry<SpawnConditionType>> SPAWN_CONDITION_TYPE = ResourceKey.createRegistryKey(FishOfThieves.res("spawn_condition_type"));
}