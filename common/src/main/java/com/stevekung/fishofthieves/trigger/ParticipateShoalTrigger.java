package com.stevekung.fishofthieves.trigger;

import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ParticipateShoalTrigger extends SimpleCriterionTrigger<ParticipateShoalTrigger.TriggerInstance>
{
    static final ResourceLocation ID = FishOfThieves.id("participate_shoal");

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public ParticipateShoalTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext)
    {
        return new ParticipateShoalTrigger.TriggerInstance(predicate);
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, triggerInstance -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        public TriggerInstance(ContextAwarePredicate player)
        {
            super(ParticipateShoalTrigger.ID, player);
        }

        public static TriggerInstance participateShoal()
        {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }
    }
}