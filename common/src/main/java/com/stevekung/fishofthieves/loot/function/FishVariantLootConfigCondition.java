package com.stevekung.fishofthieves.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FishVariantLootConfigCondition implements LootItemCondition
{
    @Override
    public LootItemConditionType getType()
    {
        return FOTLootItemConditions.FISH_VARIANT_LOOT_CONFIG;
    }

    @Override
    public boolean test(LootContext lootContext)
    {
        return FishOfThieves.CONFIG.general.dropAndPickFishesWithVariant;
    }

    public static LootItemCondition.Builder configEnabled()
    {
        return FishVariantLootConfigCondition::new;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<FishVariantLootConfigCondition>
    {
        @Override
        public void serialize(JsonObject jsonObject, FishVariantLootConfigCondition lootItemEntityPropertyCondition, JsonSerializationContext jsonSerializationContext) {}

        @Override
        public FishVariantLootConfigCondition deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext)
        {
            return new FishVariantLootConfigCondition();
        }
    }
}
