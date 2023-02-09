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

public class TrophyFishPredicate implements EntitySubPredicate
{
    public static final TrophyFishPredicate ANY = new TrophyFishPredicate(false);
    private static final String TROPHY_KEY = "trophy";
    private final boolean trophy;

    private TrophyFishPredicate(boolean trophy)
    {
        this.trophy = trophy;
    }

    public static TrophyFishPredicate trophy(boolean trophy)
    {
        return new TrophyFishPredicate(trophy);
    }

    public static TrophyFishPredicate fromJson(JsonObject json)
    {
        var jsonElement = json.get(TROPHY_KEY);
        return jsonElement != null ? new TrophyFishPredicate(GsonHelper.convertToBoolean(jsonElement, TROPHY_KEY)) : ANY;
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
            jsonObject.add(TROPHY_KEY, new JsonPrimitive(this.trophy));
            return jsonObject;
        }
    }

    @Override
    public EntitySubPredicate.Type type()
    {
        return FOTEntitySubPredicate.TROPHY;
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
            return this.trophy == thievesFish.isTrophy();
        }
    }
}
