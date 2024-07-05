package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.advancements.critereon.MinMaxBounds;

public record HeightCondition(MinMaxBounds.Ints height) implements SpawnCondition
{
    public static final MapCodec<HeightCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MinMaxBounds.Ints.CODEC.fieldOf("height").forGetter(HeightCondition::height)).apply(instance, HeightCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.HEIGHT;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return this.height.matches(context.blockPos().getY());
    }

    public static HeightCondition.Builder height(MinMaxBounds.Ints height)
    {
        return () -> new HeightCondition(height);
    }
}