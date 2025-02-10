package com.stevekung.fishofthieves.entity.condition;

import java.util.List;

import com.mojang.serialization.MapCodec;

import net.minecraft.Util;
import net.minecraft.world.entity.variant.SpawnCondition;

public class AllConditionCheck extends CompositeSpawnCondition
{
    public static final MapCodec<AllConditionCheck> CODEC = createCodec(AllConditionCheck::new);

    AllConditionCheck(List<SpawnCondition> conditions)
    {
        super(conditions, Util.allOf(conditions));
    }

    public static AllConditionCheck allOf(SpawnCondition... conditions)
    {
        return new AllConditionCheck(List.of(conditions));
    }

    public static AllConditionCheck allOf(List<SpawnCondition> conditions)
    {
        return new AllConditionCheck(List.copyOf(conditions));
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    public static class Builder extends CompositeSpawnCondition.Builder
    {
        public Builder(SpawnCondition... builders)
        {
            super(builders);
        }

        public AllConditionCheck.Builder and(SpawnCondition builder)
        {
            this.addTerm(builder);
            return this;
        }

        @Override
        protected SpawnCondition create(List<SpawnCondition> conditions)
        {
            return new AllConditionCheck(conditions);
        }
    }
}