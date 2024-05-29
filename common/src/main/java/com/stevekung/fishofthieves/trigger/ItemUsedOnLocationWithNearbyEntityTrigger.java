package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
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
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, BlockPos pos, ItemStack stack, Entity entity)
    {
        var serverLevel = player.serverLevel();
        var blockState = serverLevel.getBlockState(pos);
        var lootParams = new LootParams.Builder(player.serverLevel()).withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.ORIGIN, pos.getCenter()).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.BLOCK_STATE, blockState).withParameter(LootContextParams.TOOL, stack).create(LootContextParamSets.ADVANCEMENT_LOCATION);
        var lootContext = new LootContext.Builder(lootParams).create(Optional.empty());
        this.trigger(player, triggerInstance -> triggerInstance.matches(lootContext));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), ExtraCodecs.strictOptionalField(ContextAwarePredicate.CODEC, "location").forGetter(TriggerInstance::location)).apply(instance, TriggerInstance::new));

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
        public void validate(CriterionValidator validator)
        {
            SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
            this.location.ifPresent(contextAwarePredicate -> validator.validate(contextAwarePredicate, LootContextParamSets.ADVANCEMENT_LOCATION, ".location"));
        }
    }
}