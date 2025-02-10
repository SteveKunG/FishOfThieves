package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record InvertedCondition(SpawnCondition term) implements SpawnCondition
{
    public static final MapCodec<InvertedCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(SpawnCondition.CODEC.fieldOf("term").forGetter(InvertedCondition::term)).apply(instance, InvertedCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return !this.term.test(context);
    }

    public static SpawnCondition invert(SpawnCondition toInvert)
    {
        return new InvertedCondition(toInvert);
    }
}