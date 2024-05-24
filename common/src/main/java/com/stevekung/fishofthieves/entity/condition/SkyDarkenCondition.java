package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.entity.LivingEntity;

public record SkyDarkenCondition(int min, int max) implements SpawnCondition
{
    //@formatter:off
    public static final MapCodec<SkyDarkenCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("min").forGetter(SkyDarkenCondition::min),
            Codec.INT.fieldOf("max").forGetter(SkyDarkenCondition::max)
    ).apply(instance, SkyDarkenCondition::new));
    //@formatter:on

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SKY_DARKEN;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return livingEntity.level().getSkyDarken() >= this.min && livingEntity.level().getSkyDarken() <= this.max;
    }

    public static Builder skyDarken(int min, int max)
    {
        return () -> new SkyDarkenCondition(min, max);
    }
}