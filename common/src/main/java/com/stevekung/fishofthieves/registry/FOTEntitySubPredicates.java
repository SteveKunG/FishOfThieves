package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.loot.predicate.TreasuredFishPredicate;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;

import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTEntitySubPredicates
{
    public static final Codec<TrophyFishPredicate> TROPHY = register("trophy", TrophyFishPredicate.CODEC);
    public static final Codec<TreasuredFishPredicate> TREASURED = register("treasured", TreasuredFishPredicate.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Entity Sub Predicate");
    }

    private static <T extends EntitySubPredicate> Codec<T> register(String key, Codec<T> codec)
    {
        return Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, FishOfThieves.id(key), codec);
    }
}