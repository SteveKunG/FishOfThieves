package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntBooleanStateProvider;
import com.stevekung.fishofthieves.feature.stateproviders.RandomizedIntBooleanStateProvider;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FOTBlockStateProviderTypes
{
    public static final MapCodec<RandomizedIntBooleanStateProvider> RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER = register("randomized_int_boolean_state_provider", RandomizedIntBooleanStateProvider.CODEC);
    public static final MapCodec<DirectionalRandomizedIntBooleanStateProvider> DIRECTIONAL_RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER = register("directional_randomized_int_boolean_state_provider", DirectionalRandomizedIntBooleanStateProvider.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Block State Provider Type");
    }

    private static <P extends BlockStateProvider> MapCodec<P> register(String key, MapCodec<P> type)
    {
        return Registry.register(BuiltInRegistries.BLOCK_STATE_PROVIDER_TYPE, FishOfThieves.id(key), type);
    }
}