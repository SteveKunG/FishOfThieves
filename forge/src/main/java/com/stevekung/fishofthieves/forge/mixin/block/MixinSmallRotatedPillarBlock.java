package com.stevekung.fishofthieves.forge.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.SmallRotatedPillarBlock;
import com.stevekung.fishofthieves.forge.AxeStrippableDummy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;

@Mixin(SmallRotatedPillarBlock.class)
public class MixinSmallRotatedPillarBlock implements IForgeBlock
{
    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable)
    {
        return false;
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
    {
        if (toolAction == ToolActions.AXE_STRIP)
        {
            if (AxeStrippableDummy.Small.CUSTOM_STRIPPABLES.containsKey(state.getBlock()))
            {
                return AxeStrippableDummy.Small.CUSTOM_STRIPPABLES.get(state.getBlock()).withPropertiesOf(state);
            }
        }
        return null;
    }
}