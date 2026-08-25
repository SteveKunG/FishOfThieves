package com.stevekung.fishofthieves.forge.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.SmallRotatedPillarBlock;
import com.stevekung.fishofthieves.forge.AxeStrippableDummy;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;

@Mixin(SmallRotatedPillarBlock.class)
public abstract class MixinSmallRotatedPillarBlock implements IForgeBlock
{
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
    {
        if (toolAction == ToolActions.AXE_STRIP && AxeStrippableDummy.Small.CUSTOM_STRIPPABLES.containsKey(state.getBlock()))
        {
            return AxeStrippableDummy.Small.CUSTOM_STRIPPABLES.get(state.getBlock()).withPropertiesOf(state);
        }
        return null;
    }
}