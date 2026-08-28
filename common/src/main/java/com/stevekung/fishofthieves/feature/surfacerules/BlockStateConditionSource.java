package com.stevekung.fishofthieves.feature.surfacerules;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;
import net.minecraft.world.level.levelgen.material.condition.ConditionEvaluator;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;

public record BlockStateConditionSource(BlockState blockState, int offset) implements MaterialCondition
{
    public static final MapCodec<BlockStateConditionSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BlockState.CODEC.fieldOf("block_state").forGetter(BlockStateConditionSource::blockState), Codec.INT.fieldOf("offset").forGetter(BlockStateConditionSource::offset)).apply(instance, BlockStateConditionSource::new));

    public BlockStateConditionSource(BlockState blockState, int offset)
    {
        this.blockState = blockState;
        this.offset = offset;
        Objects.requireNonNull(this.blockState);
    }

    @Override
    public MapCodec<? extends MaterialCondition> codec()
    {
        return BlockStateConditionSource.CODEC;
    }

    @Override
    public ConditionEvaluator compile(MaterialRuleContext context)
    {
        return () -> context.getChunkAccess().getBlockState(context.pos.offset(0, this.offset, 0)).is(this.blockState.getBlock());
    }
}