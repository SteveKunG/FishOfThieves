package com.stevekung.fishofthieves.feature.surfacerules;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.mixin.accessor.MaterialRuleContextAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;
import net.minecraft.world.level.levelgen.material.condition.ConditionEvaluator;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;

public class BlockStateConditionSource implements MaterialCondition
{
    public static final MapCodec<BlockStateConditionSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.fieldOf("block_state").forGetter(BlockStateConditionSource::blockState),
            Codec.INT.fieldOf("offset").forGetter(BlockStateConditionSource::offset)
    ).apply(instance, BlockStateConditionSource::new));

    private final BlockState blockState;
    private final int offset;
    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

    public BlockStateConditionSource(BlockState blockState, int offset)
    {
        this.blockState = blockState;
        this.offset = offset;
    }

    public BlockState blockState()
    {
        return this.blockState;
    }

    public int offset()
    {
        return this.offset;
    }

    @Override
    public MapCodec<? extends MaterialCondition> codec()
    {
        return BlockStateConditionSource.CODEC;
    }

    @Override
    public ConditionEvaluator compile(MaterialRuleContext context)
    {
        return () ->
        {
            var accessor = ((MaterialRuleContextAccessor) (Object) context);
            var pos = this.mutablePos.set(accessor.getBlockX(), accessor.getBlockY() + this.offset, accessor.getBlockZ());
            return context.getChunkAccess().getBlockState(pos).is(this.blockState.getBlock());
        };
    }
}