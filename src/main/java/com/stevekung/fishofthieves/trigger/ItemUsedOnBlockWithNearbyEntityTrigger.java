package com.stevekung.fishofthieves.trigger;

import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;

public class ItemUsedOnBlockWithNearbyEntityTrigger extends SimpleCriterionTrigger<ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance>
{
    static final ResourceLocation ID = new ResourceLocation(FishOfThieves.MOD_ID, "item_used_on_block_with_nearby_entity");

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser)
    {
        var locationPredicate = LocationPredicate.fromJson(json.get("location"));
        var itemPredicate = ItemPredicate.fromJson(json.get("item"));
        var composite = EntityPredicate.Composite.fromJson(json, "entity", conditionsParser);
        return new TriggerInstance(entityPredicate, locationPredicate, itemPredicate, composite);
    }

    public void trigger(ServerPlayer player, BlockPos pos, ItemStack stack, Entity entity)
    {
        var blockState = player.getLevel().getBlockState(pos);
        var lootContext = EntityPredicate.createContext(player, entity);
        this.trigger(player, triggerInstance -> triggerInstance.matches(blockState, player.getLevel(), pos, stack, lootContext));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final LocationPredicate location;
        private final ItemPredicate item;
        private final EntityPredicate.Composite entity;

        public TriggerInstance(EntityPredicate.Composite composite, LocationPredicate locationPredicate, ItemPredicate itemPredicate, EntityPredicate.Composite entity)
        {
            super(ID, composite);
            this.location = locationPredicate;
            this.item = itemPredicate;
            this.entity = entity;
        }

        public static TriggerInstance itemUsedOnBlock(LocationPredicate.Builder locationBuilder, ItemPredicate.Builder stackBuilder, EntityPredicate.Composite entity)
        {
            return new TriggerInstance(EntityPredicate.Composite.ANY, locationBuilder.build(), stackBuilder.build(), entity);
        }

        public boolean matches(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, LootContext lootContext)
        {
            if (!this.location.matches(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5))
            {
                return false;
            }
            return this.item.matches(stack) || this.entity.matches(lootContext);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context)
        {
            var jsonObject = super.serializeToJson(context);
            jsonObject.add("location", this.location.serializeToJson());
            jsonObject.add("item", this.item.serializeToJson());
            jsonObject.add("entity", this.entity.toJson(context));
            return jsonObject;
        }
    }
}