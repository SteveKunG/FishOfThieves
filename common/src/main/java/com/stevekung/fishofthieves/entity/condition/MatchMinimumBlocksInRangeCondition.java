package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public record MatchMinimumBlocksInRangeCondition(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range, int size) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<MatchMinimumBlocksInRangeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("blocks").forGetter(MatchMinimumBlocksInRangeCondition::blocks),
            RegistryCodecs.homogeneousList(Registries.FLUID).optionalFieldOf("fluids").forGetter(MatchMinimumBlocksInRangeCondition::fluids),
            Codec.INT.fieldOf("range").forGetter(MatchMinimumBlocksInRangeCondition::range),
            Codec.INT.fieldOf("size").forGetter(MatchMinimumBlocksInRangeCondition::size)
    ).apply(instance, MatchMinimumBlocksInRangeCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.MATCH_MINIMUM_BLOCKS_IN_RANGE;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        var level = context.level();

        if (this.blocks.isPresent() && TerrainUtils.lookForBlocksWithSize(context.blockPos(), this.range, this.size, blockPos2 -> level.getBlockState(blockPos2).is(this.blocks.get())))
        {
            return true;
        }
        else
        {
            return this.fluids.isPresent() && TerrainUtils.lookForBlocksWithSize(context.blockPos(), this.range, this.size, blockPos2 -> level.getFluidState(blockPos2).is(this.fluids.get()) && level.getFluidState(blockPos2).isSource());
        }
    }

    public static Builder minimumBlocksInRange(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range, int size)
    {
        return () -> new MatchMinimumBlocksInRangeCondition(blocks, fluids, range, size);
    }
}