package com.stevekung.fishofthieves.forge.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import com.stevekung.fishofthieves.block.CoconutSaplingBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

@Mixin(CoconutSaplingBlock.class)
public abstract class MixinCoconutSaplingBlock implements IPlantable
{
    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos)
    {
        return PlantType.DESERT;
    }
}