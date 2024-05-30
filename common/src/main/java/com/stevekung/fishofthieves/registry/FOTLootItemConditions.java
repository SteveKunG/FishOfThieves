package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;

public class FOTLootItemConditions
{
    public static final LootItemConditionType FOT_LOCATION_CHECK = new LootItemConditionType(FOTLocationCheck.CODEC);
    public static final LootItemConditionType FISH_VARIANT_LOOT_CONFIG = new LootItemConditionType(FishVariantLootConfigCondition.CODEC);

    public static final LootItemCondition.Builder THUNDERING = WeatherCheck.weather().setThundering(true);
    public static final LootItemCondition.Builder COAST = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST));
    public static final LootItemCondition.Builder HAS_RAIDS = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().hasRaids());

    public static void init()
    {
        register("fot_location_check", FOT_LOCATION_CHECK);
        register("fish_variant_loot_config", FISH_VARIANT_LOOT_CONFIG);
    }

    private static void register(String key, LootItemConditionType type)
    {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, FishOfThieves.id(key), type);
    }
}