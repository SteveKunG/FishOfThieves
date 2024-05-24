package com.stevekung.fishofthieves.entity.condition;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.world.entity.LivingEntity;

public abstract class CompositeSpawnCondition implements SpawnCondition
{
    protected final List<SpawnCondition> terms;
    private final Predicate<LivingEntity> composedPredicate;

    protected CompositeSpawnCondition(List<SpawnCondition> terms, Predicate<LivingEntity> composedPredicate)
    {
        this.terms = terms;
        this.composedPredicate = composedPredicate;
    }

    protected static <T extends CompositeSpawnCondition> MapCodec<T> createCodec(Function<List<SpawnCondition>, T> factory)
    {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(FOTSpawnConditions.DIRECT_CODEC.listOf().fieldOf("terms").forGetter(compositeSpawnCondition -> compositeSpawnCondition.terms)).apply(instance, factory));
    }

    protected static <T extends CompositeSpawnCondition> Codec<T> createInlineCodec(Function<List<SpawnCondition>, T> factory)
    {
        return FOTSpawnConditions.DIRECT_CODEC.listOf().xmap(factory, compositeSpawnCondition -> compositeSpawnCondition.terms);
    }

    @Override
    public final boolean test(LivingEntity context)
    {
        return this.composedPredicate.test(context);
    }

    public abstract static class Builder implements SpawnCondition.Builder
    {
        private final ImmutableList.Builder<SpawnCondition> terms = ImmutableList.builder();

        protected Builder(SpawnCondition.Builder... conditions)
        {
            for (var builder : conditions)
            {
                this.terms.add(builder.build());
            }
        }

        public void addTerm(SpawnCondition.Builder condition)
        {
            this.terms.add(condition.build());
        }

        @Override
        public SpawnCondition build()
        {
            return this.create(this.terms.build());
        }

        protected abstract SpawnCondition create(List<SpawnCondition> conditions);
    }
}