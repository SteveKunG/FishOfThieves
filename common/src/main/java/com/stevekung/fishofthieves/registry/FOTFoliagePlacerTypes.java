package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.feature.foliageplacers.BananaLeavesPlacer;
import com.stevekung.fishofthieves.feature.foliageplacers.CoconutFrondsPlacer;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class FOTFoliagePlacerTypes
{
    public static final FoliagePlacerType<CoconutFrondsPlacer> COCONUT_FRONDS_PLACER = new FoliagePlacerType<>(CoconutFrondsPlacer.CODEC);
    public static final FoliagePlacerType<BananaLeavesPlacer> BANANA_LEAVES_PLACER = new FoliagePlacerType<>(BananaLeavesPlacer.CODEC);

    public static void init()
    {
        register("coconut_fronds_placer", COCONUT_FRONDS_PLACER);
        register("banana_leaves_placer", BANANA_LEAVES_PLACER);
    }

    private static <P extends FoliagePlacer> void register(String key, FoliagePlacerType<P> type)
    {
        FOTPlatform.registerFoliagePlacerType(key, type);
    }
}