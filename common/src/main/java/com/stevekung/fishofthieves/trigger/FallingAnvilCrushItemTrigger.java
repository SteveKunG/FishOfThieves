package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FallingAnvilCrushItemTrigger extends SimpleCriterionTrigger<FallingAnvilCrushItemTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer, ItemStack itemStack)
    {
        this.trigger(serverPlayer, triggerInstance -> triggerInstance.matches(itemStack));
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> crushItem(ItemPredicate.Builder item)
        {
            return FOTCriteriaTriggers.FALLING_ANVIL_CRUSH_ITEM.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(item.build())));
        }

        public boolean matches(ItemStack itemStack)
        {
            return this.item.isEmpty() || this.item.get().test(itemStack);
        }
    }
}