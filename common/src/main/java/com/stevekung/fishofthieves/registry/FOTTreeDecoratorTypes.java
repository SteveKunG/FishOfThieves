package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.treedecorators.BananaDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.BananaShootsDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class FOTTreeDecoratorTypes
{
    public static final TreeDecoratorType<CoconutDecorator> COCONUT = new TreeDecoratorType<>(CoconutDecorator.CODEC);
    public static final TreeDecoratorType<BananaDecorator> BANANA = new TreeDecoratorType<>(BananaDecorator.CODEC);
    public static final TreeDecoratorType<BananaShootsDecorator> BANANA_SHOOTS = new TreeDecoratorType<>(BananaShootsDecorator.CODEC);

    public static void init()
    {
        register("coconut", COCONUT);
        register("banana", BANANA);
        register("banana_shoots", BANANA_SHOOTS);
    }

    private static <P extends TreeDecorator> void register(String key, TreeDecoratorType<P> type)
    {
        Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, FishOfThieves.id(key), type);
    }
}