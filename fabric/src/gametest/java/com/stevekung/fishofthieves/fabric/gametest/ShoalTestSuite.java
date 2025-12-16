package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

//TODO
public class ShoalTestSuite implements FOTGameTest
{
    @GameTest(structure = EMPTY_5X5)
    public void shoalLifetimeExpired(GameTestHelper helper)
    {
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalTreasuredLifetimeNotExpired(GameTestHelper helper)
    {
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalRemoveWaterBelow(GameTestHelper helper)
    {
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalRemoveShoalBlockBelow(GameTestHelper helper)
    {
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalInvulnerableNotDestroy(GameTestHelper helper)
    {
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalExplosionDamage(GameTestHelper helper)
    {
    }

    @GameTest(structure = EMPTY_5X5)
    public void shoalDestroyByPiston(GameTestHelper helper)
    {
    }
}