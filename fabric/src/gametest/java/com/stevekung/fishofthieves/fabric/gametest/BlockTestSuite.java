package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.block.*;
import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.properties.AttachFace;

public class BlockTestSuite implements FOTGameTest
{
    @GameTest(template = EMPTY_3X4)
    public void coconutFrondsSingleStateTest(GameTestHelper helper)
    {
        var polePos = new BlockPos(1, 2, 3);
        var blockPos = new BlockPos(1, 4, 2);

        for (var y = 0; y < 3; y++)
        {
            helper.setBlock(polePos.above(y), Blocks.POLISHED_ANDESITE);
        }

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRONDS);
        helper.setBlock(blockPos.north(), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE));
        helper.setBlock(blockPos.north(2), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL));

        helper.runAtTickTime(20, () ->
        {
            helper.destroyBlock(blockPos.north(1));
            helper.succeedWhen(() -> helper.assertBlockState(blockPos, blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.SINGLE, () -> "Expected coconut fronds single state!"));
        });
    }

    @GameTest(template = EMPTY_3X4)
    public void coconutFrondsStemAndTailStateTest(GameTestHelper helper)
    {
        var polePos = new BlockPos(1, 2, 3);
        var blockPos = new BlockPos(1, 4, 2);

        for (var y = 0; y < 3; y++)
        {
            helper.setBlock(polePos.above(y), Blocks.POLISHED_ANDESITE);
        }

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRONDS);
        helper.setBlock(blockPos.north(), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE));
        helper.setBlock(blockPos.north(2), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL));

        helper.runAtTickTime(20, () ->
        {
            helper.destroyBlock(blockPos.north(2));
            helper.succeedWhen(() ->
            {
                helper.assertBlockState(blockPos, blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.STEM, () -> "Expected coconut fronds stem state!");
                helper.assertBlockState(blockPos.north(), blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.TAIL, () -> "Expected coconut fronds tail state!");
            });
        });
    }

    @GameTest(template = EMPTY_3X5)
    public void coconutFrondsAllStateTest(GameTestHelper helper)
    {
        var polePos = new BlockPos(1, 2, 4);
        var blockPos = new BlockPos(1, 4, 3);

        for (var y = 0; y < 3; y++)
        {
            helper.setBlock(polePos.above(y), Blocks.POLISHED_ANDESITE);
        }

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.STEM));
        helper.setBlock(blockPos.north(), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE));
        helper.setBlock(blockPos.north(2), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.MIDDLE));
        helper.setBlock(blockPos.north(3), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL));

        helper.runAtTickTime(20, () ->
        {
            helper.destroyBlock(blockPos.north(3));
            helper.succeedWhen(() ->
            {
                helper.assertBlockState(blockPos, blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.STEM, () -> "Expected coconut fronds stem state!");
                helper.assertBlockState(blockPos.north(), blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.MIDDLE, () -> "Expected coconut fronds middle state!");
                helper.assertBlockState(blockPos.north(2), blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.TAIL, () -> "Expected coconut fronds tail state!");
            });
        });
    }

    @GameTest(template = EMPTY_3X4)
    public void bonemealCoconutFrondsTest(GameTestHelper helper)
    {
        var polePos = new BlockPos(1, 2, 3);
        var blockPos = new BlockPos(1, 4, 2);
        var player = helper.makeMockPlayer(GameType.CREATIVE);

        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BONE_MEAL));

        for (var y = 0; y < 3; y++)
        {
            helper.setBlock(polePos.above(y), Blocks.POLISHED_ANDESITE);
        }

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRONDS);

        helper.runAtTickTime(20, () ->
        {
            helper.useBlock(blockPos, player);
            helper.succeedWhen(() -> helper.assertBlockState(blockPos.north(), blockState -> blockState.is(FOTBlocks.COCONUT_FRONDS) && blockState.getValue(CoconutFrondsBlock.PART) == CoconutFrondsBlock.Part.TAIL, () -> "Expected coconut fronds tail state!"));
        });
    }

    @GameTest(template = EMPTY_3X4, timeoutTicks = 200, attempts = 3, skyAccess = true)
    public void waterFromCoconutFrondsTest(GameTestHelper helper)
    {
        var polePos = new BlockPos(1, 2, 3);
        var blockPos = new BlockPos(1, 4, 2);
        var cauldronPos = new BlockPos(1, 2, 1);
        var level = helper.getLevel();

        for (var y = 0; y < 3; y++)
        {
            helper.setBlock(polePos.above(y), Blocks.POLISHED_ANDESITE);
        }

        level.setWeatherParameters(0, 10000, true, false);

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRONDS);
        helper.setBlock(blockPos.north(), FOTBlocks.COCONUT_FRONDS.defaultBlockState().setValue(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL));

        helper.setBlock(cauldronPos, Blocks.CAULDRON);

        helper.onEachTick(() -> helper.randomTick(cauldronPos.above(2)));

        helper.succeedWhen(() ->
        {
            helper.assertBlockState(cauldronPos, blockState -> blockState.is(Blocks.WATER_CAULDRON) && blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, () -> "Expected full water cauldron!");
            level.setWeatherParameters(0, 0, false, false);
        });
    }

    @GameTest(template = EMPTY_3X3, attempts = 3)
    public void growCoconutFruitsTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 2, 1);

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG);
        helper.onEachTick(() -> helper.randomTick(blockPos));

        helper.succeedWhen(() ->
        {
            for (var direction : Direction.Plane.HORIZONTAL.stream().toList())
            {
                helper.assertBlock(blockPos.relative(direction), block -> block == FOTBlocks.COCONUT_FRUIT, () -> "Expected coconut fruit at %s!".formatted(direction));
            }
        });
    }

    @GameTest(template = COCONUT_FALL)
    public void coconutFallTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 8, 2);
        var targetPos = new BlockPos(1, 2, 1);

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG);
        helper.setBlock(blockPos.north(), FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.FACING, Direction.SOUTH).setValue(CoconutFruitBlock.AGE, 2));

        helper.runAtTickTime(20, () -> helper.destroyBlock(blockPos));

        helper.succeedWhen(() -> helper.assertEntityPresent(EntityType.FALLING_BLOCK, targetPos));
    }

    @GameTest(template = COCONUT_FALL)
    public void coconutFallHurtChickenTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 8, 2);
        var targetPos = new BlockPos(1, 2, 1);

        var chicken = helper.spawnWithNoFreeWill(EntityType.CHICKEN, targetPos);
        helper.withLowHealth(chicken);

        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG);
        helper.setBlock(blockPos.north(), FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.FACING, Direction.SOUTH).setValue(CoconutFruitBlock.AGE, 2));

        helper.runAtTickTime(20, () -> helper.destroyBlock(blockPos));

        helper.succeedWhen(() -> helper.assertEntityNotPresent(EntityType.CHICKEN, targetPos));
    }

    @GameTest(template = EMPTY_3X3)
    public void growBananaShootsTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 2, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BONE_MEAL, 64));

        helper.forEveryBlockInStructure(blockPos1 ->
        {
            if (helper.getBlockState(blockPos1).is(Blocks.POLISHED_ANDESITE))
            {
                helper.setBlock(blockPos1, Blocks.DIRT);
            }
        });

        helper.setBlock(blockPos, FOTBlocks.BANANA_STEM);
        helper.onEachTick(() -> helper.useBlock(blockPos, player));

        helper.succeedWhen(() ->
        {
            for (var direction : Direction.Plane.HORIZONTAL.stream().toList())
            {
                helper.assertBlock(blockPos.relative(direction), block -> block == FOTBlocks.BANANA_SHOOTS_PLANT, () -> "Expected banana shoots plant at %s!".formatted(direction));
            }
        });
    }

    @GameTest(template = EMPTY_3X4, timeoutTicks = 200, attempts = 3, skyAccess = true)
    public void waterFromBananaLeavesTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 4, 3);
        var cauldronPos = new BlockPos(1, 2, 1);
        var level = helper.getLevel();

        level.setWeatherParameters(0, 10000, true, false);

        helper.setBlock(blockPos, FOTBlocks.BANANA_STEM);
        helper.setBlock(blockPos.north(), FOTBlocks.BANANA_LEAVES.defaultBlockState().setValue(BananaLeavesBlock.COUNT, 2));
        helper.setBlock(blockPos.north(2), FOTBlocks.BANANA_LEAVES.defaultBlockState().setValue(BananaLeavesBlock.COUNT, 2).setValue(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL));

        helper.setBlock(cauldronPos, Blocks.CAULDRON);

        helper.onEachTick(() -> helper.randomTick(cauldronPos.above(2)));

        helper.succeedWhen(() ->
        {
            helper.assertBlockState(cauldronPos, blockState -> blockState.is(Blocks.WATER_CAULDRON) && blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, () -> "Expected full water cauldron!");
            level.setWeatherParameters(0, 0, false, false);
        });
    }

    @GameTest(template = BANANA_CLUSTERS)
    public void shotArrowToBreakBananaClustersTest(GameTestHelper helper)
    {
        var buttonPos = new BlockPos(1, 4, 0);
        var blockPos = new BlockPos(1, 5, 2);

        helper.setBlock(blockPos, FOTBlocks.RIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(BananaClusterPlantBlock.FACING, Direction.SOUTH).setValue(BananaClusterPlantBlock.HANGING, BananaClusterPlantBlock.HangingType.STEM));
        helper.setBlock(blockPos.below(), FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(BananaClusterPlantBlock.FACING, Direction.SOUTH));
        helper.setBlock(blockPos.below(2), FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT.defaultBlockState().setValue(BananaClusterPlantBlock.FACING, Direction.SOUTH));
        helper.setBlock(blockPos.below(3), FOTBlocks.BANANA_BLOSSOM_PLANT.defaultBlockState().setValue(BananaBlossomPlantBlock.FACING, Direction.SOUTH).setValue(BananaBlossomPlantBlock.HANGING, BananaHangingType.SMALL_CLUSTER));

        helper.runAtTickTime(20, () -> helper.pressButton(buttonPos));

        helper.succeedWhen(() ->
        {
            for (var i = 0; i < 4; i++)
            {
                helper.assertBlock(new BlockPos(1, 2, 2).above(i), block -> block == Blocks.AIR, () -> "Expected air block!");
            }
        });
    }

    @GameTest(template = EMPTY_3X4)
    public void shotArrowToBreakCoconutFruitTest(GameTestHelper helper)
    {
        var dispenserPos = new BlockPos(1, 4, 0);
        var blockPos = new BlockPos(1, 4, 2);

        helper.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.SOUTH));
        helper.setBlock(dispenserPos.above(), Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR));

        if (helper.getBlockEntity(dispenserPos) instanceof DispenserBlockEntity dispenser)
        {
            dispenser.setItem(0, new ItemStack(Items.ARROW, 64));
        }

        helper.setBlock(blockPos.south(), FOTBlocks.COCONUT_FRUIT_GROWABLE_LOG);
        helper.setBlock(blockPos, FOTBlocks.COCONUT_FRUIT.defaultBlockState().setValue(CoconutFruitBlock.FACING, Direction.SOUTH).setValue(CoconutFruitBlock.AGE, 2));

        helper.runAtTickTime(20, () -> helper.pressButton(dispenserPos.above()));

        helper.succeedWhen(() -> helper.assertEntityPresent(EntityType.FALLING_BLOCK, blockPos.below(2)));
    }

    @GameTest(template = SHOT_MANGO, attempts = 64, timeoutTicks = 60)
    public void shotArrowToBreakMangoFruitTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 0);

        helper.runAtTickTime(20, () -> helper.pressButton(blockPos));

        helper.succeedWhen(() -> helper.assertEntityPresent(EntityType.FALLING_BLOCK, new BlockPos(1, 2, 6)));
    }

    @GameTest(template = EMPTY_3X4, attempts = 64, timeoutTicks = 60)
    public void shotArrowToBreakHangingMangoFruitTest(GameTestHelper helper)
    {
        var dispenserPos = new BlockPos(1, 4, 0);
        var blockPos = new BlockPos(1, 4, 2);

        helper.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.SOUTH));
        helper.setBlock(dispenserPos.above(), Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR));

        if (helper.getBlockEntity(dispenserPos) instanceof DispenserBlockEntity dispenser)
        {
            dispenser.setItem(0, new ItemStack(Items.ARROW, 64));
        }

        helper.runAtTickTime(20, () -> helper.pressButton(dispenserPos.above()));

        helper.setBlock(blockPos.above(), FOTBlocks.MANGO_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
        helper.setBlock(blockPos, FOTBlocks.HANGING_MANGO_FRUIT.defaultBlockState().setValue(HangingMangoFruitBlock.AGE, 2));

        helper.succeedWhen(() -> helper.assertEntityPresent(EntityType.FALLING_BLOCK, blockPos.below(2)));
    }

    @GameTest(template = EMPTY_3X3)
    public void growMangoesTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BONE_MEAL, 64));

        helper.setBlock(blockPos, FOTBlocks.MANGO_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
        helper.onEachTick(() -> helper.useBlock(blockPos, player));

        helper.succeedWhen(() ->
        {
            for (var direction : Direction.stream().filter(direction -> direction != Direction.UP).toList())
            {
                if (direction == Direction.DOWN)
                {
                    helper.assertBlock(blockPos.relative(direction), block -> block == FOTBlocks.HANGING_MANGO_FRUIT, () -> "Expected hanging mango fruit at %s!".formatted(direction));
                }
                else
                {
                    helper.assertBlock(blockPos.relative(direction), block -> block == FOTBlocks.MANGO_FRUIT, () -> "Expected mango fruit at %s!".formatted(direction));
                }
            }
        });
    }

    @GameTest(template = EMPTY_3X4)
    public void mangoFallTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 4, 3);
        var targetPos = new BlockPos(1, 2, 2);

        helper.setBlock(blockPos, FOTBlocks.MANGO_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
        helper.setBlock(blockPos.north(), FOTBlocks.MANGO_FRUIT.defaultBlockState().setValue(MangoFruitBlock.FACING, Direction.SOUTH).setValue(MangoFruitBlock.AGE, 2).setValue(MangoFruitBlock.FALLING, false));

        helper.runAtTickTime(20, () -> helper.destroyBlock(blockPos));

        helper.succeedWhen(() -> helper.assertEntityPresent(EntityType.FALLING_BLOCK, targetPos));
    }

    @GameTest(template = EMPTY_3X4)
    public void mangoFallHurtChickenTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 4, 3);
        var targetPos = new BlockPos(1, 2, 2);

        helper.setBlock(blockPos, FOTBlocks.MANGO_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
        helper.setBlock(blockPos.north(), FOTBlocks.MANGO_FRUIT.defaultBlockState().setValue(MangoFruitBlock.FACING, Direction.SOUTH).setValue(MangoFruitBlock.AGE, 2).setValue(MangoFruitBlock.FALLING, false));
        var chicken = helper.spawnWithNoFreeWill(EntityType.CHICKEN, targetPos);
        helper.withLowHealth(chicken);

        helper.runAtTickTime(20, () -> helper.destroyBlock(blockPos));

        helper.succeedWhen(() -> helper.assertEntityNotPresent(EntityType.CHICKEN, targetPos));
    }

    @GameTest(template = EMPTY_3X3)
    public void shearsRipePineappleBlockTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 2, 1);
        var player = helper.makeMockPlayer(GameType.CREATIVE);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.SHEARS));

        helper.setBlock(blockPos, FOTBlocks.RIPE_PINEAPPLE_BLOCK);
        helper.useBlock(blockPos, player);

        helper.succeedWhen(() -> helper.assertBlock(blockPos, block -> block == FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, () -> "Expected crownless ripe pineapple block!"));
    }

    @GameTest(template = EMPTY_3X3)
    public void bonemealPomegranatePlantTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 2, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BONE_MEAL, 3));

        helper.forEveryBlockInStructure(blockPos1 ->
        {
            if (helper.getBlockState(blockPos1).is(Blocks.POLISHED_ANDESITE))
            {
                helper.setBlock(blockPos1, Blocks.DIRT);
            }
        });

        helper.setBlock(blockPos, FOTBlocks.POMEGRANATE_PLANT);
        helper.onEachTick(() -> helper.useBlock(blockPos, player));

        helper.succeedWhen(() -> helper.assertBlockState(blockPos, blockState -> blockState.is(FOTBlocks.POMEGRANATE_PLANT) && blockState.getValue(PomegranatePlantBlock.AGE) == 3, () -> "Expected full growth pomegranate plant!"));
    }

    @GameTest(template = EMPTY_3X3)
    public void bonemealTallPomegranatePlantTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 2, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BONE_MEAL, 3));

        helper.forEveryBlockInStructure(blockPos1 ->
        {
            if (helper.getBlockState(blockPos1).is(Blocks.POLISHED_ANDESITE))
            {
                helper.setBlock(blockPos1, Blocks.DIRT);
            }
        });

        helper.setBlock(blockPos, FOTBlocks.TALL_POMEGRANATE_PLANT);
        helper.onEachTick(() -> helper.useBlock(blockPos, player));

        helper.succeedWhen(() ->
        {
            helper.assertBlockState(blockPos, blockState -> blockState.is(FOTBlocks.TALL_POMEGRANATE_PLANT) && blockState.getValue(TallPomegranatePlantBlock.AGE) == 3, () -> "Expected full growth tall pomegranate plant!");
            helper.assertBlockState(blockPos.above(), blockState -> blockState.is(FOTBlocks.TALL_POMEGRANATE_PLANT) && blockState.getValue(TallPomegranatePlantBlock.AGE) == 3, () -> "Expected full growth tall pomegranate plant!");
        });
    }
}