package com.stevekung.fishofthieves.feature.surfacerules;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class BlockStateConditionSource extends SurfaceRules implements SurfaceRules.ConditionSource
{
    public static final MapCodec<BlockStateConditionSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.fieldOf("block_state").forGetter(BlockStateConditionSource::blockState),
            Codec.INT.fieldOf("offset").forGetter(BlockStateConditionSource::offset)
    ).apply(instance, BlockStateConditionSource::new));

    private final BlockState blockState;
    private final int offset;

    public BlockStateConditionSource(BlockState blockState, int offset)
    {
        this.blockState = blockState;
        this.offset = offset;
        Objects.requireNonNull(this.blockState);
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
    public MapCodec<? extends ConditionSource> codec()
    {
        return BlockStateConditionSource.CODEC;
    }

    @Override
    public Condition apply(Context context)
    {
        return () -> context.chunk.getBlockState(context.pos.offset(0, this.offset, 0)).is(this.blockState.getBlock());
    }
}