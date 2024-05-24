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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public record MatchBlocksInRangeCondition(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<MatchBlocksInRangeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("blocks").forGetter(MatchBlocksInRangeCondition::blocks),
            RegistryCodecs.homogeneousList(Registries.FLUID).optionalFieldOf("fluids").forGetter(MatchBlocksInRangeCondition::fluids),
            Codec.INT.fieldOf("range").forGetter(MatchBlocksInRangeCondition::range)
    ).apply(instance, MatchBlocksInRangeCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.MATCH_BLOCKS_IN_RANGE;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        var level = livingEntity.level();

        if (this.blocks.isPresent() && TerrainUtils.lookForBlock(livingEntity.blockPosition(), this.range, blockPos2 -> level.getBlockState(blockPos2).is(this.blocks.get())).isPresent())
        {
            return true;
        }
        else
        {
            return this.fluids.isPresent() && TerrainUtils.lookForBlock(livingEntity.blockPosition(), this.range, blockPos2 -> level.getFluidState(blockPos2).is(this.fluids.get()) && level.getFluidState(blockPos2).isSource()).isPresent();
        }
    }

    public static Builder hasBlocksInRange(Optional<HolderSet<Block>> blocks, Optional<HolderSet<Fluid>> fluids, int range)
    {
        return () -> new MatchBlocksInRangeCondition(blocks, fluids, range);
    }
}