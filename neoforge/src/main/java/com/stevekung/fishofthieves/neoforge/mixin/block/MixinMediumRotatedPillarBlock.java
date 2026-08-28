package com.stevekung.fishofthieves.neoforge.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.MediumRotatedPillarBlock;
import com.stevekung.fishofthieves.neoforge.AxeStrippableDummy;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

@Mixin(MediumRotatedPillarBlock.class)
public abstract class MixinMediumRotatedPillarBlock implements IBlockExtension
{
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate)
    {
        if (itemAbility == ItemAbilities.AXE_STRIP && AxeStrippableDummy.Medium.CUSTOM_STRIPPABLES.containsKey(state.getBlock()))
        {
            return AxeStrippableDummy.Medium.CUSTOM_STRIPPABLES.get(state.getBlock()).withPropertiesOf(state);
        }
        return null;
    }
}