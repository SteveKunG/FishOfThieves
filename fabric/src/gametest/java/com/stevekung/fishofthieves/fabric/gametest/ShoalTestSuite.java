package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;

public class ShoalTestSuite implements FOTGameTest
{
    @GameTest(structure = SHOAL)
    public void shoalLifetimeExpired(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 4, 2);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.setExpiredAt(helper.getLevel().getGameTime() + 50);
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(structure = SHOAL)
    public void shoalTreasuredLifetimeNotExpired(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 4, 2);
        helper.setBlock(blockPos, FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.createTreasuredSpawn(1);
        shoal.setTreasured(true);
        shoal.setExpiredAt(helper.getLevel().getGameTime() + 20);
        helper.runAtTickTime(50, () -> helper.succeedWhenEntityPresent(FOTEntities.SHOAL, blockPos));
    }

    @GameTest(structure = SHOAL)
    public void shoalRemoveWaterBelow(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 4, 2);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.createTreasuredSpawn(2);
        shoal.setTreasured(true);
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(structure = SHOAL)
    public void shoalRemoveShoalBlockBelow(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 4, 2);
        helper.setBlock(blockPos.below(), FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.createTreasuredSpawn(2);
        shoal.setTreasured(true);
        helper.runAtTickTime(50, () -> helper.setBlock(blockPos.below(), Blocks.WATER));
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(structure = SHOAL)
    public void shoalInvulnerableNotDestroy(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 4, 2);
        helper.setBlock(blockPos.below(), FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.createTreasuredSpawn(2);
        shoal.setInvulnerable(true);
        helper.runAtTickTime(50, () -> helper.setBlock(blockPos.below(), Blocks.WATER));
        helper.succeedWhenEntityPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(structure = SHOAL)
    public void shoalExplosionDamage(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 4, 2);
        helper.setBlock(blockPos.below(), FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.createTreasuredSpawn(2);
        shoal.setTreasured(true);
        helper.runAtTickTime(20, () -> helper.spawn(EntityType.TNT, blockPos));
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalDestroyByPiston(GameTestHelper helper)
    {
        var blockPos = new BlockPos(2, 3, 2);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.createTreasuredSpawn(2);
        shoal.setInvulnerable(true);
        helper.setBlock(blockPos.north(), Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.SOUTH));
        helper.runAtTickTime(50, () -> helper.setBlock(blockPos.north().above(), Blocks.REDSTONE_BLOCK));
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }
}