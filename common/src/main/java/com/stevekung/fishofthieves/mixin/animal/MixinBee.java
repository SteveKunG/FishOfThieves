package com.stevekung.fishofthieves.mixin.animal;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.block.*;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.bee.Bee;
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

    @Mixin(targets = "net.minecraft.world.entity.animal.bee.Bee$BeeGrowCropGoal")
    public abstract static class MixinBeeGrowCropGoal extends Goal
    {
        @Unique
        private Bee bee;

        @Inject(method = "<init>", at = @At("TAIL"))
        private void fishofthieves$init(Bee bee, CallbackInfo info)
        {
            this.bee = bee;
        }

        @Inject(method = "tick", at = @At(
                value = "JUMP",
                opcode = Opcodes.IF_ICMPGT, // for loop
                ordinal = 0,
                shift = At.Shift.AFTER
        ))
        private void fishofthieves$addGrowableBlocksInCube(CallbackInfo info)
        {
            for (var blockPos : BlockPos.randomInCube(this.bee.getRandom(), 3, this.bee.blockPosition(), 2))
            {
                var blockState = this.bee.level().getBlockState(blockPos);

                if (blockState.is(FOTBlocks.UNDERRIPE_BANANA_CLUSTER_PLANT) || blockState.is(FOTBlocks.BARELY_RIPE_BANANA_CLUSTER_PLANT))
                {
                    ((BonemealableBlock) blockState.getBlock()).performBonemeal((ServerLevel) this.bee.level(), this.bee.getRandom(), blockPos, blockState);
                }
                else if (blockState.is(FOTBlocks.MANGO_FRUIT) || blockState.is(FOTBlocks.HANGING_MANGO_FRUIT))
                {
                    int age = blockState.getValue(AbstractMangoFruitBlock.AGE);

                    if (age < 2)
                    {
                        this.bee.level().setBlock(blockPos, blockState.getBlock().withPropertiesOf(blockState).setValue(AbstractMangoFruitBlock.AGE, age + 1), Block.UPDATE_ALL);
                    }
                }
            }
        }

        @Inject(method = "tick", at = @At(
                value = "JUMP",
                opcode = Opcodes.IFNULL // if (blockState != null)
        ),
                slice = @Slice(
                        from = @At(
                                value = "FIELD",
                                target = "net/minecraft/world/level/block/Blocks.CAVE_VINES:Lnet/minecraft/world/level/block/Block;",
                                shift = At.Shift.AFTER),
                        to = @At(
                                value = "INVOKE",
                                target = "net/minecraft/world/level/Level.levelEvent(ILnet/minecraft/core/BlockPos;I)V")
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
                ((BonemealableBlock) stateToCheck.getBlock()).performBonemeal((ServerLevel) this.bee.level(), this.bee.getRandom(), blockPos, stateToCheck);
            }
        }
    }
}