package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.LivingEntity;

public record ProbabilityCondition(float chance) implements SpawnCondition
{
    public static final MapCodec<ProbabilityCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.FLOAT.fieldOf("chance").forGetter(ProbabilityCondition::chance)).apply(instance, ProbabilityCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.PROBABILITY;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return livingEntity.getRandom().nextFloat() < this.chance;
    }

    public static SpawnCondition.Builder chance(float chance)
    {
        return () -> new ProbabilityCondition(chance);
    }

    public static SpawnCondition.Builder defaultRareProbablity()
    {
        return () -> new ProbabilityCondition(0.1f);
    }
}
