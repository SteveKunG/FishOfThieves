package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.function.FOTLocationCheck;
import com.stevekung.fishofthieves.loot.function.FishVariantLootConfigCondition;
import com.stevekung.fishofthieves.loot.predicate.FOTLocationPredicate;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;

public class FOTLootItemConditions
{
    private static final HolderLookup.Provider PROVIDER = VanillaRegistries.createLookup();

    public static final LootItemConditionType FOT_LOCATION_CHECK = new LootItemConditionType(FOTLocationCheck.CODEC);
    public static final LootItemConditionType FISH_VARIANT_LOOT_CONFIG = new LootItemConditionType(FishVariantLootConfigCondition.CODEC);

    public static final LootItemCondition.Builder IN_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(Holder.direct(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.LUKEWARM_OCEAN).value())));
    public static final LootItemCondition.Builder IN_DEEP_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(Holder.direct(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN).value())));
    public static final LootItemCondition.Builder IN_WARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(Holder.direct(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.WARM_OCEAN).value())));
    public static final LootItemCondition.Builder IN_LUSH_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(Holder.direct(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.LUSH_CAVES).value())));
    public static final LootItemCondition.Builder IN_DRIPSTONE_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(Holder.direct(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.DRIPSTONE_CAVES).value())));

    public static final LootItemCondition.Builder IN_OCEAN_MONUMENTS = LocationCheck.checkLocation(LocationPredicate.Builder.inStructure(Holder.direct(PROVIDER.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.OCEAN_MONUMENT).value())));
    public static final LootItemCondition.Builder IN_PILLAGER_OUTPOSTS = LocationCheck.checkLocation(LocationPredicate.Builder.inStructure(Holder.direct(PROVIDER.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.PILLAGER_OUTPOST).value())));
    public static final LootItemCondition.Builder IN_SHIPWRECKS = LocationCheck.checkLocation(LocationPredicate.Builder.inStructure(Holder.direct(PROVIDER.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.SHIPWRECK).value())));
    public static final LootItemCondition.Builder IN_RUINED_PORTAL_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.inStructure(Holder.direct(PROVIDER.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.RUINED_PORTAL_OCEAN).value())));

    public static final LootItemCondition.Builder THUNDERING = WeatherCheck.weather().setThundering(true);

    public static final LootItemCondition.Builder IN_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_OCEAN)));
    public static final LootItemCondition.Builder IN_RIVER = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_RIVER)));
    public static final LootItemCondition.Builder IN_FOREST = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_FOREST)));
    public static final LootItemCondition.Builder IN_JUNGLE = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(PROVIDER.lookupOrThrow(Registries.BIOME).getOrThrow(BiomeTags.IS_JUNGLE)));

    public static final LootItemCondition.Builder COAST = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST));

    public static final LootItemCondition.Builder HAS_RAIDS = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().hasRaids());

    public static void init()
    {
        register("fot_location_check", FOT_LOCATION_CHECK);
        register("fish_variant_loot_config", FISH_VARIANT_LOOT_CONFIG);
    }

    private static void register(String key, LootItemConditionType type)
    {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, FishOfThieves.res(key), type);
    }
}