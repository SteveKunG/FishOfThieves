package com.stevekung.fishofthieves.registry;

import java.util.Optional;

import net.minecraft.world.level.block.grower.TreeGrower;

public class FOTTreeGrowers
{
    public static final TreeGrower COCONUT = new TreeGrower("fishofthieves_coconut", Optional.empty(), Optional.of(FOTFeatures.COCONUT_TREE), Optional.empty());
    public static final TreeGrower OLD_COCONUT = new TreeGrower("fishofthieves_old_coconut", Optional.empty(), Optional.of(FOTFeatures.OLD_COCONUT_TREE), Optional.empty());
    public static final TreeGrower BANANA = new TreeGrower("fishofthieves_banana", Optional.empty(), Optional.of(FOTFeatures.BANANA_TREE), Optional.empty());
    public static final TreeGrower MANGO = new TreeGrower("fishofthieves_mango", Optional.empty(), Optional.of(FOTFeatures.MANGO_TREE), Optional.of(FOTFeatures.MANGO_TREE_BEES_02));
}