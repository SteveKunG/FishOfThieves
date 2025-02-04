package com.stevekung.fishofthieves.trigger;

import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FallingAnvilCrushItemTrigger extends SimpleCriterionTrigger<FallingAnvilCrushItemTrigger.TriggerInstance>
{
    static final ResourceLocation ID = FishOfThieves.id("falling_anvil_crush_item");

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public FallingAnvilCrushItemTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext)
    {
        var itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new FallingAnvilCrushItemTrigger.TriggerInstance(predicate, itemPredicate);
    }

    public void trigger(ServerPlayer serverPlayer, ItemStack itemStack)
    {
        this.trigger(serverPlayer, triggerInstance -> triggerInstance.matches(itemStack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate player, ItemPredicate item)
        {
            super(FallingAnvilCrushItemTrigger.ID, player);
            this.item = item;
        }

        public static TriggerInstance crushItem(ItemPredicate.Builder item)
        {
            return new TriggerInstance(ContextAwarePredicate.ANY, item.build());
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context)
        {
            var jsonObject = super.serializeToJson(context);
            jsonObject.add("item", this.item.serializeToJson());
            return jsonObject;
        }

        public boolean matches(ItemStack itemStack)
        {
            return this.item == null || this.item.matches(itemStack);
        }
    }
}