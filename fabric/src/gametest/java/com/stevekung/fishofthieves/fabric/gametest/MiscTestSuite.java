package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.Blocks;

public class MiscTestSuite implements FOTGameTest
{
    @GameTest(structure = FISH_TANK_SPLIT, maxTicks = 300, maxAttempts = 5)
    public void rumBattlegillFollowNausea(GameTestHelper helper)
    {
        var battlegillPos = new BlockPos(1, 7, 1);
        var dolphinPos = new BlockPos(6, 3, 1);

        var battlegill = helper.spawn(FOTEntities.BATTLEGILL, battlegillPos);
        battlegill.setVariant(helper.getLevel().registryAccess().lookupOrThrow(FOTRegistries.BATTLEGILL_VARIANT).getOrThrow(BattlegillVariants.RUM));

        var dolphin = helper.spawnWithNoFreeWill(EntityTypes.DOLPHIN, dolphinPos);
        dolphin.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 10000));

        helper.runAtTickTime(100, () -> this.removeTintedGlass(helper));

        helper.runAtTickTime(200, () ->
        {
            var battlegill2 = helper.getEntities(FOTEntities.BATTLEGILL, dolphinPos, 2);

            if (!battlegill2.isEmpty())
            {
                if (battlegill2.getFirst().getBrain().hasMemoryValue(FOTMemoryModuleTypes.IS_EFFECT_FOLLOWER))
                {
                    helper.succeed();
                }
                else
                {
                    helper.fail(Component.literal("Battlegill does not follow dolphin"));
                }
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