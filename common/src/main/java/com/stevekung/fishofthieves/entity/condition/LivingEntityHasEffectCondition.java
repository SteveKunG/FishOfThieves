package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public record LivingEntityHasEffectCondition(Holder<MobEffect> mobEffect) implements SpawnCondition
{
    public static final MapCodec<LivingEntityHasEffectCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MobEffect.CODEC.fieldOf("mob_effect").forGetter(LivingEntityHasEffectCondition::mobEffect)).apply(instance, LivingEntityHasEffectCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.LIVING_ENTITY_HAS_EFFECT;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.livingEntity() != null && context.livingEntity().hasEffect(this.mobEffect);
    }

    public static Builder effect(Holder<MobEffect> mobEffect)
    {
        return () -> new LivingEntityHasEffectCondition(mobEffect);
    }
}