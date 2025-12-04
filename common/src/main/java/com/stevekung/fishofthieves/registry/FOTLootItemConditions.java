package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.condition.BaitAttachedCondition;
import com.stevekung.fishofthieves.loot.condition.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.condition.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.PeakTypes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;

public class FOTLootItemConditions
{
    public static final LootItemConditionType LOCATION_CHECK = register("location_check", new LootItemConditionType(FOTLocationCheck.CODEC));
    public static final LootItemConditionType FISH_VARIANT_LOOT_CONFIG = register("fish_variant_loot_config", new LootItemConditionType(FishVariantLootConfigCondition.CODEC));
    public static final LootItemConditionType BAIT_ATTACHED_HOOK = register("bait_attached_hook", new LootItemConditionType(BaitAttachedCondition.CODEC));

    public static final LootItemCondition.Builder THUNDERING = WeatherCheck.weather().setThundering(true);
    public static final LootItemCondition.Builder COAST_CONTINENTALNESS = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST));
    public static final LootItemCondition.Builder LOW_PEAKTYPE = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setPeakType(PeakTypes.LOW));
    public static final LootItemCondition.Builder MID_PEAKTYPE = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setPeakType(PeakTypes.MID));
    public static final LootItemCondition.Builder VALLEY_PEAKTYPE = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setPeakType(PeakTypes.VALLEY));
    public static final LootItemCondition.Builder HAS_RAIDS = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().hasRaids());

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Loot Item Condition");
    }

    private static LootItemConditionType register(String key, LootItemConditionType type)
    {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, FishOfThieves.id(key), type);
    }
}