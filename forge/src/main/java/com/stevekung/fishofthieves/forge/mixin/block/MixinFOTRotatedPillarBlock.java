package com.stevekung.fishofthieves.forge.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.FOTRotatedPillarBlock;
import com.stevekung.fishofthieves.forge.AxeStrippableDummy;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;

@Mixin(FOTRotatedPillarBlock.class)
public abstract class MixinFOTRotatedPillarBlock implements IForgeBlock
{
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
    {
        if (toolAction == ToolActions.AXE_STRIP)
        {
            if (AxeStrippableDummy.STRIPPED_BLOCKS.containsKey(state.getBlock()))
            {
                return AxeStrippableDummy.STRIPPED_BLOCKS.get(state.getBlock()).withPropertiesOf(state);
            }
        }
        return null;
    }
}