package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record HeightCondition(int min, int max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<HeightCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("min").forGetter(HeightCondition::min),
            Codec.INT.fieldOf("max").forGetter(HeightCondition::max)
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
        return context.blockPos().getY() >= this.min && context.blockPos().getY() <= this.max;
    }

    public static HeightCondition.Builder height(int min, int max)
    {
        return () -> new HeightCondition(min, max);
    }
}