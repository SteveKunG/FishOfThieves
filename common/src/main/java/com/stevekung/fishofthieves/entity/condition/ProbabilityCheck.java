package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record ProbabilityCheck(float chance) implements SpawnCondition
{
    public static final MapCodec<ProbabilityCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(ProbabilityCheck::chance)).apply(instance, ProbabilityCheck::new));

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
        return new ProbabilityCheck(chance);
    }

    public static SpawnCondition defaultRareProbablity()
    {
        return new ProbabilityCheck(0.05f);
    }
}