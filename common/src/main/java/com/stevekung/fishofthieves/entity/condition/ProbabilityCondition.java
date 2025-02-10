package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record ProbabilityCondition(float chance) implements SpawnCondition
{
    public static final MapCodec<ProbabilityCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(ProbabilityCondition::chance)).apply(instance, ProbabilityCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return context.level().getRandom().nextFloat() < this.chance;
    }

    public static SpawnCondition chance(float chance)
    {
        return new ProbabilityCondition(chance);
    }

    public static SpawnCondition defaultRareProbablity()
    {
        return new ProbabilityCondition(0.05f);
    }
}