package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;

public class FOTLootItemConditions
{
    public static final LootItemConditionType FOT_LOCATION_CHECK = new LootItemConditionType(FOTLocationCheck.CODEC);
    public static final LootItemConditionType FISH_VARIANT_LOOT_CONFIG = new LootItemConditionType(FishVariantLootConfigCondition.CODEC);

    public static final LootItemCondition.Builder IN_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.LUKEWARM_OCEAN));
    public static final LootItemCondition.Builder IN_DEEP_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.DEEP_LUKEWARM_OCEAN));
    public static final LootItemCondition.Builder IN_WARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.WARM_OCEAN));
    public static final LootItemCondition.Builder IN_LUSH_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.LUSH_CAVES));
    public static final LootItemCondition.Builder IN_DRIPSTONE_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.DRIPSTONE_CAVES));

    public static final LootItemCondition.Builder IN_OCEAN_MONUMENTS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.OCEAN_MONUMENT));
    public static final LootItemCondition.Builder IN_PILLAGER_OUTPOSTS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.PILLAGER_OUTPOST));
    public static final LootItemCondition.Builder IN_SHIPWRECKS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.SHIPWRECK));
    public static final LootItemCondition.Builder IN_RUINED_PORTAL_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.RUINED_PORTAL_OCEAN));

    public static final LootItemCondition.Builder THUNDERING = WeatherCheck.weather().setThundering(true);

    public static final LootItemCondition.Builder IN_OCEAN = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_OCEAN));
    public static final LootItemCondition.Builder IN_RIVER = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_RIVER));
    public static final LootItemCondition.Builder IN_FOREST = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_FOREST));
    public static final LootItemCondition.Builder IN_JUNGLE = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiome(BiomeTags.IS_JUNGLE));

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