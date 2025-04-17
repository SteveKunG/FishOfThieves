package com.stevekung.fishofthieves.registry;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.surfacerules.BlockStateConditionSource;
import com.stevekung.fishofthieves.feature.surfacerules.WaterSurroundedConditionSource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class FOTSurfaceRuleConditionSources
{
    public static final MapCodec<WaterSurroundedConditionSource> WATER_SURROUNDED = register("water_surrounded", WaterSurroundedConditionSource.CODEC);
    public static final MapCodec<BlockStateConditionSource> BLOCK_STATE = register("block_state", BlockStateConditionSource.CODEC);

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Surface Rule Condition Source");
    }

    private static <S extends SurfaceRules.ConditionSource> MapCodec<S> register(String key, KeyDispatchDataCodec<S> dataCodec)
    {
        return Registry.register(BuiltInRegistries.MATERIAL_CONDITION, FishOfThieves.id(key), dataCodec.codec());
    }
}