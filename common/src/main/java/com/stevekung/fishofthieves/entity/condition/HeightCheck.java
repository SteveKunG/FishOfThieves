package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record HeightCheck(MinMaxBounds.Ints height) implements SpawnCondition
{
    public static final MapCodec<HeightCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("height").forGetter(HeightCheck::height)).apply(instance, HeightCheck::new));

    @Override
    public MapCodec<HeightCheck> codec()
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
        return new HeightCheck(height);
    }
}