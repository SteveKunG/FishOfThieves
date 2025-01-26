package com.stevekung.fishofthieves.trigger;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
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
        var locationPredicate = LocationPredicate.fromJson(json.get("location"));

        if (block != null)
        {
            statePropertiesPredicate.checkState(block.getStateDefinition(), string ->
            {
                throw new JsonSyntaxException("Block " + block + " has no property " + string);
            });
        }
        return new WaterDripOnBlockTrigger.TriggerInstance(predicate, block, statePropertiesPredicate, locationPredicate);
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
        this.trigger(player, triggerInstance -> triggerInstance.matches(state, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        @Nullable
        private final Block block;
        private final StatePropertiesPredicate state;
        private final LocationPredicate location;

        public TriggerInstance(ContextAwarePredicate player, @Nullable Block block, StatePropertiesPredicate state, LocationPredicate location)
        {
            super(WaterDripOnBlockTrigger.ID, player);
            this.block = block;
            this.state = state;
            this.location = location;
        }

        public static TriggerInstance waterDrip(Block block)
        {
            return new TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY, LocationPredicate.ANY);
        }

        public static TriggerInstance waterDrip(Block block, LocationPredicate.Builder locationPredicate)
        {
            return new TriggerInstance(ContextAwarePredicate.ANY, block, StatePropertiesPredicate.ANY, locationPredicate.build());
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context)
        {
            var jsonObject = super.serializeToJson(context);

            if (this.block != null)
            {
                jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(this.block).toString());
            }

            jsonObject.add("location", this.location.serializeToJson());
            jsonObject.add("state", this.state.serializeToJson());
            return jsonObject;
        }

        public boolean matches(BlockState state, ServerLevel serverLevel, double x, double y, double z)
        {
            return (this.block == null || state.is(this.block)) && this.state.matches(state) && this.location.matches(serverLevel, x, y, z);
        }
    }
}