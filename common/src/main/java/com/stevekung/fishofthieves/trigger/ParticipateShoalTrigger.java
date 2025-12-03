package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class ParticipateShoalTrigger extends SimpleCriterionTrigger<ParticipateShoalTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, triggerInstance -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance
                .group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> participateShoal()
        {
            return FOTCriteriaTriggers.PARTICIPATE_SHOAL.createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}