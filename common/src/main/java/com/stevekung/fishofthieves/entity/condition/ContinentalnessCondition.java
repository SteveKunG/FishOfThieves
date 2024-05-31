package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.TerrainUtils;

public record ContinentalnessCondition(Continentalness continentalness) implements SpawnCondition
{
    public static final MapCodec<ContinentalnessCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Continentalness.CODEC.fieldOf("continentalness").forGetter(ContinentalnessCondition::continentalness)).apply(instance, ContinentalnessCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.CONTINENTALNESS;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        var continentalness = TerrainUtils.getContinentalness(context.level(), context.blockPos());
        return continentalness == this.continentalness;
    }

    public static ContinentalnessCondition.Builder builder()
    {
        return new ContinentalnessCondition.Builder();
    }

    public static class Builder implements SpawnCondition.Builder
    {
        private Continentalness continentalness = Continentalness.OCEAN;

        public ContinentalnessCondition.Builder continentalness(Continentalness continentalness)
        {
            this.continentalness = continentalness;
            return this;
        }

        @Override
        public ContinentalnessCondition build()
        {
            return new ContinentalnessCondition(this.continentalness);
        }
    }
}