package com.stevekung.fishofthieves.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;

import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FOTLocationCheck implements LootItemCondition
{
    final FOTLocationPredicate predicate;
    final BlockPos offset;

    FOTLocationCheck(FOTLocationPredicate locationPredicate, BlockPos blockPos)
    {
        this.predicate = locationPredicate;
        this.offset = blockPos;
    }

    @Override
    public LootItemConditionType getType()
    {
        return FOTLootItemConditions.FOT_LOCATION_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext)
    {
        var vec3 = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        return vec3 != null && this.predicate.matches(lootContext.getLevel(), vec3.x() + this.offset.getX(), vec3.y() + this.offset.getY(), vec3.z() + this.offset.getZ());
    }

    public static LootItemCondition.Builder checkLocation(FOTLocationPredicate.Builder locationPredicateBuilder)
    {
        return () -> new FOTLocationCheck(locationPredicateBuilder.build(), BlockPos.ZERO);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<FOTLocationCheck>
    {
        @Override
        public void serialize(JsonObject jsonObject, FOTLocationCheck locationCheck, JsonSerializationContext jsonSerializationContext)
        {
            jsonObject.add("predicate", locationCheck.predicate.serializeToJson());

            if (locationCheck.offset.getX() != 0)
            {
                jsonObject.addProperty("offsetX", locationCheck.offset.getX());
            }
            if (locationCheck.offset.getY() != 0)
            {
                jsonObject.addProperty("offsetY", locationCheck.offset.getY());
            }
            if (locationCheck.offset.getZ() != 0)
            {
                jsonObject.addProperty("offsetZ", locationCheck.offset.getZ());
            }
        }

        @Override
        public FOTLocationCheck deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext)
        {
            var locationPredicate = FOTLocationPredicate.fromJson(jsonObject.get("predicate"));
            var i = GsonHelper.getAsInt(jsonObject, "offsetX", 0);
            var j = GsonHelper.getAsInt(jsonObject, "offsetY", 0);
            var k = GsonHelper.getAsInt(jsonObject, "offsetZ", 0);
            return new FOTLocationCheck(locationPredicate, new BlockPos(i, j, k));
        }
    }
}