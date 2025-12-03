package com.stevekung.fishofthieves.trigger;

import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class FollowLivingWithEffectTrigger extends SimpleCriterionTrigger<FollowLivingWithEffectTrigger.TriggerInstance>
{
    static final ResourceLocation ID = FishOfThieves.id("follow_living_with_effect");

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext)
    {
        var sourceEntity = EntityPredicate.fromJson(json, "source_entity", deserializationContext);
        var mobEffectsPredicate = MobEffectsPredicate.fromJson(json.get("effects"));
        return new TriggerInstance(contextAwarePredicate, sourceEntity, mobEffectsPredicate);
    }

    public void trigger(ServerPlayer player, Entity sourceEntity)
    {
        var lootContext = EntityPredicate.createContext(player, sourceEntity);
        this.trigger(player, triggerInstance -> triggerInstance.matches(lootContext, player));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final ContextAwarePredicate sourceEntity;
        private final MobEffectsPredicate effects;

        public TriggerInstance(ContextAwarePredicate player, ContextAwarePredicate sourceEntity, MobEffectsPredicate effects)
        {
            super(ID, player);
            this.sourceEntity = sourceEntity;
            this.effects = effects;
        }

        public static TriggerInstance entityWithEffect(EntityPredicate entityPredicate, MobEffectsPredicate mobEffectsPredicate)
        {
            return new TriggerInstance(ContextAwarePredicate.ANY, EntityPredicate.wrap(entityPredicate), mobEffectsPredicate);
        }

        public boolean matches(LootContext lootContext, Entity sourceEntity)
        {
            return this.sourceEntity.matches(lootContext) && this.effects.matches(sourceEntity);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context)
        {
            var jsonObject = super.serializeToJson(context);
            jsonObject.add("source_entity", this.sourceEntity.toJson(context));
            jsonObject.add("effects", this.effects.serializeToJson());
            return jsonObject;
        }
    }
}