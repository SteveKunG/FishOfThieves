package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record InvertedSpawnCondition(SpawnCondition term) implements SpawnCondition
{
    public static final MapCodec<InvertedSpawnCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(FOTSpawnConditions.DIRECT_CODEC.fieldOf("term").forGetter(InvertedSpawnCondition::term)).apply(instance, InvertedSpawnCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.INVERTED;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return !this.term.test(context);
    }

    public static SpawnCondition.Builder invert(SpawnCondition.Builder toInvert)
    {
        var invertedLootItemCondition = new InvertedSpawnCondition(toInvert.build());
        return () -> invertedLootItemCondition;
    }
}