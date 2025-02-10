package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;

import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTEntitySubPredicate
{
    public static final MapCodec<TrophyFishPredicate> TROPHY = register("trophy", TrophyFishPredicate.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Entity Sub Predicate");
    }

    private static <T extends EntitySubPredicate> MapCodec<T> register(String key, MapCodec<T> codec)
    {
        return Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, FishOfThieves.id(key), codec);
    }
}