package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntBooleanStateProvider;
import com.stevekung.fishofthieves.feature.stateproviders.RandomizedIntBooleanStateProvider;

import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class FOTBlockStateProviderTypes
{
    public static final BlockStateProviderType<RandomizedIntBooleanStateProvider> RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER = new BlockStateProviderType<>(RandomizedIntBooleanStateProvider.CODEC);
    public static final BlockStateProviderType<DirectionalRandomizedIntBooleanStateProvider> DIRECTIONAL_RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER = new BlockStateProviderType<>(DirectionalRandomizedIntBooleanStateProvider.CODEC);

    public static void init()
    {
        register("randomized_int_boolean_state_provider", RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER);
        register("directional_randomized_int_boolean_state_provider", DIRECTIONAL_RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER);
    }

    private static <P extends BlockStateProvider> void register(String key, BlockStateProviderType<P> type)
    {
        FOTPlatform.registerBlockStateProviderType(key, type);
    }
}