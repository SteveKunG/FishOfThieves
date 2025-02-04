package com.stevekung.fishofthieves.neoforge.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.FOTRotatedPillarBlock;
import com.stevekung.fishofthieves.neoforge.AxeStrippableDummy;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

@Mixin(FOTRotatedPillarBlock.class)
public abstract class MixinFOTRotatedPillarBlock implements IBlockExtension
{
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate)
    {
        if (itemAbility == ItemAbilities.AXE_STRIP)
        {
            if (AxeStrippableDummy.STRIPPED_BLOCKS.containsKey(state.getBlock()))
            {
                return AxeStrippableDummy.STRIPPED_BLOCKS.get(state.getBlock()).withPropertiesOf(state);
            }
        }
        return null;
    }
}