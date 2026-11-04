package com.stevekung.fishofthieves.registry;

import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;

public class FOTTreeGrowers
{
    public static final TreeGrower COCONUT = new TreeGrower(WeightedList.of(FOTFeatures.COCONUT_TREE), WeightedList.of(), WeightedList.of(), null);
    public static final TreeGrower OLD_COCONUT = new TreeGrower(WeightedList.of(FOTFeatures.OLD_COCONUT_TREE), WeightedList.of(), WeightedList.of(), null);
    public static final TreeGrower BANANA = new TreeGrower(WeightedList.of(FOTFeatures.BANANA_TREE), WeightedList.of(), WeightedList.of(), null);
    public static final TreeGrower MANGO = new TreeGrower(WeightedList.of(FOTFeatures.MANGO_TREE), WeightedList.of(), WeightedList.of(FOTFeatures.MANGO_TREE_BEES_02), null);
}