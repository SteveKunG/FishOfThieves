package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class FollowLivingWithEffectTrigger extends SimpleCriterionTrigger<FollowLivingWithEffectTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Entity sourceEntity)
    {
        var lootContext = EntityPredicate.createContext(player, sourceEntity);
        this.trigger(player, triggerInstance -> triggerInstance.matches(lootContext, player));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MobEffectsPredicate> effects) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance
                .group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MobEffectsPredicate.CODEC.optionalFieldOf("effects").forGetter(TriggerInstance::effects)
                ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> entityWithEffect(EntityPredicate.Builder entityPredicate, MobEffectsPredicate.Builder mobEffects)
        {
            return FOTCriteriaTriggers.FOLLOW_LIVING_WITH_EFFECT.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap(entityPredicate)), mobEffects.build()));
        }

        public boolean matches(LootContext lootContext, Entity sourceEntity)
        {
            return (this.player.isEmpty() || this.player.get().matches(lootContext)) && (this.effects.isEmpty() || this.effects.get().matches(sourceEntity));
        }
    }
}