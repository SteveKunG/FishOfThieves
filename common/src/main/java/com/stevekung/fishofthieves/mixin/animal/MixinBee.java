package com.stevekung.fishofthieves.mixin.animal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.block.MangoFruitBlock;
import com.stevekung.fishofthieves.block.PineappleCropBlock;
import com.stevekung.fishofthieves.block.PomegranatePlantBlock;
import com.stevekung.fishofthieves.block.TallPomegranatePlantBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

@Mixin(Bee.class)
public class MixinBee
{
    @Inject(method = "attractsBees", cancellable = true, at = @At(value = "RETURN", ordinal = 2))
    private static void fishofthieves$addCheckForFloweringPlants(BlockState blockState, CallbackInfoReturnable<Boolean> info)
    {
        if (blockState.is(FOTBlocks.POMEGRANATE_PLANT))
        {
            info.setReturnValue(blockState.getValue(PomegranatePlantBlock.AGE) == 1);
        }
        else if (blockState.is(FOTBlocks.TALL_POMEGRANATE_PLANT))
        {
            info.setReturnValue(blockState.getValue(TallPomegranatePlantBlock.AGE) == 1 && blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER);
        }
        else if (blockState.is(FOTBlocks.PINEAPPLE_CROP))
        {
            info.setReturnValue(blockState.getValue(PineappleCropBlock.AGE) == 3 && blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER);
        }
    }

    @Mixin(targets = "net.minecraft.world.entity.animal.Bee$BeeGrowCropGoal")
    public static abstract class MixinBeeGrowCropGoal extends Goal
    {
        @SuppressWarnings("mapping")
        @Shadow(aliases = { "this$0", "f_28021_", "field_20373" }, remap = false)
        @Final
        Bee $outer;

        @Inject(method = "tick", at = @At(value = "JUMP", ordinal = 1))
        private void fishofthieves$addGrowableBlocksInCube(CallbackInfo info)
        {
            for (var blockPos : BlockPos.randomInCube(this.$outer.getRandom(), 3, this.$outer.blockPosition(), 2))
            {
                var blockState = this.$outer.level().getBlockState(blockPos);

                if (blockState.is(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT) || blockState.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT))
                {
                    ((BonemealableBlock) blockState.getBlock()).performBonemeal((ServerLevel) this.$outer.level(), this.$outer.getRandom(), blockPos, blockState);
                }
                else if (blockState.is(FOTBlocks.MANGO_FRUIT) || blockState.is(FOTBlocks.HANGING_MANGO_FRUIT))
                {
                    int age = blockState.getValue(MangoFruitBlock.AGE);

                    if (age < 2)
                    {
                        this.$outer.level().setBlock(blockPos, blockState.getBlock().withPropertiesOf(blockState).setValue(MangoFruitBlock.AGE, age + 1), Block.UPDATE_ALL);
                    }
                }
            }
        }

        @Inject(method = "tick", at = @At(value = "JUMP", ordinal = 2),
                slice = @Slice(
                        from = @At(
                                value = "FIELD",
                                target = "net/minecraft/world/level/block/Blocks.CAVE_VINES:Lnet/minecraft/world/level/block/Block;",
                                shift = At.Shift.AFTER)
                )
        )
        private void fishofthieves$addGrowableBlocksWithBonemeal(CallbackInfo info, @Local BlockPos blockPos, @Local(ordinal = 0) BlockState stateToCheck)
        {
            if (stateToCheck.is(FOTBlocks.MANGO_PIT) ||
                    stateToCheck.is(FOTBlocks.PINEAPPLE_CROP) ||
                    stateToCheck.is(FOTBlocks.POMEGRANATE_PLANT) ||
                    stateToCheck.is(FOTBlocks.TALL_POMEGRANATE_PLANT) ||
                    stateToCheck.is(FOTBlocks.POMEGRANATE_SAPLING))
            {
                ((BonemealableBlock) stateToCheck.getBlock()).performBonemeal((ServerLevel) this.$outer.level(), this.$outer.getRandom(), blockPos, stateToCheck);
            }
        }
    }
}