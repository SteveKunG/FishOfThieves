package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.advancements.critereon.MinMaxBounds;

public record TimeOfDayCondition(MinMaxBounds.Doubles timeOfDay) implements SpawnCondition
{
    public static final MapCodec<TimeOfDayCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Doubles.CODEC.fieldOf("time_of_day").forGetter(TimeOfDayCondition::timeOfDay)).apply(instance, TimeOfDayCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.TIME_OF_DAY;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return this.timeOfDay.matches(context.level().getTimeOfDay(1.0F));
    }

    public static Builder timeOfDay(MinMaxBounds.Doubles timeOfDay)
    {
        return () -> new TimeOfDayCondition(timeOfDay);
    }
}