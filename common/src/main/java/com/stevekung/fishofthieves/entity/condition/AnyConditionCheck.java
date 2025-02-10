package com.stevekung.fishofthieves.entity.condition;

import java.util.List;

import com.mojang.serialization.MapCodec;

import net.minecraft.Util;
import net.minecraft.world.entity.variant.SpawnCondition;

public class AnyConditionCheck extends CompositeSpawnCondition
{
    public static final MapCodec<AnyConditionCheck> CODEC = createCodec(AnyConditionCheck::new);

    AnyConditionCheck(List<SpawnCondition> conditions)
    {
        super(conditions, Util.anyOf(conditions));
    }

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    public static AnyConditionCheck.Builder anyOf(SpawnCondition... conditions)
    {
        return new AnyConditionCheck.Builder(conditions);
    }

    public static class Builder extends CompositeSpawnCondition.Builder
    {
        public Builder(SpawnCondition... builders)
        {
            super(builders);
        }

        public AnyConditionCheck.Builder or(SpawnCondition builder)
        {
            this.addTerm(builder);
            return this;
        }

        @Override
        protected SpawnCondition create(List<SpawnCondition> conditions)
        {
            return new AnyConditionCheck(conditions);
        }
    }
}