package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.feature.treedecorators.BananaDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.BananaShootsDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.DirectionalAttachedToLeavesDecorator;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class FOTTreeDecoratorTypes
{
    public static final TreeDecoratorType<CoconutDecorator> COCONUT = register("coconut", new TreeDecoratorType<>(CoconutDecorator.CODEC));
    public static final TreeDecoratorType<BananaDecorator> BANANA = register("banana", new TreeDecoratorType<>(BananaDecorator.CODEC));
    public static final TreeDecoratorType<BananaShootsDecorator> BANANA_SHOOTS = register("banana_shoots", new TreeDecoratorType<>(BananaShootsDecorator.CODEC));
    public static final TreeDecoratorType<DirectionalAttachedToLeavesDecorator> DIRECTIONAL_ATTACHED_TO_LEAVES = register("directional_attached_to_leaves", new TreeDecoratorType<>(DirectionalAttachedToLeavesDecorator.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Tree Decorator Type");
    }

    private static <P extends TreeDecorator> TreeDecoratorType<P> register(String key, TreeDecoratorType<P> type)
    {
        return Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, FishOfThieves.id(key), type);
    }
}