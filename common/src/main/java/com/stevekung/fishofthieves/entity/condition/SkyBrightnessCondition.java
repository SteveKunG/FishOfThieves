package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.LightLayer;

public record SkyBrightnessCondition(MinMaxBounds.Ints brightness) implements SpawnCondition
{
    public static final MapCodec<SkyBrightnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("brightness").forGetter(SkyBrightnessCondition::brightness)).apply(instance, SkyBrightnessCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SKY_BRIGHTNESS;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return this.brightness.matches(context.level().getBrightness(LightLayer.SKY, context.blockPos()));
    }

    public static Builder skyBrightness(MinMaxBounds.Ints brightness)
    {
        return () -> new SkyBrightnessCondition(brightness);
    }
}