package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

//TODO
public class ShoalTestSuite implements FOTGameTest
{
    @GameTest(template = EMPTY_5X5)
    public void shoalLifetimeExpired(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createNaturalSpawn(true);
        shoal.setExpiredAt(helper.getLevel().getGameTime() + 50);
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(template = EMPTY_5X5)
    public void shoalTreasuredLifetimeNotExpired(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createTreasuredSpawn(1);
        shoal.setExpiredAt(helper.getLevel().getGameTime() + 50);

        helper.succeedOnTickWhen(80, () -> helper.assertEntityPresent(FOTEntities.SHOAL, blockPos));
    }

    @GameTest(template = EMPTY_5X5)
    public void shoalRemoveWaterBelow(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createTreasuredSpawn(2);
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(template = EMPTY_5X5)
    public void shoalRemoveShoalBlockBelow(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        helper.setBlock(blockPos.below(), FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createTreasuredSpawn(2);
        helper.runAtTickTime(50, () -> helper.setBlock(blockPos.below(), Blocks.WATER));
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(template = EMPTY_5X5)
    public void shoalInvulnerableNotDestroy(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        helper.setBlock(blockPos.below(), FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createTreasuredSpawn(2);
        shoal.setInvulnerable(true);
        helper.runAtTickTime(50, () -> helper.setBlock(blockPos.below(), Blocks.WATER));
        helper.succeedWhenEntityPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(template = EMPTY_5X5)
    public void shoalExplosionDamage(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 3, 1);
        helper.setBlock(blockPos.below(), FOTBlocks.SHOAL);
        var shoal = helper.spawn(FOTEntities.SHOAL, blockPos);
        shoal.createTreasuredSpawn(2);
        helper.runAtTickTime(50, () -> helper.spawn(EntityType.TNT, blockPos));
        helper.succeedWhenEntityNotPresent(FOTEntities.SHOAL, blockPos);
    }

    @GameTest(template = EMPTY_5X5)
    public void shoalDestroyByPiston(GameTestHelper helper)
    {
    }
}