package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record HeightCondition(MinMaxBounds.Ints height) implements SpawnCondition
{
    public static final MapCodec<HeightCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("height").forGetter(HeightCondition::height)).apply(instance, HeightCondition::new));

    @Override
    public MapCodec<HeightCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return this.height.matches(context.pos().getY());
    }

    public static SpawnCondition height(MinMaxBounds.Ints height)
    {
        return new HeightCondition(height);
    }
}