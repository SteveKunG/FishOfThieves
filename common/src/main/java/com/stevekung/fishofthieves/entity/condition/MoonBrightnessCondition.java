package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.advancements.critereon.MinMaxBounds;

public record MoonBrightnessCondition(MinMaxBounds.Doubles brightness) implements SpawnCondition
{
    public static final MapCodec<MoonBrightnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Doubles.CODEC.fieldOf("brightness").forGetter(MoonBrightnessCondition::brightness)).apply(instance, MoonBrightnessCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.MOON_BRIGHTNESS;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return this.brightness.matches(context.level().getMoonBrightness());
    }

    public static Builder moonBrightness(MinMaxBounds.Doubles brightness)
    {
        return () -> new MoonBrightnessCondition(brightness);
    }
}