package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.feature.treedecorators.BananaDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.BananaShootsDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.CoconutDecorator;
import com.stevekung.fishofthieves.feature.treedecorators.DirectionalAttachedToLeavesDecorator;

import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class FOTTreeDecoratorTypes
{
    public static final TreeDecoratorType<CoconutDecorator> COCONUT = new TreeDecoratorType<>(CoconutDecorator.CODEC);
    public static final TreeDecoratorType<BananaDecorator> BANANA = new TreeDecoratorType<>(BananaDecorator.CODEC);
    public static final TreeDecoratorType<BananaShootsDecorator> BANANA_SHOOTS = new TreeDecoratorType<>(BananaShootsDecorator.CODEC);
    public static final TreeDecoratorType<DirectionalAttachedToLeavesDecorator> DIRECTIONAL_ATTACHED_TO_LEAVES = new TreeDecoratorType<>(DirectionalAttachedToLeavesDecorator.CODEC);

    public static void init()
    {
        register("coconut", COCONUT);
        register("banana", BANANA);
        register("banana_shoots", BANANA_SHOOTS);
        register("directional_attached_to_leaves", DIRECTIONAL_ATTACHED_TO_LEAVES);
    }

    private static <P extends TreeDecorator> void register(String key, TreeDecoratorType<P> type)
    {
        FOTPlatform.registerTreeDecoratorType(key, type);
    }
}