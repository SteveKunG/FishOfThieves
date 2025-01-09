package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntStateProvider;

import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class FOTBlockStateProviderTypes
{
    public static final BlockStateProviderType<DirectionalRandomizedIntStateProvider> DIRECTIONAL_RANDOMIZED_INT_STATE_PROVIDER = new BlockStateProviderType<>(DirectionalRandomizedIntStateProvider.CODEC);

    public static void init()
    {
        register("directional_randomized_int_state_provider", DIRECTIONAL_RANDOMIZED_INT_STATE_PROVIDER);
    }

    private static <P extends BlockStateProvider> void register(String key, BlockStateProviderType<P> type)
    {
        FOTPlatform.registerBlockStateProviderType(key, type);
    }
}