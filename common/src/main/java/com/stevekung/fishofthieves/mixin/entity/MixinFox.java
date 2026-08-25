package com.stevekung.fishofthieves.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.block.PomegranatePlantBlock;
import com.stevekung.fishofthieves.block.TallPomegranatePlantBlock;
import com.stevekung.fishofthieves.registry.FOTBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Fox.class)
public class MixinFox
{
    @Mixin(Fox.FoxEatBerriesGoal.class)
    public abstract static class MixinFoxEatBerriesGoal extends MoveToBlockGoal
    {
        @Unique
        private Fox fox;

        MixinFoxEatBerriesGoal()
        {
            super(null, 0, 0);
        }

        @Inject(method = "<init>", at = @At("TAIL"))
        private void fishofthieves$init(Fox fox, double speedModifier, int searchRange, int verticalSearchRange, CallbackInfo info)
        {
            this.fox = fox;
        }

        @Inject(method = "isValidTarget", cancellable = true, at = @At("HEAD"))
        private void fishofthieves$isPomegranate(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> info)
        {
            var blockState = level.getBlockState(pos);

            if ((blockState.is(FOTBlocks.POMEGRANATE_PLANT) || blockState.is(FOTBlocks.TALL_POMEGRANATE_PLANT)) && PomegranatePlantBlock.canHarvest(blockState.getValue(PomegranatePlantBlock.AGE)))
            {
                info.setReturnValue(true);
            }
        }

        @Inject(method = "onReachedTarget", at = @At(value = "FIELD", target = "net/minecraft/world/level/block/Blocks.SWEET_BERRY_BUSH:Lnet/minecraft/world/level/block/Block;"))
        private void fishofthieves$pickPomegranate(CallbackInfo info, @Local BlockState blockState)
        {
            if (blockState.is(FOTBlocks.POMEGRANATE_PLANT))
            {
                PomegranatePlantBlock.pick(blockState, this.fox.level(), this.blockPos, this.fox);
            }
            else if (blockState.is(FOTBlocks.TALL_POMEGRANATE_PLANT))
            {
                TallPomegranatePlantBlock.pick(blockState, this.fox.level(), this.blockPos, this.fox);
            }
        }
    }
}