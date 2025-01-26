package com.stevekung.fishofthieves.trigger;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

public class WaterDripOnBlockTrigger extends SimpleCriterionTrigger<WaterDripOnBlockTrigger.TriggerInstance>
{
    static final ResourceLocation ID = FishOfThieves.id("water_drip_on_block");

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public WaterDripOnBlockTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext)
    {
        var block = deserializeBlock(json);
        var statePropertiesPredicate = StatePropertiesPredicate.fromJson(json.get("state"));

        if (block != null)
        {
            statePropertiesPredicate.checkState(block.getStateDefinition(), string ->
            {
                throw new JsonSyntaxException("Block " + block + " has no property " + string);
            });
        }

        var contextAwarePredicate = ContextAwarePredicate.fromElement("location", deserializationContext, json.get("location"), LootContextParamSets.ADVANCEMENT_LOCATION);

        if (contextAwarePredicate == null)
        {
            throw new JsonParseException("Failed to parse 'location' field");
        }

        return new WaterDripOnBlockTrigger.TriggerInstance(predicate, block, statePropertiesPredicate, contextAwarePredicate);
    }

    @Nullable
    private static Block deserializeBlock(JsonObject json)
    {
        if (json.has("block"))
        {
            var resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "block"));
            return BuiltInRegistries.BLOCK.getOptional(resourceLocation).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + resourceLocation + "'"));
        }
        else
        {
            return null;
        }
    }

    public void trigger(ServerLevel serverLevel, BlockPos blockPos, ServerPlayer player, BlockState state)
    {
        var lootParams = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, blockPos.getCenter()).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.BLOCK_STATE, state).withParameter(LootContextParams.TOOL, player.getMainHandItem()).create(LootContextParamSets.ADVANCEMENT_LOCATION);
        var lootContext = new LootContext.Builder(lootParams).create(null);
        this.trigger(player, triggerInstance -> triggerInstance.matches(state, lootContext));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        @Nullable
        private final Block block;
        private final StatePropertiesPredicate state;
        private final ContextAwarePredicate location;

        public TriggerInstance(ContextAwarePredicate player, @Nullable Block block, StatePropertiesPredicate state, ContextAwarePredicate location)
        {
            super(WaterDripOnBlockTrigger.ID, player);
            this.block = block;
            this.state = state;
            this.location = location;
        }

        public static TriggerInstance waterDrip(Block block)
        {
            return new TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY, ContextAwarePredicate.ANY);
        }

        public static TriggerInstance waterDrip(Block block, LocationPredicate.Builder locationPredicate)
        {
            var contextAwarePredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(locationPredicate).build());
            return new TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY, contextAwarePredicate);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context)
        {
            var jsonObject = super.serializeToJson(context);

            if (this.block != null)
            {
                jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(this.block).toString());
            }

            jsonObject.add("location", this.location.toJson(context));
            jsonObject.add("state", this.state.serializeToJson());
            return jsonObject;
        }

        public boolean matches(BlockState state, LootContext context)
        {
            return (this.block == null || state.is(this.block)) && this.state.matches(state) && this.location.matches(context);
        }
    }
}