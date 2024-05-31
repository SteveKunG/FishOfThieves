package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record HeightCondition(Optional<Integer> min, Optional<Integer> max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<HeightCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.optionalFieldOf("min").forGetter(HeightCondition::min),
            Codec.INT.optionalFieldOf("max").forGetter(HeightCondition::max)
    ).apply(instance, HeightCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.HEIGHT;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.blockPos().getY() >= this.min.orElse(context.level().getMinBuildHeight()) && context.blockPos().getY() <= this.max.orElse(context.level().getMaxBuildHeight());
    }

    public static HeightCondition.Builder height(Optional<Integer> min, Optional<Integer> max)
    {
        return () -> new HeightCondition(min, max);
    }
}