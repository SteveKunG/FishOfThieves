package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.stateproviders.DirectionalRandomizedIntBooleanStateProvider;
import com.stevekung.fishofthieves.feature.stateproviders.RandomizedIntBooleanStateProvider;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class FOTBlockStateProviderTypes
{
    public static final BlockStateProviderType<RandomizedIntBooleanStateProvider> RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER = register("randomized_int_boolean_state_provider", new BlockStateProviderType<>(RandomizedIntBooleanStateProvider.CODEC));
    public static final BlockStateProviderType<DirectionalRandomizedIntBooleanStateProvider> DIRECTIONAL_RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER = register("directional_randomized_int_boolean_state_provider", new BlockStateProviderType<>(DirectionalRandomizedIntBooleanStateProvider.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Block State Provider Type");
    }

    private static <P extends BlockStateProvider> BlockStateProviderType<P> register(String key, BlockStateProviderType<P> type)
    {
        return Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, FishOfThieves.id(key), type);
    }
}