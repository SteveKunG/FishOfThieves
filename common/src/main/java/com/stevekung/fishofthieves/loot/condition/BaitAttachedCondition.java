package com.stevekung.fishofthieves.loot.condition;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
    public static final MapCodec<BaitAttachedCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(
                    ItemPredicate.CODEC.fieldOf("item").forGetter(BaitAttachedCondition::itemPredicate),
                    EntityPredicate.CODEC.fieldOf("entity").forGetter(BaitAttachedCondition::entityPredicate))
            .apply(instance, BaitAttachedCondition::new));

    @Override
    public LootItemConditionType getType()
    {
        return FOTLootItemConditions.BAIT_ATTACHED_HOOK;
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams()
    {
        return ImmutableSet.of(LootContextParams.THIS_ENTITY);
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
            return !baitStack.isEmpty() && this.itemPredicate.test(baitStack);
        }
        return false;
    }

    public static LootItemCondition.Builder baitMatches(ItemPredicate.Builder itemPredicateBuilder, EntityPredicate.Builder entityPredicateBuilder)
    {
        return () -> new BaitAttachedCondition(itemPredicateBuilder.build(), entityPredicateBuilder.build());
    }
}