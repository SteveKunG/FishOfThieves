package com.stevekung.fishofthieves.gametest;

import java.util.function.Predicate;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.registry.FOTEntities;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

public class CreateFlockTestSuite implements FabricGameTest
{
    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 300)
    public void fiveNonTrophyTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(3, 3, 3);
        this.createFishTank(helper);

        for (var i = 0; i < 5; i++)
        {
            var entity = helper.spawn(FOTEntities.SPLASHTAIL, blockPos);
            AbstractSchoolingThievesFishAi.initMemories(entity);
        }

        var list = helper.getEntities(FOTEntities.SPLASHTAIL, blockPos, 8.0d);

        helper.runAtTickTime(150, () -> helper.succeedIf(() ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount == 1 && followerCount == 4)
            {
                helper.succeed();
            }
        }));

        helper.runAtTickTime(200, () -> helper.failIf(() ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount != 1)
            {
                helper.fail("Leader should have only one per flock!, got " + leaderCount + " instead");
            }
            if (followerCount != 4)
            {
                helper.fail("Followers should have 4 per flock!, got " + followerCount + " instead");
            }
        }));
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 300)
    public void fiveTrophyTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(3, 3, 3);
        this.createFishTank(helper);

        for (var i = 0; i < 5; i++)
        {
            var entity = helper.spawn(FOTEntities.SPLASHTAIL, blockPos);
            entity.setTrophy(true);
            AbstractSchoolingThievesFishAi.initMemories(entity);
        }

        var list = helper.getEntities(FOTEntities.SPLASHTAIL, blockPos, 8.0d);

        helper.runAtTickTime(150, () -> helper.succeedIf(() ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();

            if (leaderCount == 1 && followerCount == 4)
            {
                helper.succeed();
            }
        }));

        helper.runAtTickTime(200, () -> helper.failIf(() ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();

            if (leaderCount != 1)
            {
                helper.fail("Leader should have only one per flock!, got " + leaderCount + " instead");
            }
            if (followerCount != 4)
            {
                helper.fail("Followers should have 4 per flock!, got " + followerCount + " instead");
            }
        }));
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 300)
    public void oneTrophyFourFollowerTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(3, 3, 3);
        this.createFishTank(helper);

        for (var i = 0; i < 5; i++)
        {
            var entity = helper.spawn(FOTEntities.SPLASHTAIL, blockPos);

            if (i == 0)
            {
                entity.setTrophy(true);
            }

            AbstractSchoolingThievesFishAi.initMemories(entity);
        }

        var list = helper.getEntities(FOTEntities.SPLASHTAIL, blockPos, 8.0d);

        helper.runAtTickTime(150, () -> helper.succeedIf(() ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy)).count();

            if (leaderCount == 1 && followerCount == 4)
            {
                helper.succeed();
            }
        }));

        helper.runAtTickTime(200, () -> helper.failIf(() ->
        {
            var leader = list.stream().filter(AbstractSchoolingThievesFish::isLeader).toList();
            var follower = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy));

            if (!leader.get(0).isTrophy())
            {
                helper.fail("Leader is not trophy!");
            }
            if (follower.anyMatch(AbstractSchoolingThievesFish::isLeader))
            {
                helper.fail("Follower should not be a leader!");
            }
        }));
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 300)
    public void fourTrophyOneFollowerTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(3, 3, 3);
        this.createFishTank(helper);

        for (var i = 0; i < 5; i++)
        {
            var entity = helper.spawn(FOTEntities.SPLASHTAIL, blockPos);

            if (i != 0)
            {
                entity.setTrophy(true);
            }

            AbstractSchoolingThievesFishAi.initMemories(entity);
        }

        var list = helper.getEntities(FOTEntities.SPLASHTAIL, blockPos, 8.0d);

        helper.runAtTickTime(150, () -> helper.succeedIf(() ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var trophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var nonTrophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy)).count();

            if (leaderCount == 1 && trophyFollowerCount == 3 && nonTrophyFollowerCount == 1)
            {
                helper.succeed();
            }
        }));

        helper.runAtTickTime(200, () -> helper.failIf(() ->
        {
            var leader = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).toList();
            var trophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var nonTrophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy)).count();

            if (!leader.get(0).isTrophy())
            {
                helper.fail("Leader is not trophy!");
            }
            if (trophyFollowerCount != 3 && nonTrophyFollowerCount != 1)
            {
                helper.fail("Trophy follower should have 3 and Non-trophy follower should have only one!, got trophyFollowerCount:" + trophyFollowerCount + " and nonTrophyFollowerCount: " + nonTrophyFollowerCount);
            }
        }));
    }

    private void createFishTank(GameTestHelper helper)
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
                    helper.setBlock(new BlockPos(x, 0, z), Blocks.GLASS.defaultBlockState());
                    helper.setBlock(new BlockPos(x, y, z), Blocks.WATER.defaultBlockState());
                }
            }
        }
    }
}