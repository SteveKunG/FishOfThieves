package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.predicates.FOTLocationCheck;
import com.stevekung.fishofthieves.predicates.FOTLocationPredicate;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FOTLootItemConditions
{
    public static final LootItemConditionType FOT_LOCATION_CHECK = register("fot_location_check", new LocationCheck.Serializer());

    public static final LootItemCondition.Builder IN_OCEAN = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.OCEAN));
    public static final LootItemCondition.Builder IN_RIVER = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.RIVER));
    public static final LootItemCondition.Builder IN_FOREST = FOTLocationCheck.checkLocation(FOTLocationPredicate.Builder.location().setBiomeCategory(Biome.BiomeCategory.FOREST));

    private static LootItemConditionType register(String registryName, Serializer<? extends LootItemCondition> serializer)
    {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, registryName), new LootItemConditionType(serializer));
    }
}