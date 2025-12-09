package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record LivingEntityHasEffectCondition(Holder<MobEffect> mobEffect) implements SpawnCondition
{
    public static final MapCodec<LivingEntityHasEffectCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MobEffect.CODEC.fieldOf("mob_effect").forGetter(LivingEntityHasEffectCondition::mobEffect)).apply(instance, LivingEntityHasEffectCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return context.fishofthieves$entity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(this.mobEffect);
    }

    public static SpawnCondition effect(Holder<MobEffect> mobEffect)
    {
        return new LivingEntityHasEffectCondition(mobEffect);
    }
}