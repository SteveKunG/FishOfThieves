package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class FollowLivingWithEffectTrigger extends SimpleCriterionTrigger<FollowLivingWithEffectTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Entity sourceEntity)
    {
        this.trigger(player, triggerInstance -> triggerInstance.matches(player, sourceEntity));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<EntityPredicate> sourceEntity, Optional<MobEffectsPredicate> effects) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance
                .group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        EntityPredicate.CODEC.optionalFieldOf("source_entity").forGetter(TriggerInstance::sourceEntity),
                        MobEffectsPredicate.CODEC.optionalFieldOf("effects").forGetter(TriggerInstance::effects)
                ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> entityWithEffect(EntityPredicate.Builder entityPredicate, MobEffectsPredicate.Builder mobEffects)
        {
            return FOTCriteriaTriggers.FOLLOW_LIVING_WITH_EFFECT.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(entityPredicate.build()), mobEffects.build()));
        }

        public boolean matches(ServerPlayer serverPlayer, Entity sourceEntity)
        {
            return (this.sourceEntity.isEmpty() || this.sourceEntity.get().matches(serverPlayer, sourceEntity)) && (this.effects.isEmpty() || this.effects.get().matches(serverPlayer));
        }
    }
}