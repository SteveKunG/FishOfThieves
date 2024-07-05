package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionType;
import com.stevekung.fishofthieves.entity.variant.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FOTRegistries
{
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT = create("splashtail_variant");
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT = create("pondie_variant");
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT = create("islehopper_variant");
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT = create("ancientscale_variant");
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT = create("plentifin_variant");
    public static final ResourceKey<Registry<WildsplashVariant>> WILDSPLASH_VARIANT = create("wildsplash_variant");
    public static final ResourceKey<Registry<DevilfishVariant>> DEVILFISH_VARIANT = create("devilfish_variant");
    public static final ResourceKey<Registry<BattlegillVariant>> BATTLEGILL_VARIANT = create("battlegill_variant");
    public static final ResourceKey<Registry<WreckerVariant>> WRECKER_VARIANT = create("wrecker_variant");
    public static final ResourceKey<Registry<StormfishVariant>> STORMFISH_VARIANT = create("stormfish_variant");

    public static final ResourceKey<Registry<FishPlaqueInteraction>> FISH_PLAQUE_INTERACTION = create("fish_plaque_interaction");

    public static final ResourceKey<Registry<SpawnCondition>> SPAWN_CONDITION = create("spawn_condition");
    public static final ResourceKey<Registry<SpawnConditionType>> SPAWN_CONDITION_TYPE = create("spawn_condition_type");

    private static <T> ResourceKey<Registry<T>> create(String key)
    {
        return ResourceKey.createRegistryKey(new ResourceLocation(key));
    }
}