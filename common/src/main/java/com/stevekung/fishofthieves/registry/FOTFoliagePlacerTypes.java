package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.foliageplacers.BananaLeavesPlacer;
import com.stevekung.fishofthieves.feature.foliageplacers.CoconutFrondsPlacer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class FOTFoliagePlacerTypes
{
    public static final FoliagePlacerType<CoconutFrondsPlacer> COCONUT_FRONDS_PLACER = register("coconut_fronds_placer", new FoliagePlacerType<>(CoconutFrondsPlacer.CODEC));
    public static final FoliagePlacerType<BananaLeavesPlacer> BANANA_LEAVES_PLACER = register("banana_leaves_placer", new FoliagePlacerType<>(BananaLeavesPlacer.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Trigger Type");
    }

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String key, FoliagePlacerType<P> type)
    {
        return Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, FishOfThieves.id(key), type);
    }
}