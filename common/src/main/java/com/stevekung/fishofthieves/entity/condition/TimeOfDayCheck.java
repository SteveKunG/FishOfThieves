package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record TimeOfDayCheck(MinMaxBounds.Doubles timeOfDay) implements SpawnCondition
{
    public static final MapCodec<TimeOfDayCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Doubles.CODEC.fieldOf("time_of_day").forGetter(TimeOfDayCheck::timeOfDay)).apply(instance, TimeOfDayCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return this.timeOfDay.matches(context.level().getTimeOfDay(1.0F));
    }

    public static SpawnCondition timeOfDay(MinMaxBounds.Doubles timeOfDay)
    {
        return new TimeOfDayCheck(timeOfDay);
    }
}