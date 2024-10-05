package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.foliageplacers.CoconutFrondsPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class FOTFoliagePlacerTypes
{
    public static final FoliagePlacerType<CoconutFrondsPlacer> COCONUT_FRONDS_PLACER = new FoliagePlacerType<>(CoconutFrondsPlacer.CODEC);

    public static void init()
    {
        register("coconut_fronds_placer", COCONUT_FRONDS_PLACER);
    }

    private static <P extends FoliagePlacer> void register(String key, FoliagePlacerType<P> type)
    {
        Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, FishOfThieves.id(key), type);
    }
}