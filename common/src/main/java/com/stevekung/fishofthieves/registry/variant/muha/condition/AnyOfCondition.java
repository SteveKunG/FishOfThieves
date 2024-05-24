package com.stevekung.fishofthieves.registry.variant.muha.condition;

import java.util.List;

import com.mojang.serialization.MapCodec;
import net.minecraft.Util;

public class AnyOfCondition extends CompositeSpawnCondition
{
    public static final MapCodec<AnyOfCondition> CODEC = createCodec(AnyOfCondition::new);

    AnyOfCondition(List<SpawnCondition> conditions)
    {
        super(conditions, Util.anyOf(conditions));
    }

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.ANY_OF;
    }

    public static AnyOfCondition.Builder anyOf(SpawnCondition.Builder... conditions)
    {
        return new AnyOfCondition.Builder(conditions);
    }

    public static class Builder extends CompositeSpawnCondition.Builder
    {
        public Builder(SpawnCondition.Builder... builders)
        {
            super(builders);
        }

        @Override
        public AnyOfCondition.Builder or(SpawnCondition.Builder builder)
        {
            this.addTerm(builder);
            return this;
        }

        @Override
        protected SpawnCondition create(List<SpawnCondition> conditions)
        {
            return new AnyOfCondition(conditions);
        }
    }
}