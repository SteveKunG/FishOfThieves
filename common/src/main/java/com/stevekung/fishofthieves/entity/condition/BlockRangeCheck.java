package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public record BlockRangeCheck(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range) implements SpawnCondition
{
    public static final MapCodec<BlockRangeCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("blocks").forGetter(BlockRangeCheck::blocks),
            RegistryCodecs.homogeneousList(Registries.FLUID).optionalFieldOf("fluids").forGetter(BlockRangeCheck::fluids),
            Codec.intRange(1, 32).fieldOf("range").forGetter(BlockRangeCheck::range)
    ).apply(instance, BlockRangeCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        var level = context.level().getLevel();

        if (this.blocks.isPresent() && TerrainUtils.lookForBlock(level, context.pos(), this.range, blockPos2 -> level.getBlockState(blockPos2).is(this.blocks.get())).isPresent())
        {
            return true;
        }
        else
        {
            return this.fluids.isPresent() && TerrainUtils.lookForBlock(level, context.pos(), this.range, blockPos2 -> level.getFluidState(blockPos2).is(this.fluids.get()) && level.getFluidState(blockPos2).isSource()).isPresent();
        }
    }

    public static SpawnCondition blocksInRange(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range)
    {
        return new BlockRangeCheck(blocks, fluids, range);
    }
}