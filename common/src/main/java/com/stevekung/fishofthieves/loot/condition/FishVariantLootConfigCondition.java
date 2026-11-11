package com.stevekung.fishofthieves.loot.condition;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FishVariantLootConfigCondition implements LootItemCondition
{
    public static final MapCodec<FishVariantLootConfigCondition> CODEC = MapCodec.unit(new FishVariantLootConfigCondition());

    @Override
    public MapCodec<? extends LootItemCondition> codec()
    {
        return CODEC;
    }

    @Override
    public @Nullable Boolean constantValue(ConstantConditionEvaluationContext context)
    {
        return null;
    }

    @Override
    public boolean test(LootContext lootContext)
    {
        return FishOfThieves.CONFIG.general.enableFishItemDropWithVariant;
    }

    @Override
    public void validate(ValidationContext context) {}

    public static LootItemCondition.Builder configEnabled()
    {
        return FishVariantLootConfigCondition::new;
    }
}