package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record MoonBrightnessCondition(float min, float max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<MoonBrightnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("min").forGetter(MoonBrightnessCondition::min),
            Codec.FLOAT.fieldOf("max").forGetter(MoonBrightnessCondition::max)
    ).apply(instance, MoonBrightnessCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.MOON_BRIGHTNESS;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().getMoonBrightness() >= this.min && context.level().getMoonBrightness() <= this.max;
    }

    public static Builder moonBrightness(float min, float max)
    {
        return () -> new MoonBrightnessCondition(min, max);
    }
}