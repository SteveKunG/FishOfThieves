package com.stevekung.fishofthieves.fabric.gametest;

import java.util.function.Predicate;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;

public class CreateFlockTestSuite implements FOTGameTest
{
    @GameTest(maxTicks = 300)
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

        helper.runAtTickTime(150, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount == 1 && followerCount == 4)
            {
                helper.succeed();
            }
        });
        helper.runAtTickTime(200, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).count();

            if (leaderCount != 1)
            {
                helper.fail(Component.literal("Leader should have only one per flock!, got " + leaderCount + " instead"));
            }
            if (followerCount != 4)
            {
                helper.fail(Component.literal("Followers should have 4 per flock!, got " + followerCount + " instead"));
            }
        });
    }

    @GameTest(maxTicks = 300)
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

        helper.runAtTickTime(150, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();

            if (leaderCount == 1 && followerCount == 4)
            {
                helper.succeed();
            }
        });

        helper.runAtTickTime(200, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();

            if (leaderCount != 1)
            {
                helper.fail(Component.literal("Leader should have only one per flock!, got " + leaderCount + " instead"));
            }
            if (followerCount != 4)
            {
                helper.fail(Component.literal("Followers should have 4 per flock!, got " + followerCount + " instead"));
            }
        });
    }

    @GameTest(maxTicks = 300)
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

        helper.runAtTickTime(150, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var followerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy)).count();

            if (leaderCount == 1 && followerCount == 4)
            {
                helper.succeed();
            }
        });

        helper.runAtTickTime(200, () ->
        {
            var leader = list.stream().filter(AbstractSchoolingThievesFish::isLeader).toList();
            var follower = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy));

            if (leader.isEmpty())
            {
                helper.fail(Component.literal("Leader is not found! Is it dead? :("));
            }
            if (!leader.getFirst().isTrophy())
            {
                helper.fail(Component.literal("Leader is not trophy!"));
            }
            if (follower.anyMatch(AbstractSchoolingThievesFish::isLeader))
            {
                helper.fail(Component.literal("Follower should not be a leader!"));
            }
        });
    }

    @GameTest(maxTicks = 300)
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

        helper.runAtTickTime(150, () ->
        {
            var leaderCount = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var trophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var nonTrophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy)).count();

            if (leaderCount == 1 && trophyFollowerCount == 3 && nonTrophyFollowerCount == 1)
            {
                helper.succeed();
            }
        });

        helper.runAtTickTime(200, () ->
        {
            var leader = list.stream().filter(AbstractSchoolingThievesFish::isLeader).filter(AbstractSchoolingThievesFish::isTrophy).toList();
            var trophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(AbstractSchoolingThievesFish::isTrophy).count();
            var nonTrophyFollowerCount = list.stream().filter(AbstractSchoolingThievesFish::isFollower).filter(Predicate.not(AbstractSchoolingThievesFish::isTrophy)).count();

            if (leader.isEmpty())
            {
                helper.fail(Component.literal("Leader is not found! Is it dead? :("));
            }
            if (!leader.getFirst().isTrophy())
            {
                helper.fail(Component.literal("Leader is not trophy!"));
            }
            if (trophyFollowerCount != 3 && nonTrophyFollowerCount != 1)
            {
                helper.fail(Component.literal("Trophy follower should have 3 and Non-trophy follower should have only one!, got trophyFollowerCount: " + trophyFollowerCount + " and nonTrophyFollowerCount: " + nonTrophyFollowerCount));
            }
        });
    }
}