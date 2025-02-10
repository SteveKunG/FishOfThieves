package com.stevekung.fishofthieves.entity.condition;

import java.util.List;

import com.mojang.serialization.MapCodec;

import net.minecraft.Util;
import net.minecraft.world.entity.variant.SpawnCondition;

public class AllOfCondition extends CompositeSpawnCondition
{
    public static final MapCodec<AllOfCondition> CODEC = createCodec(AllOfCondition::new);

    AllOfCondition(List<SpawnCondition> conditions)
    {
        super(conditions, Util.allOf(conditions));
    }

    public static AllOfCondition allOf(SpawnCondition... conditions)
    {
        return new AllOfCondition(List.of(conditions));
    }

    public static AllOfCondition allOf(List<SpawnCondition> conditions)
    {
        return new AllOfCondition(List.copyOf(conditions));
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

        public AllOfCondition.Builder and(SpawnCondition builder)
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