package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.LivingEntity;

public record InvertedSpawnCondition(SpawnCondition term) implements SpawnCondition
{
    public static final MapCodec<InvertedSpawnCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(SpawnConditions.DIRECT_CODEC.fieldOf("term").forGetter(InvertedSpawnCondition::term)).apply(instance, InvertedSpawnCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.INVERTED;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        return !this.term.test(livingEntity);
    }

    public static SpawnCondition.Builder invert(SpawnCondition.Builder toInvert)
    {
        var invertedLootItemCondition = new InvertedSpawnCondition(toInvert.build());
        return () -> invertedLootItemCondition;
    }
}