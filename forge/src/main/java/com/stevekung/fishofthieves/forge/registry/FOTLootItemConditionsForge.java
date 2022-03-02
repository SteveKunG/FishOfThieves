package com.stevekung.fishofthieves.forge.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.predicates.FOTLocationCheckForge;
import com.stevekung.fishofthieves.predicates.FOTLocationPredicate;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class FOTLootItemConditionsForge
{
    public static final LootItemConditionType FOT_LOCATION_CHECK = new LootItemConditionType(new FOTLocationCheckForge.Serializer());

    public static final LootItemCondition.Builder IN_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.LUKEWARM_OCEAN));
    public static final LootItemCondition.Builder IN_DEEP_LUKEWARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.DEEP_LUKEWARM_OCEAN));
    public static final LootItemCondition.Builder IN_WARM_OCEAN = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.WARM_OCEAN));
    public static final LootItemCondition.Builder IN_LUSH_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.LUSH_CAVES));
    public static final LootItemCondition.Builder IN_DRIPSTONE_CAVES = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.DRIPSTONE_CAVES));

    public static final LootItemCondition.Builder IN_OCEAN_MONUMENTS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setFeature(BuiltinStructures.OCEAN_MONUMENT));
    public static final LootItemCondition.Builder IN_PILLAGER_OUTPOSTS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setFeature(BuiltinStructures.PILLAGER_OUTPOST));
    public static final LootItemCondition.Builder IN_SHIPWRECKS = LocationCheck.checkLocation(LocationPredicate.Builder.location().setFeature(BuiltinStructures.SHIPWRECK));

    public static final LootItemCondition.Builder THUNDERING = WeatherCheck.weather().setThundering(true);

    public static final LootItemCondition.Builder IN_OCEAN = FOTLocationCheckForge.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.OCEAN));
    public static final LootItemCondition.Builder IN_RIVER = FOTLocationCheckForge.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.RIVER));
    public static final LootItemCondition.Builder IN_FOREST = FOTLocationCheckForge.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.FOREST));
    public static final LootItemCondition.Builder IN_JUNGLE = FOTLocationCheckForge.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.JUNGLE));

    public static final LootItemCondition.Builder COAST = FOTLocationCheckForge.checkLocation(FOTLocationPredicate.Builder.location().setContinentalness(Continentalness.COAST));

    public static final LootItemCondition.Builder RAID_ACTIVE = FOTLocationCheckForge.checkLocation(FOTLocationPredicate.Builder.location().hasRaids(true));

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event)
    {
        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, "fot_location_check"), FOT_LOCATION_CHECK);
    }
}