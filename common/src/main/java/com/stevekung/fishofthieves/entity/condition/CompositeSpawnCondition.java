package com.stevekung.fishofthieves.entity.condition;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public abstract class CompositeSpawnCondition implements SpawnCondition
{
    protected final List<SpawnCondition> terms;
    private final Predicate<SpawnContext> composedPredicate;

    protected CompositeSpawnCondition(List<SpawnCondition> terms, Predicate<SpawnContext> composedPredicate)
    {
        this.terms = terms;
        this.composedPredicate = composedPredicate;
    }

    protected static <T extends CompositeSpawnCondition> MapCodec<T> createCodec(Function<List<SpawnCondition>, T> factory)
    {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(SpawnCondition.CODEC.listOf().fieldOf("terms").forGetter(compositeSpawnCondition -> compositeSpawnCondition.terms)).apply(instance, factory));
    }

    @Override
    public final boolean test(SpawnContext context)
    {
        return this.composedPredicate.test(context);
    }

    public abstract static class Builder
    {
        private final ImmutableList.Builder<SpawnCondition> terms = ImmutableList.builder();

        protected Builder(SpawnCondition... conditions)
        {
            for (var builder : conditions)
            {
                this.terms.add(builder);
            }
        }

        public void addTerm(SpawnCondition condition)
        {
            this.terms.add(condition);
        }

        public SpawnCondition build()
        {
            return this.create(this.terms.build());
        }

        protected abstract SpawnCondition create(List<SpawnCondition> conditions);
    }
}