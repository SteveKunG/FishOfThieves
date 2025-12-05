package com.stevekung.fishofthieves.loot.predicate;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTEntitySubPredicate;

import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record TreasuredFishPredicate(boolean treasured) implements EntitySubPredicate
{
    public static final TreasuredFishPredicate ANY = new TreasuredFishPredicate(false);
    private static final String TREASURED_KEY = "treasured";

    public static TreasuredFishPredicate treasured(boolean treasured)
    {
        return new TreasuredFishPredicate(treasured);
    }

    public static TreasuredFishPredicate fromJson(JsonObject json)
    {
        var jsonElement = json.get(TREASURED_KEY);
        return jsonElement != null ? new TreasuredFishPredicate(GsonHelper.convertToBoolean(jsonElement, TREASURED_KEY)) : ANY;
    }

    @Override
    public JsonObject serializeCustomData()
    {
        if (this == ANY)
        {
            return new JsonObject();
        }
        else
        {
            var jsonObject = new JsonObject();
            jsonObject.add(TREASURED_KEY, new JsonPrimitive(this.treasured));
            return jsonObject;
        }
    }

    @Override
    public Type type()
    {
        return FOTEntitySubPredicate.TREASURED;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 vec3)
    {
        if (this == ANY)
        {
            return true;
        }
        else if (!(entity instanceof ThievesFish<?> thievesFish))
        {
            return false;
        }
        else
        {
            return this.treasured == thievesFish.getVariant().isTreasured().isPresent();
        }
    }
}