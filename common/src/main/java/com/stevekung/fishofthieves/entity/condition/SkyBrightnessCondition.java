package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.level.LightLayer;

public record SkyBrightnessCondition(int min, int max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<SkyBrightnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.intRange(0, 15).fieldOf("min").forGetter(SkyBrightnessCondition::min),
            Codec.intRange(0, 15).fieldOf("max").forGetter(SkyBrightnessCondition::max)
    ).apply(instance, SkyBrightnessCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SKY_BRIGHTNESS;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().getBrightness(LightLayer.SKY, context.blockPos()) >= this.min && context.level().getBrightness(LightLayer.SKY, context.blockPos()) <= this.max;
    }

    public static Builder skyBrightness(int min, int max)
    {
        return () -> new SkyBrightnessCondition(min, max);
    }
}