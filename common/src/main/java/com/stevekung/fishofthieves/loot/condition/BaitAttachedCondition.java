package com.stevekung.fishofthieves.loot.condition;

import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record BaitAttachedCondition(ItemPredicate itemPredicate, EntityPredicate entityPredicate) implements LootItemCondition
{
    @Override
    public LootItemConditionType getType()
    {
        return FOTLootItemConditions.BAIT_ATTACHED_HOOK;
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams()
    {
        return Set.of(LootContextParams.THIS_ENTITY);
    }

    @Override
    public boolean test(LootContext context)
    {
        var entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        return entity != null && this.entityPredicate.matches(context.getLevel(), null, entity) && this.checkBaitFromHook(entity);
    }

    private boolean checkBaitFromHook(Entity entity)
    {
        if (entity instanceof FishingHook fishingHook)
        {
            var baitStack = fishingHook.fishofthieves$getBaitStack();
            return !baitStack.isEmpty() && this.itemPredicate.matches(baitStack);
        }
        return false;
    }

    public static LootItemCondition.Builder baitMatches(ItemPredicate.Builder itemPredicateBuilder, EntityPredicate.Builder entityPredicateBuilder)
    {
        return () -> new BaitAttachedCondition(itemPredicateBuilder.build(), entityPredicateBuilder.build());
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BaitAttachedCondition>
    {
        @Override
        public void serialize(JsonObject jsonObject, BaitAttachedCondition baitAttachedCondition, JsonSerializationContext jsonSerializationContext)
        {
            jsonObject.add("item_predicate", baitAttachedCondition.itemPredicate.serializeToJson());
            jsonObject.add("entity_predicate", baitAttachedCondition.entityPredicate.serializeToJson());
        }

        @Override
        public BaitAttachedCondition deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext)
        {
            var itemPredicate = ItemPredicate.fromJson(jsonObject.get("item_predicate"));
            var entityPredicate = EntityPredicate.fromJson(jsonObject.get("entity_predicate"));
            return new BaitAttachedCondition(itemPredicate, entityPredicate);
        }
    }
}