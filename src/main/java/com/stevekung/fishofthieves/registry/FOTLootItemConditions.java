package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.predicates.FOTLocationCheck;
import com.stevekung.fishofthieves.predicates.FOTLocationPredicate;
import com.stevekung.fishofthieves.utils.Continentalness;

import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FOTLootItemConditions
{
    public static final LootItemConditionType FOT_LOCATION_CHECK = register("fot_location_check", new LocationCheck.Serializer());

    public static final LootItemCondition.Builder IN_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.LUKEWARM_OCEAN));
    public static final LootItemCondition.Builder IN_DEEP_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.DEEP_LUKEWARM_OCEAN));
    public static final LootItemCondition.Builder IN_WARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.WARM_OCEAN));
    public static final LootItemCondition.Builder IN_LUSH_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.LUSH_CAVES));
    public static final LootItemCondition.Builder IN_DRIPSTONE_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.DRIPSTONE_CAVES));

    public static final LootItemCondition.Builder IN_OCEAN_MONUMENTS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setFeature(StructureFeature.OCEAN_MONUMENT));
    public static final LootItemCondition.Builder IN_PILLAGER_OUTPOSTS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setFeature(StructureFeature.PILLAGER_OUTPOST));
    public static final LootItemCondition.Builder IN_SHIPWRECKS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setFeature(StructureFeature.SHIPWRECK));

    public static final LootItemCondition.Builder IN_OCEAN = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.OCEAN));
    public static final LootItemCondition.Builder IN_RIVER = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.RIVER));
    public static final LootItemCondition.Builder IN_FOREST = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.FOREST));
    public static final LootItemCondition.Builder IN_JUNGLE = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.JUNGLE));

    public static final LootItemCondition.Builder COAST = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST));

    public static final LootItemCondition.Builder RAID_ACTIVE = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().isRaidActive());

    private static LootItemConditionType register(String registryName, Serializer<? extends LootItemCondition> serializer)
    {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, registryName), new LootItemConditionType(serializer));
    }
}