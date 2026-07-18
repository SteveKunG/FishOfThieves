package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.phys.Vec3;

public class ItemUsedOnLocationWithNearbyEntityTrigger extends SimpleCriterionTrigger<ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, BlockPos pos, ItemStack stack, Entity entity)
    {
        var serverLevel = player.level();
        var blockState = serverLevel.getBlockState(pos);
        var lootParams = new LootParams.Builder(serverLevel).withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.BLOCK_STATE, blockState).withParameter(LootContextParams.TOOL, stack).create(LootContextParamSets.ADVANCEMENT_LOCATION);
        var lootContext = new LootContext.Builder(lootParams).create(Optional.empty());
        this.trigger(player, triggerInstance -> triggerInstance.matches(lootContext));
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player, Optional<Holder<LootItemCondition>> location) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player), LootItemCondition.CODEC.optionalFieldOf("location").forGetter(TriggerInstance::location)).apply(instance, TriggerInstance::new));

        private static ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance itemUsedOnLocation(LocationPredicate.Builder location, ItemPredicate.Builder tool, EntityPredicate.Builder entity)
        {
            var predicate = Holder.direct(AllOfCondition.allOf(LocationCheck.checkLocation(location), MatchTool.toolMatches(tool), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, entity)).build());
            return new ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance(Optional.empty(), Optional.of(predicate));
        }

        public static Criterion<TriggerInstance> itemUsedOnBlock(LocationPredicate.Builder location, ItemPredicate.Builder tool, EntityPredicate.Builder entity)
        {
            return FOTCriteriaTriggers.ITEM_USED_ON_LOCATION_WITH_NEARBY_ENTITY.createCriterion(itemUsedOnLocation(location, tool, entity));
        }

        public boolean matches(LootContext context)
        {
            return this.location.isEmpty() || this.location.get().value().test(context);
        }

        @Override
        public void validate(ValidationContextSource validator)
        {
            SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
            Validatable.validateHolder(validator.context(LootContextParamSets.ADVANCEMENT_LOCATION), "location", this.location);
        }
    }
}