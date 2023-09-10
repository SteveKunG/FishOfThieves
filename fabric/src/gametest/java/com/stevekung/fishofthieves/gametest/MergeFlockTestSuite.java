package com.stevekung.fishofthieves.gametest;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.registry.FOTEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

public class MergeFlockTestSuite implements FOTGameTest
{
    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 400)
    public void oneSmallFlockMergeIntoSmallLeaderFlock(GameTestHelper helper)
    {
        var blockPos = new BlockPos(3, 3, 3);
        var blockPos1 = new BlockPos(1, 3, 1);
        var blockPos2 = new BlockPos(6, 3, 1);
        this.createFishTank(helper);

        for (var i = 0; i < 8; i++)
        {
            var entity = helper.spawn(FOTEntities.PLENTIFIN, i > 3 ? blockPos2 : blockPos1);
            AbstractSchoolingThievesFishAi.initMemories(entity);
        }

        this.removeTintedGlass(helper);
        var list = helper.getEntities(FOTEntities.PLENTIFIN, blockPos, 8.0d);

        helper.runAtTickTime(300, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount == 1 && followerCount == 7)
            {
                helper.succeed();
            }
        });

        helper.runAtTickTime(380, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount != 1)
            {
                helper.fail("Leader should have only one per flock!, got " + leaderCount + " instead");
            }
            if (followerCount != 4)
            {
                helper.fail("Followers should have 7 per flock!, got " + followerCount + " instead");
            }
        });
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 400)
    public void oneSmallFlockMergeIntoTrophyLeaderFlock(GameTestHelper helper)
    {
        var blockPos = new BlockPos(3, 3, 3);
        var blockPos1 = new BlockPos(1, 3, 1);
        var blockPos2 = new BlockPos(6, 3, 1);
        this.createFishTank(helper);

        for (var i = 0; i < 8; i++)
        {
            var entity = helper.spawn(FOTEntities.PLENTIFIN, i > 3 ? blockPos2 : blockPos1);

            if (i == 0)
            {
                entity.setTrophy(true);
            }

            AbstractSchoolingThievesFishAi.initMemories(entity);
        }

        this.removeTintedGlass(helper);
        var list = helper.getEntities(FOTEntities.PLENTIFIN, blockPos, 8.0d);

        helper.runAtTickTime(300, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount == 1 && followerCount == 7)
            {
                helper.succeed();
            }
        });

        helper.runAtTickTime(380, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount != 1)
            {
                helper.fail("Leader should have only one per flock!, got " + leaderCount + " instead");
            }
            if (followerCount != 4)
            {
                helper.fail("Followers should have 7 per flock!, got " + followerCount + " instead");
            }
        });
    }

    @Override
    public void createFishTank(GameTestHelper helper)
    {
        var relative = 0;
        var size = 8;

        for (var x = relative; x < size; x++)
        {
            for (var y = 1; y < size; y++)
            {
                for (var z = relative; z < size; z++)
                {
                    if (x == relative || x == size - 1 || z == relative || z == size - 1)
                    {
                        helper.setBlock(new BlockPos(x, y, z), Blocks.GLASS.defaultBlockState());
                        continue;
                    }

                    if (x == 3 || x == 4)
                    {
                        helper.setBlock(new BlockPos(x, y, z), Blocks.TINTED_GLASS.defaultBlockState());
                    }
                    else
                    {
                        helper.setBlock(new BlockPos(x, y, z), Blocks.WATER.defaultBlockState());
                    }
                    helper.setBlock(new BlockPos(x, 0, z), Blocks.GLASS.defaultBlockState());
                }
            }
        }
    }

    private void removeTintedGlass(GameTestHelper helper)
    {
        helper.runAtTickTime(200, () ->
        {
            var relative = 0;
            var size = 8;

            for (var x = relative; x < size; x++)
            {
                for (var y = 0; y < size; y++)
                {
                    for (var z = relative; z < size; z++)
                    {
                        var blockPos = new BlockPos(x, y, z);
                        var blockState = helper.getBlockState(blockPos);

                        if (blockState.is(Blocks.TINTED_GLASS))
                        {
                            helper.setBlock(blockPos, Blocks.WATER.defaultBlockState());
                        }
                    }
                }
            }
        });
    }
}