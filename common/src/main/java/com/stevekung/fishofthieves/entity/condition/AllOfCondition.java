package com.stevekung.fishofthieves.entity.condition;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.Util;

public class AllOfCondition extends CompositeSpawnCondition
{
    public static final MapCodec<AllOfCondition> CODEC = createCodec(AllOfCondition::new);
    public static final Codec<AllOfCondition> INLINE_CODEC = createInlineCodec(AllOfCondition::new);

    AllOfCondition(List<SpawnCondition> conditions)
    {
        super(conditions, Util.allOf(conditions));
    }

    public static AllOfCondition allOf(List<SpawnCondition> conditions)
    {
        return new AllOfCondition(List.copyOf(conditions));
    }

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.ALL_OF;
    }

    public static AllOfCondition.Builder allOf(SpawnCondition.Builder... conditions)
    {
        return new AllOfCondition.Builder(conditions);
    }

    public static class Builder extends CompositeSpawnCondition.Builder
    {
        public Builder(SpawnCondition.Builder... builders)
        {
            super(builders);
        }

        @Override
        public AllOfCondition.Builder and(SpawnCondition.Builder builder)
        {
            this.addTerm(builder);
            return this;
        }

        @Override
        protected SpawnCondition create(List<SpawnCondition> conditions)
        {
            return new AllOfCondition(conditions);
        }
    }
}