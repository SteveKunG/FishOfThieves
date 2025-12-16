package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

public class MiscTestSuite implements FOTGameTest
{
    @GameTest(template = FISH_TANK_SPLIT, timeoutTicks = 300)
    public void rumBattlegillFollowNausea(GameTestHelper helper)
    {
        var battlegillPos = new BlockPos(1, 7, 1);
        var dolphinPos = new BlockPos(6, 3, 1);

        var battlegill = helper.spawn(FOTEntities.BATTLEGILL, battlegillPos);
        battlegill.setVariant(BattlegillVariants.RUM);

        var dolphin = helper.spawnWithNoFreeWill(EntityType.DOLPHIN, dolphinPos);
        dolphin.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10000));

        helper.runAtTickTime(100, () -> this.removeTintedGlass(helper));

        helper.runAtTickTime(200, () ->
        {
            var battlegill2 = helper.getEntities(FOTEntities.BATTLEGILL, dolphinPos, 2).get(0);

            if (battlegill2.getBrain().hasMemoryValue(FOTMemoryModuleTypes.IS_EFFECT_FOLLOWER))
            {
                helper.succeed();
            }
            else
            {
                helper.fail("Battlegill does not follow dolphin");
            }
        });
    }

    private void removeTintedGlass(GameTestHelper helper)
    {
        var relative = 0;
        var size = 9;

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
    }
}