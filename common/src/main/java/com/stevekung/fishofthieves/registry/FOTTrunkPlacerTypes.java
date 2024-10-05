package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.trunkplacers.CoconutTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FOTTrunkPlacerTypes
{
    public static final TrunkPlacerType<CoconutTrunkPlacer> COCONUT_TRUNK_PLACER = new TrunkPlacerType<>(CoconutTrunkPlacer.CODEC);

    public static void init()
    {
        register("coconut_trunk_placer", COCONUT_TRUNK_PLACER);
    }

    private static void register(String key, TrunkPlacerType<?> type)
    {
        Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, FishOfThieves.id(key), type);
    }
}