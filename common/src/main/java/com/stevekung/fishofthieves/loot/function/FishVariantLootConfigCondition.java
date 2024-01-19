package com.stevekung.fishofthieves.loot.function;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootItemConditions;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FishVariantLootConfigCondition implements LootItemCondition
{
    public static final Codec<FishVariantLootConfigCondition> CODEC = Codec.unit(new FishVariantLootConfigCondition());

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
}