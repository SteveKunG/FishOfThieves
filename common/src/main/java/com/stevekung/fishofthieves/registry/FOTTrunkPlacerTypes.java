package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.trunkplacers.BananaTrunkPlacer;
import com.stevekung.fishofthieves.feature.trunkplacers.CoconutTrunkPlacer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FOTTrunkPlacerTypes
{
    public static final TrunkPlacerType<CoconutTrunkPlacer> COCONUT_TRUNK_PLACER = register("coconut_trunk_placer", new TrunkPlacerType<>(CoconutTrunkPlacer.CODEC));
    public static final TrunkPlacerType<BananaTrunkPlacer> BANANA_TRUNK_PLACER = register("banana_trunk_placer", new TrunkPlacerType<>(BananaTrunkPlacer.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Trunk Placer Type");
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String key, TrunkPlacerType<P> type)
    {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, FishOfThieves.id(key), type);
    }
}