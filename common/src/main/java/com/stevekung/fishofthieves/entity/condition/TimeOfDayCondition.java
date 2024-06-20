package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record TimeOfDayCondition(float min, float max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<TimeOfDayCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.floatRange(0.0f, 1.0f).fieldOf("min").forGetter(TimeOfDayCondition::min),
            Codec.floatRange(0.0f, 1.0f).fieldOf("max").forGetter(TimeOfDayCondition::max)
    ).apply(instance, TimeOfDayCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.TIME_OF_DAY;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        var time = context.level().getTimeOfDay(1.0F);
        return time >= this.min && time <= this.max;
    }

    public static Builder timeOfDay(float min, float max)
    {
        return () -> new TimeOfDayCondition(min, max);
    }
}