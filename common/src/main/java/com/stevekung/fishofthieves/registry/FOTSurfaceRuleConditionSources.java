package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.surfacerules.BlockStateConditionSource;
import com.stevekung.fishofthieves.feature.surfacerules.WaterSurroundedConditionSource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class FOTSurfaceRuleConditionSources
{
    public static final KeyDispatchDataCodec<WaterSurroundedConditionSource> WATER_SURROUNDED = WaterSurroundedConditionSource.CODEC;
    public static final KeyDispatchDataCodec<BlockStateConditionSource> BLOCK_STATE = BlockStateConditionSource.CODEC;

    public static void init()
    {
        register("water_surrounded", WATER_SURROUNDED);
        register("block_state", BLOCK_STATE);
    }

    private static <S extends SurfaceRules.ConditionSource> void register(String key, KeyDispatchDataCodec<S> dataCodec)
    {
        Registry.register(BuiltInRegistries.MATERIAL_CONDITION, FishOfThieves.id(key), dataCodec.codec());
    }
}