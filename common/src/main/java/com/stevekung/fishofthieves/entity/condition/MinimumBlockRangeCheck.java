package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public record MinimumBlockRangeCheck(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range, int size) implements SpawnCondition
{
    public static final MapCodec<MinimumBlockRangeCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.holderSet(Registries.BLOCK).optionalFieldOf("blocks").forGetter(MinimumBlockRangeCheck::blocks),
            RegistryCodecs.holderSet(Registries.FLUID).optionalFieldOf("fluids").forGetter(MinimumBlockRangeCheck::fluids),
            Codec.intRange(1, 32).fieldOf("range").forGetter(MinimumBlockRangeCheck::range),
            Codec.intRange(1, 64).fieldOf("size").forGetter(MinimumBlockRangeCheck::size)
    ).apply(instance, MinimumBlockRangeCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        var level = context.level().getLevel();

        if (this.blocks.isPresent() && TerrainUtils.lookForBlocksWithSize(context.pos(), this.range, this.size, blockPos2 -> level.getBlockState(blockPos2).is(this.blocks.get())))
        {
            return true;
        }
        else
        {
            return this.fluids.isPresent() && TerrainUtils.lookForBlocksWithSize(context.pos(), this.range, this.size, blockPos2 -> level.getFluidState(blockPos2).is(this.fluids.get()) && level.getFluidState(blockPos2).isSource());
        }
    }

    public static SpawnCondition minimumBlocksInRange(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range, int size)
    {
        return new MinimumBlockRangeCheck(blocks, fluids, range, size);
    }
}