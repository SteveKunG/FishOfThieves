package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record ProbabilityCondition(float chance) implements SpawnCondition
{
    public static final MapCodec<ProbabilityCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(ProbabilityCondition::chance)).apply(instance, ProbabilityCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.PROBABILITY;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.random().nextFloat() < this.chance;
    }

    public static SpawnCondition.Builder chance(float chance)
    {
        return () -> new ProbabilityCondition(chance);
    }

    public static SpawnCondition.Builder defaultRareProbablity()
    {
        return () -> new ProbabilityCondition(0.01f);
    }
}
