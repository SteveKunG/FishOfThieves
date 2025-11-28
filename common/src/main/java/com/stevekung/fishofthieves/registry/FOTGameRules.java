package com.stevekung.fishofthieves.registry;

import java.util.function.ToIntFunction;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;

public class FOTGameRules
{
    public static final GameRule<Boolean> SHOAL_SPAWNING = registerBoolean("shoal_spawning", GameRuleCategory.SPAWNING, true);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Game Rules");
    }

    private static GameRule<Boolean> registerBoolean(String string, GameRuleCategory gameRuleCategory, boolean defaultValue)
    {
        return register(string, gameRuleCategory, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, defaultValue, FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean, boolean_ -> boolean_ ? 1 : 0);
    }

    private static <T> GameRule<T> register(String string, GameRuleCategory gameRuleCategory, GameRuleType gameRuleType, ArgumentType<T> argumentType, Codec<T> codec, T defaultValue, FeatureFlagSet featureFlagSet, GameRules.VisitorCaller<T> visitorCaller, ToIntFunction<T> toIntFunction)
    {
        return Registry.register(BuiltInRegistries.GAME_RULE, FishOfThieves.id(string), new GameRule<>(gameRuleCategory, gameRuleType, argumentType, visitorCaller, codec, toIntFunction, defaultValue, featureFlagSet));
    }
}