package com.stevekung.fishofthieves.fabric.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

public interface FOTGameTest extends FabricGameTest
{
    default void createFishTank(GameTestHelper helper)
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