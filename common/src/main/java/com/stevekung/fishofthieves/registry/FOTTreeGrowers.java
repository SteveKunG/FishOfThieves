package com.stevekung.fishofthieves.registry;

import java.util.Optional;

import net.minecraft.world.level.block.grower.TreeGrower;

public class FOTTreeGrowers
{
    public static final TreeGrower COCONUT = new TreeGrower("fot_coconut", Optional.empty(), Optional.of(FOTFeatures.COCONUT_TREE), Optional.empty());
    public static final TreeGrower BANANA = new TreeGrower("fot_banana", Optional.empty(), Optional.of(FOTFeatures.BANANA_TREE), Optional.empty());
    public static final TreeGrower MANGO = new TreeGrower("fot_mango", Optional.empty(), Optional.of(FOTFeatures.MANGO_TREE), Optional.of(FOTFeatures.MANGO_TREE_BEES_02));
}