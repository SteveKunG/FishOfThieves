package com.stevekung.fishofthieves.entity.condition;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

public record SeeSkyCondition(Optional<Boolean> belowWater) implements SpawnCondition
{
    public static final MapCodec<SeeSkyCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("below_water").forGetter(SeeSkyCondition::belowWater)).apply(instance, SeeSkyCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.SEE_SKY;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return this.belowWater.isPresent() ? context.level().canSeeSkyFromBelowWater(context.blockPos()) : context.level().canSeeSky(context.blockPos());
    }

    public static SpawnCondition.Builder seeSky()
    {
        return () -> new SeeSkyCondition(Optional.empty());
    }

    public static SpawnCondition.Builder seeSkyBelowWater()
    {
        return () -> new SeeSkyCondition(Optional.of(true));
    }
}