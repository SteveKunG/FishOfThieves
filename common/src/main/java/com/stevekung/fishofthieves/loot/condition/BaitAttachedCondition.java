package com.stevekung.fishofthieves.loot.condition;

import java.util.Set;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record BaitAttachedCondition(ItemPredicate itemPredicate, EntityPredicate entityPredicate) implements LootItemCondition.ContextUser
{
    public static final MapCodec<BaitAttachedCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(
                    ItemPredicate.CODEC.fieldOf("item").forGetter(BaitAttachedCondition::itemPredicate),
                    EntityPredicate.CODEC.fieldOf("entity").forGetter(BaitAttachedCondition::entityPredicate))
            .apply(instance, BaitAttachedCondition::new));

    @Override
    public MapCodec<? extends LootItemCondition.ContextUser> codec()
    {
        return CODEC;
    }

    @Override
    public Set<ContextKey<?>> requiredContextParams()
    {
        return Set.of(LootContextParams.THIS_ENTITY);
    }

    @Override
    public boolean test(LootContext context)
    {
        var entity = context.getOptional(LootContextParams.THIS_ENTITY);
        return entity != null && this.entityPredicate.matches(context.getLevel(), null, entity) && this.checkBaitFromHook(entity);
    }

    @Override
    public void validate(ValidationContext context) {}

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