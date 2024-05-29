package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class ItemUsedOnLocationWithNearbyEntityTrigger extends SimpleCriterionTrigger<ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance>
{
    @Override
    public ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext deserializationContext)
    {
        var optional = ContextAwarePredicate.fromElement("location", deserializationContext, json.get("location"), LootContextParamSets.ADVANCEMENT_LOCATION);

        if (optional.isEmpty())
        {
            throw new JsonParseException("Failed to parse 'location' field");
        }
        else
        {
            return new ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance(player, optional.get());
        }
    }

    public void trigger(ServerPlayer player, BlockPos pos, ItemStack stack, Entity entity)
    {
        var serverLevel = player.serverLevel();
        var blockState = serverLevel.getBlockState(pos);
        var lootParams = new LootParams.Builder(player.serverLevel()).withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.ORIGIN, pos.getCenter()).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.BLOCK_STATE, blockState).withParameter(LootContextParams.TOOL, stack).create(LootContextParamSets.ADVANCEMENT_LOCATION);
        var lootContext = new LootContext.Builder(lootParams).create(Optional.empty());
        this.trigger(player, triggerInstance -> triggerInstance.matches(lootContext));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final Optional<ContextAwarePredicate> location;

        public TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location)
        {
            super(player);
            this.location = location;
        }

        private static ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance itemUsedOnLocation(LocationPredicate.Builder location, ItemPredicate.Builder tool, EntityPredicate.Builder entity)
        {
            var contextAwarePredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(location).build(), MatchTool.toolMatches(tool).build(), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, entity).build());
            return new ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance(Optional.empty(), Optional.of(contextAwarePredicate));
        }

        public static Criterion<ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance> itemUsedOnBlock(LocationPredicate.Builder location, ItemPredicate.Builder tool, EntityPredicate.Builder entity)
        {
            return FOTCriteriaTriggers.ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY.createCriterion(itemUsedOnLocation(location, tool, entity));
        }

        public boolean matches(LootContext context)
        {
            return this.location.isEmpty() || this.location.get().matches(context);
        }

        @Override
        public JsonObject serializeToJson()
        {
            var jsonObject = super.serializeToJson();
            this.location.ifPresent(contextAwarePredicate -> jsonObject.add("location", contextAwarePredicate.toJson()));
            return jsonObject;
        }
    }
}
