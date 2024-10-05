package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class FOTTreeDecoratorTypes
{
    public static final TreeDecoratorType<CoconutDecorator> COCONUT = new TreeDecoratorType<>(CoconutDecorator.CODEC);

    public static void init()
    {
        register("coconut", COCONUT);
    }

    private static <P extends TreeDecorator> void register(String key, TreeDecoratorType<P> type)
    {
        Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, FishOfThieves.id(key), type);
    }
}