package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

public record SkyBrightnessCondition(int min, int max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<SkyBrightnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("min").forGetter(SkyBrightnessCondition::min),
            Codec.INT.fieldOf("max").forGetter(SkyBrightnessCondition::max)
    ).apply(instance, SkyBrightnessCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SKY_BRIGHTNESS;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return livingEntity.level().getBrightness(LightLayer.SKY, livingEntity.blockPosition()) >= this.min && livingEntity.level().getBrightness(LightLayer.SKY, livingEntity.blockPosition()) <= this.max;
    }

    public static Builder skyBrightness(int min, int max)
    {
        return () -> new SkyBrightnessCondition(min, max);
    }
}